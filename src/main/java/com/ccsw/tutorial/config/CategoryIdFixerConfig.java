package com.ccsw.tutorial.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryIdFixerConfig {

    private final CategoryIdFixerService categoryIdFixerService;

    public CategoryIdFixerConfig(CategoryIdFixerService categoryIdFixerService) {
        this.categoryIdFixerService = categoryIdFixerService;
    }

    @Bean
    public ApplicationRunner runCategoryIdFix() {
        return args -> categoryIdFixerService.fixCategoryId();
    }
}