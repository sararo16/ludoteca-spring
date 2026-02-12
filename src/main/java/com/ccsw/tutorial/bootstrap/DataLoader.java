package com.ccsw.tutorial.bootstrap;

import com.ccsw.tutorial.author.model.Author;
import com.ccsw.tutorial.author.repository.AuthorRepository;
import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.repository.CategoryRepository;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.game.repository.GameRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final GameRepository gameRepository;

    public DataLoader(CategoryRepository categoryRepository, AuthorRepository authorRepository, GameRepository gameRepository) {
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // Si ya hay datos, no hacemos nada
        if (categoryRepository.count() > 0 || authorRepository.count() > 0 || gameRepository.count() > 0) {
            return;
        }

        // Categorías
        Category euro = new Category();
        euro.setName("Eurogames");
        Category ameri = new Category();
        ameri.setName("Ameritrash");
        Category fam = new Category();
        fam.setName("Familiar");
        euro = categoryRepository.save(euro);
        ameri = categoryRepository.save(ameri);
        fam = categoryRepository.save(fam);

        // Autores
        Author a1 = new Author();
        a1.setName("Alan R. Moon");
        a1.setNationality("US");
        Author a2 = new Author();
        a2.setName("Vital Lacerda");
        a2.setNationality("PT");
        Author a3 = new Author();
        a3.setName("Simone Luciani");
        a3.setNationality("IT");
        Author a4 = new Author();
        a4.setName("Perepau Llistosella");
        a4.setNationality("ES");
        Author a5 = new Author();
        a5.setName("Michael Kiesling");
        a5.setNationality("DE");
        Author a6 = new Author();
        a6.setName("Phil Walker-Harding");
        a6.setNationality("US");
        a1 = authorRepository.save(a1);
        a2 = authorRepository.save(a2);
        a3 = authorRepository.save(a3);
        a4 = authorRepository.save(a4);
        a5 = authorRepository.save(a5);
        a6 = authorRepository.save(a6);

        // Juegos
        Game g1 = new Game();
        g1.setTitle("Aventureros");
        g1.setAge(String.valueOf(8));
        g1.setAuthor(a1);
        g1.setCategory(fam);
        Game g2 = new Game();
        g2.setTitle("Catan");
        g2.setAge(String.valueOf(10));
        g2.setAuthor(a1);
        g2.setCategory(euro);
        Game g3 = new Game();
        g3.setTitle("Carcassonne");
        g3.setAge(String.valueOf(8));
        g3.setAuthor(a2);
        g3.setCategory(euro);
        Game g4 = new Game();
        g4.setTitle("Pandemic");
        g4.setAge(String.valueOf(12));
        g4.setAuthor(a2);
        g4.setCategory(fam);
        Game g5 = new Game();
        g5.setTitle("Agricola");
        g5.setAge(String.valueOf(14));
        g5.setAuthor(a3);
        g5.setCategory(ameri);
        Game g6 = new Game();
        g6.setTitle("Terraforming Mars");
        g6.setAge(String.valueOf(12));
        g6.setAuthor(a3);
        g6.setCategory(ameri);
        gameRepository.save(g1);
        gameRepository.save(g2);
        gameRepository.save(g3);
        gameRepository.save(g4);
        gameRepository.save(g5);
    }

}

