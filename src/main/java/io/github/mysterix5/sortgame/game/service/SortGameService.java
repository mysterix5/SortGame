package io.github.mysterix5.sortgame.game.service;

import io.github.mysterix5.sortgame.game.solution.SolverWeight;
import io.github.mysterix5.sortgame.game.solution.StaticMethods;
import io.github.mysterix5.sortgame.models.game.*;
import io.github.mysterix5.sortgame.security.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SortGameService {
    private final SortGameRepository gameRepository;
    private final PlayerLevelsRepository playerLevelsRepository;
    private final UserService userService;

    public GameInfo getGameById(String userId, String levelId) {
        Optional<StartedLevel> tmp = playerLevelsRepository.findByPlayerIdAndGameId(userId, levelId);
        StartedLevel startedLevel;
        if (tmp.isPresent()) {
            startedLevel = tmp.get();
        } else {
            startedLevel = StartedLevel.builder()
                    .gameId(levelId)
                    .playerId(userId)
                    .actualPosition(gameRepository.findById(levelId).orElseThrow().getInitialPosition())
                    .build();
            try {
                playerLevelsRepository.save(startedLevel);
            } catch (DuplicateKeyException e) {
                log.info("catched it", e);
                return new GameInfo(playerLevelsRepository.findByPlayerIdAndGameId(userId, levelId).orElseThrow());
            } catch (Exception e) {
                log.info("what is this exception", e);
            }
        }
        return new GameInfo(startedLevel);
    }

    public void move(String lvlId, String username, Move move) {
        String userId = userService.getUserIdFromName(username);
        var lvl = playerLevelsRepository.findByPlayerIdAndGameId(userId, lvlId).orElseThrow();
        System.out.println(lvl);
        lvl.getActualPosition().move(move);
        playerLevelsRepository.save(lvl);
    }

    public void resetGame(String username, String id) {
        String userId = userService.getUserIdFromName(username);
        Game game = gameRepository.findById(id).orElseThrow();
        StartedLevel playerLevel = playerLevelsRepository.findByPlayerIdAndGameId(userId, id).orElseThrow();
        playerLevel.setActualPosition(game.getInitialPosition());
        playerLevelsRepository.save(playerLevel);
    }

    public List<GameInfo> getAllSavedGames() {
        return gameRepository.findAll().stream().map(GameInfo::new).toList();
    }

    public GamesList getAllGames(String username) {
        List<Game> allLevels = gameRepository.findAll();
        String userId = userService.getUserIdFromName(username);
        List<StartedLevel> playerLevels = playerLevelsRepository.findByPlayerId(userId);
        List<String> startedLvlIds = playerLevels.stream().map(StartedLevel::getGameId).toList();
        var gamesList = new GamesList();
        gamesList.setNewLevels(
                allLevels.stream()
                        .filter(game -> !startedLvlIds.contains(game.getId()))
                        .map(GameInfo::new).toList());
        gamesList.setStartedLevels(
                playerLevels.stream()
                        .filter(g -> !g.getActualPosition().isWon())
                        .map(GameInfo::new).toList()
        );
        gamesList.setFinishedLevels(
                playerLevels.stream()
                        .filter(g -> g.getActualPosition().isWon())
                        .map(GameInfo::new).toList()
        );
        return gamesList;
    }

    public Move getHint(String username, String levelId) {
        String userId = userService.getUserIdFromName(username);
        var lvl = playerLevelsRepository.findByPlayerIdAndGameId(userId, levelId).orElseThrow();
        SolverWeight solver = new SolverWeight();
        solver.setup(lvl.getActualPosition());
        solver.solve(false);
        if (!solver.getSolutions().isEmpty()) {
            return solver.getSolutionsAsSortedMoveLists().get(0).get(0);
        }
        System.out.println("no solution found");
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
        playerLevelsRepository.deleteAll();
    }

}
