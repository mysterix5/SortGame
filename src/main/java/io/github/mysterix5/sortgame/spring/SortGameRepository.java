package io.github.mysterix5.sortgame.spring;

import io.github.mysterix5.sortgame.models.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SortGameRepository extends MongoRepository<Game, String> {
}
