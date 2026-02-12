package com.ccsw.tutorial.category.service;

import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDto;
import com.ccsw.tutorial.category.repository.CategoryRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ccsw
 *
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Category get(Long id) {

        return this.categoryRepository.findById(id).orElse(null);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Category> findAll() {

        return (List<Category>) this.categoryRepository.findAll();    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void save(Long id, CategoryDto dto) {
        Category category;
        if (id == null) {
            category = new Category();
        } else {
            category = this.categoryRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        }
        category.setName(dto.getName());
        this.categoryRepository.save(category);
    }


    /**
     * {@inheritDoc}
     */

    @Override
    public void delete(Long id) {
        if (!this.categoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
        this.categoryRepository.deleteById(id);
    }


}
