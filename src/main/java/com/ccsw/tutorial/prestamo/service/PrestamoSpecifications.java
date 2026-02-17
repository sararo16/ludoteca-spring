
/*
* Specifications estaticas para montar el WHERE de forma componsable
* Se aplican solo los filtros que vengan informados
 */

package com.ccsw.tutorial.prestamo.service;

import java.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;
import com.ccsw.tutorial.prestamo.model.Prestamo;

public class PrestamoSpecifications {

    /*filtro por id del juego, si no viene, no filtro
    * devuelve una Specification que compara el id del juego: si gameid es null,
    * se devuelve cb.conjunction() para no afectar al WHERE
     */
    public static Specification<Prestamo> byGameId(Long gameId) {
        return (root, q, cb) ->
                gameId == null ? cb.conjunction() : cb.equal(root.get("game").get("id"), gameId);
    }

    /* Filtro por id de cliente. Si no viene, no filtro.
     * Igual patron que byGameId: conjunction (true) si el filtro no llega informado
     */
    public static Specification<Prestamo> byClientId(Long clientId) {
        return (root, q, cb) ->
                clientId == null ? cb.conjunction() : cb.equal(root.get("client").get("id"), clientId);
    }

    /*
     * Filtro por fecha ⇒ prestamos “activos” ese día:
     * startDate <= date AND endDate >= date
     * si date es null. devuelve conjuncion (true) para no aplicar el filtro
     */
    public static Specification<Prestamo> byDate(LocalDate date) {
        return (root, q, cb) ->
                date == null ? cb.conjunction() :
                        cb.and(cb.lessThanOrEqualTo(root.get("startDate"), date),
                                cb.greaterThanOrEqualTo(root.get("endDate"), date));
    }
}


