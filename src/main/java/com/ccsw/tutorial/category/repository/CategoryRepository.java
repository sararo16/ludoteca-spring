
//Este archivo es la capa encargada de acceder a la base de datos
//guarda,busca y borra categorias

package com.ccsw.tutorial.category.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ccsw.tutorial.category.model.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ccsw
 *
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

}
