package io.github.mysterix5.sortgame.game.service;

import io.github.mysterix5.sortgame.game.solution.SolverWeight;
import io.github.mysterix5.sortgame.game.solution.StaticMethods;
import io.github.mysterix5.sortgame.models.game.*;
import io.github.mysterix5.sortgame.security.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SortGameService {
    private final SortGameRepository gameRepository;
    private final PlayerLevelsRepository playerLevelsRepository;
    private final UserService userService;

    public Optional<GameInfo> getGameById(String id) {
        var tmp = gameRepository.findById(id);
        if (tmp.isPresent()) {
            var g = tmp.get();
            return Optional.of(new GameInfo(g));
        } else {
            return Optional.empty();
        }
    }

    public void move(String id, Move move) {
        Optional<Game> game = gameRepository.findById(id);
        if(game.isPresent()){
            game.get().getActualPosition().move(move);
            gameRepository.save(game.get());
        }
    }

    public void resetGame(String id) {
        var game = gameRepository.findById(id);
        game.ifPresent(Game::reset);
        game.ifPresent(gameRepository::save);
    }

    public List<GameInfo> getAllSavedGames() {
        return gameRepository.findAll().stream().map(GameInfo::new).toList();
    }

    public GamesList getAllGames(String username) {
        List<Game> allLevels = gameRepository.findAll();
        String userId = userService.getUserIdFromName(username);
        List<StartedLevel> playerLevelRepositories = playerLevelsRepository.findByPlayerId(userId);
        List<String> startedLvlIds = playerLevelRepositories.stream().map(StartedLevel::getGameId).toList();
        var gamesList = new GamesList();
        gamesList.setNewLevels(
                allLevels.stream()
                .filter(gameId -> !startedLvlIds.contains(gameId))
                .map(GameInfo::new).toList());
        return gamesList;
    }

    public Move getHint(String id) {
        Optional<Game> game = gameRepository.findById(id);
        if(game.isPresent()) {
            PlayingField playingField = game.get().getActualPosition();
            SolverWeight solver = new SolverWeight();

            solver.setup(playingField);
            solver.solve(false);
            if(!solver.getSolutions().isEmpty()){
                return solver.getSolutionsAsSortedMoveLists().get(0).get(0);
            }
            System.out.println("no solution found");
        }
        throw new RuntimeException();
    }

    public GameInfo createGame(GameCreationData gameCreationData) {

        GameProperties gameProperties = new GameProperties(gameCreationData.getHeight(), gameCreationData.getColors(), gameCreationData.getEmpty());
        while (true) {
            PlayingField playingField = StaticMethods.createRandomGame(gameProperties);
            System.out.println(playingField);

            SolverWeight solver = new SolverWeight();

            solver.setup(playingField);
            solver.solve(true);
            if (solver.getSolutions().isEmpty()) {
                System.out.println("no solution found");
            } else {
                Game game = new Game(gameProperties);
                game.setPosition(playingField);

                gameRepository.save(game);

                return new GameInfo(game);
            }
        }
    }

    public void deleteAllLevels() {
        gameRepository.deleteAll();
    }

}
