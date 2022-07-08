package io.github.mysterix5.sortgame.gameCreation;

import io.github.mysterix5.sortgame.game.GameProperties;
import io.github.mysterix5.sortgame.game.PlayingField;
import io.github.mysterix5.sortgame.game.solution.PotentialGameStateWeight;
import io.github.mysterix5.sortgame.game.solution.SolverWeight;
import io.github.mysterix5.sortgame.game.solution.StaticMethods;

import java.util.Comparator;

public class CreateAndSolveGame {

    public static void main(String[] args) {
        GameProperties gameProperties = new GameProperties(6, 12, 2);
        PlayingField playingField = StaticMethods.createDummyGame(gameProperties);

        System.out.println(playingField);

        SolverWeight solver = new SolverWeight();
        solver.setup(playingField);
        solver.solve(false);
        if(solver.getSolutions().isEmpty()){
            System.out.println("no solution found");
        }else{
            System.out.println(solver.getSolutions().get(0).getMoves());
            var solutions = solver.getSolutions().stream()
                    .sorted(Comparator.comparingInt(s -> s.getMoves().size()))
                    .map(PotentialGameStateWeight::getMoves).toList();
            System.out.println("sol1 size: " + solutions.get(0).size() + ", sollast size: " + solutions.get(solutions.size()-1).size());

            PlayingField solutionPlayingField = new PlayingField(playingField);
            solutionPlayingField.move(solver.getSolutions().get(0).getMoves());
            System.out.println(solutionPlayingField);
//            System.out.println(playingField.toInitializationString());
        }
    }
}
