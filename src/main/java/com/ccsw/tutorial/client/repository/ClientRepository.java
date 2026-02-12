package com.ccsw.tutorial.client.repository;

import com.ccsw.tutorial.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/*
* Esta interfaz permite acceder a la base de datos sin escribir SQL.
* Spring Data JPA genera automaticamente las operaciones CRUD
* (findAll,save,deletById...) gracias a JpaRepository
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    /*
    * metodo generado automaticamente , comprueba si existe un cliente con un nombre concreto
    * ignorando mayusculas/minusculas (se usa en la capa Service)
     * */
    boolean existsByNameIgnoreCase(String name);


}


