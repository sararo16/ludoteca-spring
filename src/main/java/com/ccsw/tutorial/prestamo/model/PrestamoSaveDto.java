
/*
* DTO de entrada (payload: info que viaja dentro de una peticion http) usado para crear o editar un prestamo
* PROPOSITO-- recoger los datos enviados por el front en operaciones de alta o edicion/evitar exponer directamente la entidad
* ESCENARIOS -- alta(campo id viene a null), EDICION (id real se recoge desde la URL back sobreescribe el valor)
*/


package com.ccsw.tutorial.prestamo.model;

import java.time.LocalDate;

public class PrestamoSaveDto {

    //null en alta, en edicion se ignora y se usa el id de la URL
    private Long id;
    //identificador del juego
    private Long gameId;
    //identificador del cliente
    private Long clientId;
    //fecha inicio prestamo
    private LocalDate startDate;
    //fecha fin prestamo
    private LocalDate endDate;

    //getter y setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
