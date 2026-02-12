package com.ccsw.tutorial.game.repository;

import com.ccsw.tutorial.game.model.Game;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import com.ccsw.tutorial.game.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author ccsw
 *
 */

        public interface GameRepository extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game> {



}
