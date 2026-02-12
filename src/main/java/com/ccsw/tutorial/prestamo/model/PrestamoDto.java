
/*
* Este DTO se devuelve al front. mejor no devolver la entidad completa
* para no acoplarla al modelo de datos internos y ademas se necesita
* devolver campos derivados (juego, cliente)
 */

package com.ccsw.tutorial.prestamo.model;
import java.time.LocalDate;

public class PrestamoDto {

     private Long id;

     //se identifica el juego y se manda tambn el nombre para pintar la tabla
     private Long gameId;
     private String gameName;

     //lo mismo con cliente
     private Long clientId;
     private String clientName;

     //fechas inclusivas
     private LocalDate startDate;
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

    public String getGameName(){ return gameName; }
    public void setGameName(String gameName){ this.gameName = gameName; }


    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName(){ return clientName; }
    public void setClientName(String clientName){ this.clientName = clientName;}


    public LocalDate getStartDate(){ return startDate; }
    public void setStartDate(LocalDate startDate){ this.startDate = startDate; }

    public LocalDate getEndDate(){ return endDate; }
    public void setEndDate(LocalDate endDate){ this.endDate = endDate; }

}
