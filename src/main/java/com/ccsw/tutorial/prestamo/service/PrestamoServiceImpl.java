
/*
* Implementacion de las reglas de negocio:
* Paginacion/filtrado con specification
* validaciones de fechas y reglas de dominio
 */

package com.ccsw.tutorial.prestamo.service;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.repository.ClientRepository;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.game.repository.GameRepository;
import com.ccsw.tutorial.prestamo.model.*;
import com.ccsw.tutorial.prestamo.repository.PrestamoRepository;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    //inyeccion de dependencias de repositorios necesarios para la logica
    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ClientRepository clientRepository;

    /*
    * busqueda paginada. compongo el specification con los filtros que vengan (opcional)
    * Retorno DTO mapeado para que el front pinte nombres sin llamadas extra
     */
    @Override
    public Page<PrestamoDto> search (PrestamoSearchDto dto) {
       //construccion del pageable a partir de page/size/sort del DTO de busqueda
        Pageable pageable = PageRequest.of(dto.getPage(),dto.getSize(),
                sortFrom(dto.getSort()));

        // composicion de specification dinamico (si no hay filtro null)
        Specification <Prestamo> spec=Specification
                .where(PrestamoSpecifications.byGameId(dto.getGameId()))
                .and(PrestamoSpecifications.byClientId(dto.getClientId()))
                .and (PrestamoSpecifications.byDate(dto.getDate()));
        //ejecuto la busqueda paginada y mapeo entidad --> dto
        var page = prestamoRepository.findAll(spec, pageable);
        return page.map(this::toDto);
    }

    /*
    *Alta/edicion de prestamo con todas las validaciones de negocio.
    * @transactional para que sea atomico (si algo falla, no deja estados parciales)
     */
    @Override
    @Transactional
    public PrestamoDto save (PrestamoSaveDto dto) {

        //VALIDACIONES DE ENTRADA: fechas no nulas y orden correcto
        if (dto.getStartDate()==null || dto.getEndDate()==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha fin no puede ser anterior a la fecha de inicio");
        }
        //max 14 dias (computo inclusivo por eso + 1)
        long days= ChronoUnit.DAYS.between(dto.getStartDate(),dto.getEndDate())+1;
        if (days>14){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El periodo máximo de préstamo es de 14 días");
        }
        //verifico existencia de juego y cliente (si no existen es un 400)
        Game game= gameRepository.findById(dto.getGameId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Juego inexistente"));

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente inexistente"));

        //excludeId-- si estoy editando, ignoro mi propio registro en comporbaciones
        Long excludeId=dto.getId();

        //REGLA 1: el mismo juego no puede solaparse en las fechas indicadas
        boolean overlapGame=prestamoRepository.existsOverlapForGame(
                game.getId(),
                dto.getStartDate(),
                dto.getEndDate(),
                excludeId
        );
        if (overlapGame){
            //409, conflicto de negocio (datos validos pero incumplen reglas)
        throw new ResponseStatusException(HttpStatus.CONFLICT,"Ese juego ya esta prestado en las fechas indicadas");
        }

        //REGLA 2: maximo 2 prestamos por cliente y dia (se comprueba dia a dia en el rango)
        LocalDate d=dto.getStartDate();
        while (!d.isAfter(dto.getEndDate())){
            long count = prestamoRepository.countClientPrestamoOnDay(client.getId(),d,excludeId);
        //si ya tiene 2, añadir este seria el 3 para ese dia  --> rechazo
            if (count >=2){
                throw new ResponseStatusException(HttpStatus.CONFLICT,"El cliente supera el máximo de 2 préstamos para el dia: "+d);
            }
            d=d.plusDays(1);
        }
        //Persistencia: crear o editar (si id viene null --> alta si no, buscar y editar)
        Prestamo entity=(dto.getId()==null)
              ? new Prestamo()
              : prestamoRepository.findById(dto.getId())
                      .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Préstamo no existe"));

        //asigno relaciones y fechas desde el dto
        entity.setGame(game);
        entity.setClient(client);
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());

        //guardo en bbdd y transformo a DTO de salida
        entity=prestamoRepository.save(entity);
        return toDto(entity);
    }

    //Eliminacion segura: si no existe, devuelvo 404
    @Override
    @Transactional
    public void delete(Long id){
        if (!prestamoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Prestamo no existe");
        }
        prestamoRepository.deleteById(id);
    }

    //Helpers privados (no forman parte de la logica, siven de apoyo a la implementacion)
    /*
    * Construyo el Sort a partir de una cadena "campo, asc/desc"
    * Si no viene bien formada --> id asc
     */
    private Sort sortFrom(String sort){
        if (sort == null ||sort.isBlank()) return Sort.by("id").ascending();
        String [] parts = sort.split (",");
        if (parts.length==2 && "desc".equalsIgnoreCase(parts[1])) {
            return Sort.by(parts[0]).descending();
        }
        return Sort.by(parts[0]).ascending();
    }

    /*
    *Mapeo entidad --> DTO de salida
    * Ajusta getters a las entidades reales (title/name)
     */
    private PrestamoDto toDto(Prestamo prestamo) {
        PrestamoDto dto = new PrestamoDto();
        dto.setId(prestamo.getId());

        dto.setGameId(prestamo.getGame().getId());
        dto.setClientId(prestamo.getClient().getId());

        dto.setGameName(prestamo.getGame().getTitle());
        dto.setClientName(prestamo.getClient().getName());

        dto.setStartDate(prestamo.getStartDate());
        dto.setEndDate(prestamo.getEndDate());
        return dto;
    }

}