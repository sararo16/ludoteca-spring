package com.ccsw.tutorial.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CategoryIdFixerService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void fixCategoryId() {

        Long maxId = ((Number) em.createNativeQuery(
                "SELECT COALESCE(MAX(id), 0) FROM category"
        ).getSingleResult()).longValue();

        long next = maxId + 1;

        em.createNativeQuery(
                "ALTER TABLE category ALTER COLUMN id RESTART WITH " + next
        ).executeUpdate();

        System.out.println("Reiniciado contador de ID de category a: " + next);
    }
}