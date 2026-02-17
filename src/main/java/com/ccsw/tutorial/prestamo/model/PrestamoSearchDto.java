
/*
* DTO de busqueda que recibe el back para filtrar prestamos y aplicar paginacion. payload de ENTRADA
* Permite al front buscar prestamos aplicando filtros opcionales, centralizar la paginacion y el orden
 */

package com.ccsw.tutorial.prestamo.model;


import java.time.LocalDate;

public class PrestamoSearchDto {

    private Long gameId;     // opcional
    private Long clientId;   // opcional
    private LocalDate date;  // opcional . activos ese dia

    //paginacion/orden
    private int page = 0; //indice de pagina
    private int size = 10; //tamaño por defecto
    private String sort = "id,asc"; //orden por defecto

    //Getters y setters
    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}


