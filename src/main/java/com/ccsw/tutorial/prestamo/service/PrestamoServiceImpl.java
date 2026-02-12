
/*
* Implementacion de las reglas de negocio:
* paginacion/filtrado con Specifications
* validaciones de fechas y reglas de dominio: fin >=inicio, rango 14 dias
* ,un juego no prestado a 2 clientes mismo dia, un cliente no puede tener mas 2 prestamos al dia
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

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ClientRepository clientRepository;

    /*
    * Busqueda paginada. compongo el specification con los filtros que vengan.
    * Retorno DTO mapeado para que el front pinte nombres sin mas llamadas
     */
    @Override
    public Page<PrestamoDto> search (PrestamoSearchDto dto) {
        Pageable pageable = PageRequest.of(dto.getPage(),dto.getSize(),
                sortFrom(dto.getSort()));

        Specification <Prestamo> spec=Specification
                .where(PrestamoSpecifications.byGameId(dto.getGameId()))
                .and(PrestamoSpecifications.byClientId(dto.getClientId()))
                .and (PrestamoSpecifications.byDate(dto.getDate()));

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

        //VALIDACIONES DE ENTRADA
        if (dto.getStartDate()==null || dto.getEndDate()==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha fin no puede ser anterior a la fecha de inicio");
        }
        //max 14 dias (cuenta inclusivo por eso sumo 1)
        long days= ChronoUnit.DAYS.between(dto.getStartDate(),dto.getEndDate())+1;
        if (days>14){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El periodo máximo de préstamo es de 14 días");
        }
        //verifico existencia de juego y cliente (si no existen es un 400)
        Game game= gameRepository.findById(dto.getGameId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Juego inexistente"));

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente inexistente"));

        //excludeId-- si estoy editando quiero ignorarme las comprobaciones
        Long excludeId=dto.getId();

        //REGLA 1: no solape el mismo juego
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

        //REGLA 2: maximo 2 prestamos por cliente y dia
        LocalDate d=dto.getStartDate();
        while (!d.isAfter(dto.getEndDate())){
            long count = prestamoRepository.countClientPrestamoOnDay(client.getId(),d,excludeId);
        //si ya tiene 2, añadir este 3 para ese dia concreto -- rechazo
            if (count >=2){
                throw new ResponseStatusException(HttpStatus.CONFLICT,"El cliente supera el máximo de 2 préstamos para el dia: "+d);
            }
            d=d.plusDays(1);
        }
        //Persistencia (crear/editar)
        Prestamo entity=(dto.getId()==null)
              ? new Prestamo()
              : prestamoRepository.findById(dto.getId())
                      .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Préstamo no existe"));

        //asigno relaciones y fechas
        entity.setGame(game);
        entity.setClient(client);
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());

        //guardo y devuelvo DTO
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

    //Helpers privados
    /*
    * Construyo el Sort a partir de una cadena "campo, asc/desc"
    * Si no viene bien formada, id asc
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
    *Mapeo entidad -- DTO de salida
    * Ajusto getters a mis entidades reales:
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