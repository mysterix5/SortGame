package io.github.mysterix5.sortgame.spring;

import io.github.mysterix5.sortgame.game.GameProperties;
import io.github.mysterix5.sortgame.game.PlayingField;
import io.github.mysterix5.sortgame.game.solution.PotentialGameStateWeight;
import io.github.mysterix5.sortgame.game.solution.SolverWeight;
import io.github.mysterix5.sortgame.game.solution.StaticMethods;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class SolverService {

    public PlayingField createGame(GameCreationData gameCreationData) {
        GameProperties gameProperties = new GameProperties(gameCreationData.getHeight(), gameCreationData.getColors(), gameCreationData.getEmpty());
        while (true) {
            PlayingField playingField = StaticMethods.createDummyGame(gameProperties);
            System.out.println(playingField);

            SolverWeight solver = new SolverWeight();

            solver.setup(playingField);
            solver.solve(false);
            if (solver.getSolutions().isEmpty()) {
                System.out.println("no solution found");
            } else {
                var solutions = solver.getSolutions().stream()
                        .sorted(Comparator.comparingInt(s -> s.getMoves().size()))
                        .map(PotentialGameStateWeight::getMoves).toList();
                System.out.println("length of shortest solution: " + solutions.get(0).size() + ", longest: " + solutions.get(solutions.size() - 1).size());

                return playingField;
            }
        }
    }
}
