package io.github.mysterix5.sortgame.spring;

import io.github.mysterix5.sortgame.game.solution.PotentialGameStateDb;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SolverRepository extends MongoRepository<PotentialGameStateDb, String> {
}
