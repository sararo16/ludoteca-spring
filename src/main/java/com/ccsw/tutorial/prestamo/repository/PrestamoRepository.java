
/*
* Repositorio JPA para la entidad prestamo
* CRUD basico heredado de JpaRepository, busquedas con filtros dinamicos, consultas
 */

package com.ccsw.tutorial.prestamo.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.ccsw.tutorial.prestamo.model.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long>,
        JpaSpecificationExecutor<Prestamo>{

    /*
    *  Busqueda paginada con filtros dinamicos.
    * @EntityGraph fuerza a JPA a realizar un fetch de game y client en la misma query
    * evitando cargas perezosas posteriores que multiplican las consultas (N+1)
    */
    @EntityGraph (attributePaths = {"game","client"})
    Page<Prestamo> findAll(Specification<Prestamo> spec, Pageable pageable);

    /*
    * Valido si existe un prestamo del mismo juego que solape con el rango de fechas proporcionado
     */
    @Query("""
      select count(p) > 0
      from Prestamo p
      where p.game.id = :gameId
        and (:excludeId is null or p.id <> :excludeId)
        and p.startDate <= :end
        and p.endDate   >= :start
      """)
    boolean existsOverlapForGame(@Param("gameId") Long gameId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("excludeId") Long excludeId); //cuando edito , ignoro mi propio registro

    /*
    * Cuenta cuantos prestamos activos tiene un cliente en un dia concreto
    * se usa para validar la regla de negocio: max 2 juegos por dia
    * BETWEEN -- en dates es inclusivo en ambos extremos
     * */

    @Query("""
          select count(p)
          from Prestamo p
          where p.client.id = :clientId
            and (:excludeId is null or p.id <> :excludeId)
            and :day between p.startDate and p.endDate
          """)
        long countClientPrestamoOnDay(@Param("clientId") Long clientId,
                @Param("day") LocalDate day,
                @Param("excludeId") Long excludeId);

}

