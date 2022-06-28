package io.github.mysterix5.sortgame.spring;

import io.github.mysterix5.sortgame.game.Game;
import io.github.mysterix5.sortgame.game.GameInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SortGameService {
    private final SortGameRepository gameRepository;

    public Optional<GameInfo> getGameById(String id) {
        var tmp = gameRepository.findById(id);
        if (tmp.isPresent()) {
            var g = tmp.get();
            return Optional.of(new GameInfo(g.getId(), g.getGameProperties().getNEmptyContainers() + g.getGameProperties().getNColors(), g.getGameProperties().getNColors(), g.getActualPosition().getContainers()));
        } else {
            return Optional.empty();
        }
    }

    public void move(GamePutter gamePutter) {
        Optional<Game> game = gameRepository.findById(gamePutter.getId());
        if(game.isPresent()){
            game.get().getActualPosition().move(gamePutter.getMove());
            gameRepository.save(game.get());
        }
    }

    public void resetGame(String id) {
        gameRepository.findById(id).ifPresent(Game::reset);
    }

    public List<String> getAllSavedGamesIds() {
        return gameRepository.findAll().stream().map(Game::getId).toList();
    }
}
