
/*
* Repositorio JPA para prestamo
* Extiendo JpaSpecificationExecturor para poder aplicar filtros dinamicos
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

    /**
     * Declaro explicitamente findAll para poder anotar @entityGraph
     *  y asi forzar que game y client se carguen en la misma query
     */

    @EntityGraph (attributePaths = {"game","client"})
    Page<Prestamo> findAll(Specification<Prestamo> spec, Pageable pageable);

    /*
    * Valido si existe un prestamo del mismo juego que solape con el rango dado
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
    * Devuelve los prestamos que tiene el cliente ACTIVOS en un dia concreto.
    * Se usa para validar el limite "maximo 2 juegos por dia"
     */

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

