
package com.ccsw.tutorial.client.model;

import jakarta.persistence.*; //convierte esta clase a una tabla de la base de datos

/**
 * Esta entidad representa un cliente dentro de la base de datos.
 * Esta clase corresponde a la tabla client
 */

@Entity //Se marca como entidad persistente (se guarda en bbdd)
@Table(name = "client") //tabla asociada
public class Client {

    //PK (primary key) autoincremental
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //el valor del id lo genera la bbdd de forma incremental
    private Long id; //identificador unico de cada cliente

    //nombre del cliente, obligatorio
    @Column(name = "name", nullable = false) //nullable--> no permite valores nulos (requisito funcional)
    private String name;

    //constructor vacio necesario para JPA para poder instanciar la entidad
    //mediante reflexion al leer/escribir desde la bbdd
    public Client() {}

    /**
     * getter id
     */
    public Long getId() {

        return this.id;
    }

    /**
     * setter del id.
     */
    public void setId(Long id) {

        this.id = id;
    }

    /**
     * getter del nombre del cliente
     */

    public String getName() {

        return this.name;
    }
    /**
     * setter del nombre del cliente.
     */
    public void setName(String name) {

        this.name = name;
    }


}