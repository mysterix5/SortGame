package io.github.mysterix5.sortgame.someTestStuff;

import io.github.mysterix5.sortgame.models.Color;
import io.github.mysterix5.sortgame.models.Container;
import io.github.mysterix5.sortgame.models.GameProperties;
import io.github.mysterix5.sortgame.models.PlayingField;
import io.github.mysterix5.sortgame.game.solution.PotentialGameStateWeight;
import io.github.mysterix5.sortgame.game.solution.SolverWeight;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CreateAndSolveGame {

    public static void main(String[] args) {
//        GameProperties gameProperties = new GameProperties(6, 12, 2);
//        PlayingField playingField = StaticMethods.createDummyGame(gameProperties);

        GameProperties gameProperties = new GameProperties(4, 10, 2);
        int height = gameProperties.getContainerHeight();

        PlayingField playingField = new PlayingField(height);
        List<Container> containers = new ArrayList<>();
        containers.add(new Container(height, new ArrayList<>(List.of(Color.GREEN, Color.PINK, Color.PINK, Color.ORANGE))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.ORANGE, Color.BROWN, Color.YELLOW, Color.RED))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.ORANGE, Color.BROWN, Color.LIME, Color.RED))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.BLUE, Color.PURPLE, Color.PURPLE, Color.BLUE))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.BLUE, Color.ORANGE, Color.GREY, Color.YELLOW))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.GREEN, Color.LIME, Color.LIME, Color.YELLOW))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.GREY, Color.BROWN, Color.LIME, Color.PINK))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.RED, Color.BLUE, Color.PINK, Color.BROWN))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.GREEN, Color.PURPLE, Color.RED, Color.GREY))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.GREEN, Color.YELLOW, Color.PURPLE, Color.GREY))));
        containers.add(new Container(height, new ArrayList<>(List.of())));
        containers.add(new Container(height, new ArrayList<>(List.of())));
        playingField.setContainers(containers);

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
