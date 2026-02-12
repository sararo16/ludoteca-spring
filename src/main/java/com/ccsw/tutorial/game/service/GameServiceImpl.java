package com.ccsw.tutorial.game.service;

import com.ccsw.tutorial.author.model.Author;
import com.ccsw.tutorial.author.service.AuthorService;
import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.service.CategoryService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.GameSpecification;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.game.repository.GameRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class GameServiceImpl implements GameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    AuthorService authorService;

    @Autowired
    CategoryService categoryService;

    @Override
    public List<Game> find(String title, Long idCategory) {

        Specification<Game> spec = null;

        if (title != null && !title.isBlank()) {
            spec = new GameSpecification(new SearchCriteria("title", ":", title));

        }

            if (idCategory != null) {
                GameSpecification categorySpec =
                        new GameSpecification(new SearchCriteria("category.id", ":", idCategory));
                spec = (spec == null) ? categorySpec : spec.and(categorySpec);
        }

        return (spec == null) ? gameRepository.findAll() : gameRepository.findAll(spec);
    }

    @Override
    public void save(Long id, GameDto dto) {

        Game game;

        if (id == null) {
            game = new Game();
        } else {
            game = this.gameRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        }

        // Copiar los atributos simples
        BeanUtils.copyProperties(dto, game, "id", "author", "category");

        // Validaciones
        if (dto.getAuthor() == null || dto.getAuthor().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author is required");
        }

        if (dto.getCategory() == null || dto.getCategory().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category is required");
        }

        Author author = authorService.get(dto.getAuthor().getId());
        if (author == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author not found");
        }

        Category category = categoryService.get(dto.getCategory().getId());
        if (category == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found");
        }

        game.setAuthor(author);
        game.setCategory(category);

        this.gameRepository.save(game);
    }
}