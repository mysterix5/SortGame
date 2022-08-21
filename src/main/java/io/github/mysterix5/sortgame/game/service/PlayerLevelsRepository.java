package io.github.mysterix5.sortgame.game.service;

import io.github.mysterix5.sortgame.models.game.StartedLevel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerLevelsRepository extends MongoRepository<StartedLevel, String> {
    List<StartedLevel> findByPlayerId(String id);
}
