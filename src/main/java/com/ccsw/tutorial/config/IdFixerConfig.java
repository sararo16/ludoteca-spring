package com.ccsw.tutorial.config;


import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class IdFixerConfig {

    private final IdFixerService idFixerService;

    public IdFixerConfig(IdFixerService idFixerService) {
        this.idFixerService = idFixerService;
    }

    @Bean
    public ApplicationRunner runIdFix() {
        return args -> idFixerService.fixAuthorId();
    }
}
