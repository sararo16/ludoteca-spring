package com.ccsw.tutorial.client;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.service.ClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* Controlador para gestionar la API Client.
* Esta capa recibe las peticiones HTTP del front y delega
* toda la logica de negocio (ClientService). NO toca la bbdd directamente
 */
//etiqueta para documentacion
@Tag(name = "Client", description = "API of Client")

//ruta base,todas las peticiones a api/clients entran a este controlador
@RequestMapping(value = "/api/clients")

//marca esta clase como controlador
//convierte automaticamente objetos java a json
@RestController

//Permite que Angular haga peticiones a este backend
@CrossOrigin(origins = {"http://localhost:4200"}, allowCredentials = "true")

    public class ClientController {

        //inyeccion del servicio que tiene la logica de negocio
        @Autowired
        private ClientService clientService;

        //ModelMapper permite convertir entidades a DTOS (no se usa)
        @Autowired
        ModelMapper mapper;

        /*
        * GET-- devuelve la lista de todos los clientes
         */
        @GetMapping
        public List<Client> getAll() {
            //Delegamos al servicio
            return clientService.findAll();
        }

        /*
        * POST-- crea un nuevo cliente,
        * recibe un JSON desde Angular y lo convierte en objeto Java
         */
        @PostMapping
        public Client create(@RequestBody Client client) {
            //llamamos al servicio, que valida que el nombre no este duplicado
            return clientService.save(client);
        }

        /*
        *PUT -- actualiza un cliente existente
        * @PathVariable extrae el id de la URL
         */
        @PutMapping("/{id}")
        public Client update(@PathVariable Long id, @RequestBody Client client) {
            return clientService.update(id, client);
        }

        /*
        *DELETE -- elimina un cliente por id
         */
        @DeleteMapping("/{id}")
        public void delete(@PathVariable Long id) {
            clientService.delete(id);
        }
    }




