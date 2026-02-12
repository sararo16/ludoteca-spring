package com.ccsw.tutorial.client.service;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/*
* Implementacion de la capa dde servicio para la entidad Client
* Se centraliza la logica de negocio
 */
@Service
public class ClientServiceImpl implements ClientService {

    //inyectamos el respositorio para acceder a la BBDD
    @Autowired
    private ClientRepository clientRepository;

    /*
    * Devuelve los clientes
    * se usa para el listado en el fronted
     */
    @Override
    public List<Client> findAll() {
        //findAll lo implementa Spring Data JPA
        return clientRepository.findAll();
    }

    /*
    *Crea un nuevo cliente
    * Regla de negocio: no permitir nombres duplicados
     */
    @Override
    public Client save(Client client) {

        //validacion de unicidad por nombre
        if (clientRepository.existsByNameIgnoreCase(client.getName())) {
            //lanzamos excepcion de negocio si existe el nombre
            throw new RuntimeException("El nombre ya existe");
        }
        //si pasa la validacion, persistimos la entidad
        return clientRepository.save(client);
    }

    /*
    * Actualiza un cliente existente.
    * El cliente con ese id debe existir,
    * si cambia el nombre no debe duplucar el de otro cliente
     */
    @Override
    public Client update(Long id, Client client) {
        //1. recuperamos el cliente actual o lanzamos excepcion si no existe
        Client existing = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no existe"));

        //2. si el nombre cambia, comprobamos unicidad
        if (!existing.getName().equalsIgnoreCase(client.getName())
                && clientRepository.existsByNameIgnoreCase(client.getName())) {
            throw new RuntimeException("El nombre ya existe");
        }

        //3. aplicamos los cambios permitidos (solo name)
        existing.setName(client.getName());

        //4. guardamos y devolvemos el cliente actualizado
        return clientRepository.save(existing);
    }

    /*
    * Elimina cliente por id.
    * Devuelve void porque al eliminar no hay nada que devolver
     */
    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }
}