
/*
* Servicio de dominio para gestionar la logica de negocio de los prestamos.
* PROPOSITO -- desacoplat el controlador de la implementacion real, exponer solo las operaciones del caso de uso
* Se implementa en PrestamoServiceImpl.
 */
package com.ccsw.tutorial.prestamo.service;

import com.ccsw.tutorial.prestamo.model.PrestamoSearchDto;
import org.springframework.data.domain.Page;

import com.ccsw.tutorial.prestamo.model.PrestamoDto;
import com.ccsw.tutorial.prestamo.model.PrestamoSaveDto;

public interface PrestamoService {

    //busqueda paginada con filtros opcionales
    Page<PrestamoDto> search(PrestamoSearchDto dto);

    //Alta o edicion (si dto.id !=null). aplica todas las validaciones
    PrestamoDto save(PrestamoSaveDto dto);

    //elimina un prestamo por id (si no existe --> 404)
    void delete(Long id);

}
