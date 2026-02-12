
/*
* Controlador REST de prestamos.
* Endpoints: POST (filtros), POST(crear), PUT (editar), DELETE (eliminar)
 */

package com.ccsw.tutorial.prestamo;


import com.ccsw.tutorial.prestamo.service.PrestamoService;
import com.ccsw.tutorial.prestamo.service.PrestamoSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.ccsw.tutorial.prestamo.model.PrestamoDto;
import com.ccsw.tutorial.prestamo.model.PrestamoSaveDto;
import com.ccsw.tutorial.prestamo.model.PrestamoSearchDto;


    @RestController
    @RequestMapping ("api/prestamos")
    @CrossOrigin (origins="http://localhost:4200", allowCredentials = "true") //habilitan las llamadas desde el front

    public class PrestamoController {

        @Autowired
        private PrestamoService prestamoService;

        //búsqueda paginada con filtros opcionales
        @PostMapping("/search")
        public Page<PrestamoDto> search(@RequestBody PrestamoSearchDto dto) {
            return prestamoService.search(dto);
        }

        //Alta: fuerzo id= null para evitar que el cliente lo manipule
        @PostMapping
        public PrestamoDto create (@RequestBody PrestamoSaveDto dto){
            dto.setId(null);
            return prestamoService.save(dto);
        }

        //Edicion: el id "oficial" viene de la ruta no del body
        @PutMapping ("/{id}")
        public PrestamoDto update (@PathVariable Long id, @RequestBody PrestamoSaveDto dto){
            dto.setId(id);
            return prestamoService.save(dto);
        }

        //Eliminar
        @DeleteMapping ("/{id}")
        public void delete (@PathVariable Long id){
            prestamoService.delete(id);
        }
}
