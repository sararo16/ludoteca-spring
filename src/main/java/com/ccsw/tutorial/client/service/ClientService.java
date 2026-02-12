package com.ccsw.tutorial.client.service;

import com.ccsw.tutorial.client.model.Client;
import java.util.List;

/*
* Interfaz de la capa Service para la entidad Client.
* Se define que operaciones puede realizar nuestro servicio
* Sin implementar logica todavia
 */

public interface ClientService {
    //Devuelve la lista completa de clientes
    //se usa en listado del front
    List<Client> findAll();

    //guarda un cliente nuevo, en Implementacion se comprueba que no exista un cliente con el mismo nombre
    Client save(Client client);

    //actualiza un cliente existente, valida que no se duplique el nombre
    //y que el cliente exista
    Client update(Long id, Client client);

    //elimina un cliente por id, evita que el controlador trabaje
    //directamente con el repositorio
    void delete(Long id);
}
