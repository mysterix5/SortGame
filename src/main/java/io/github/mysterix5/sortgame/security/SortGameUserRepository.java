package io.github.mysterix5.sortgame.security;

import io.github.mysterix5.sortgame.models.security.SortGameUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SortGameUserRepository extends MongoRepository<SortGameUser, String> {

    boolean existsByUsernameIgnoreCase(String username);

    Optional<SortGameUser> findByUsername(String username);
}