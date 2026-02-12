
/*
* Payload para crear/editar un prestamo
* en alta, id= null.
* en edicion, id viene en la URL y se resetea con el servicio/controlador
 */

package com.ccsw.tutorial.prestamo.model;

import java.time.LocalDate;

public class PrestamoSaveDto {

    private Long id;         // null en alta
    private Long gameId; //obligatorio
    private Long clientId; //obligatorio
    private LocalDate startDate; //obligatorio
    private LocalDate endDate; //obligatorio

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
