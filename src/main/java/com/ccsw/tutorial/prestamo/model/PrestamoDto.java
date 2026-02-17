
/*
* Este DTO (Data Transfer Object) expone prestamos hacia el front
* Evita devolver la entidad directamente (previniendo problemas de lazy loading /campos internos)
* Incluye campos derivados o de presentacion sin obligar al front a hacer llamadas adicionales
* USO: se devuelve en respuesta al controller, puede usarse como payload de entrada
 */


package com.ccsw.tutorial.prestamo.model;
import java.time.LocalDate;

public class PrestamoDto {

    //identificador del prestamo
     private Long id;

     //identificador del juego asociado que permite operar desde el front
     private Long gameId;
     //nombre del juego(campo de conveniencia para pintar la tabla)
     private String gameName;

     //lo mismo con cliente
     private Long clientId;
     private String clientName;

     //fechas inicio y fin del prestamo
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
