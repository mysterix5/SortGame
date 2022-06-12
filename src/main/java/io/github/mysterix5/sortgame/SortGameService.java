package io.github.mysterix5.sortgame;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SortGameService {
    private final SortGameRepository gameRepository;

    public List<GameInfo> getGamesOverview() {
        var games = gameRepository.findAll();
        return games.stream().map(g ->
                new GameInfo(g.getId(), g.getGameProperties().getNEmptyContainers()+g.getGameProperties().getNColors(), g.getGameProperties().getNColors(), g.getInitialPosition().getContainers())).toList();
    }

    public GameInfo getGameById(String id) {
        Game g = gameRepository.findById(id);
        return new GameInfo(g.getId(), g.getGameProperties().getNEmptyContainers()+g.getGameProperties().getNColors(), g.getGameProperties().getNColors(), g.getInitialPosition().getContainers());
    }
}
