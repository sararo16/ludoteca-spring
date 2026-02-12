package com.ccsw.tutorial.author.repository;

import com.ccsw.tutorial.author.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ccsw
 *
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {
    /**
     * Método para recuperar un listado paginado de {@link Author}
     *
     * @param pageable pageable
     * @return {@link Page} de {@link Author}
     */

    Page<Author> findAll(Pageable pageable);

}



