
//Entidad q¡en la que se representa un prestamo con su juego, cliente, fecha inicio y fin
//Se usa LocalDate porque el dominio funciona por dias
package com.ccsw.tutorial.prestamo.model;

import java.time.LocalDate;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.game.model.Game;

import jakarta.persistence.*;


@Entity
@Table(name = "prestamo")

public class Prestamo {

    //PK , se usa identity para incrementar
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    * relacion N-1 con Game. Un prestamo siempre esta asociado a un juego
    * optional = false-- no permite prestamos sin juego
    * fetch--No cargo el juego si no lo necesito (optimizacion)
    * @JoinColumn-- la FK se llama game_id
     */

    @ManyToOne(optional = false, fetch = FetchType.LAZY)

    @JoinColumn(name = "game_id")
    private Game game;


    /*
    * Relacion N-1 con Client. Un prestamo siempre asociado a un cliente
    * Optional = false -- no permito prestamos sin cliente
    * fetch=LAZY -- optimizo la carga
    * @JoinColumn -- la FK se llama client__id
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    //fecha de inicio del prestamo
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    //fecha fin del prestamo
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Game getGame() { return game; }
    public void setGame(Game game) { this.game = game; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}

