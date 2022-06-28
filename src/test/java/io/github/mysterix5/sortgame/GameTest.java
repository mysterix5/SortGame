package io.github.mysterix5.sortgame;

import io.github.mysterix5.sortgame.game.*;
import io.github.mysterix5.sortgame.game.solution.Solver;
import io.github.mysterix5.sortgame.game.solution.StaticMethods;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class GameTest {

    @Test
    void createDummyGame() {
        PlayingField playingField = StaticMethods.createDummyGame(new GameProperties(4, 3, 2));
        System.out.println(playingField);
    }

    @Test
    void miniSolvable(){
        Game game = new Game(new GameProperties(4, 3, 1));
        PlayingField playingField = new PlayingField(game.getGameProperties().getContainerHeight());
        List<Container> containers = new ArrayList<>();
        containers.add(new Container(game.getGameProperties().getContainerHeight(), new ArrayList<>(List.of(Color.RED, Color.BLUE, Color.YELLOW, Color.YELLOW))));
        containers.add(new Container(game.getGameProperties().getContainerHeight(), new ArrayList<>(List.of(Color.BLUE, Color.YELLOW, Color.RED, Color.BLUE))));
        containers.add(new Container(game.getGameProperties().getContainerHeight(), new ArrayList<>(List.of(Color.RED, Color.YELLOW, Color.BLUE, Color.RED))));
        containers.add(new Container(game.getGameProperties().getContainerHeight()));
        playingField.setContainers(containers);
        game.setPosition(playingField);

        System.out.println(game.getInitialPosition());

        playingField.move(new Move(0,3));
        System.out.println(playingField);
        playingField.move(new Move(1,0));
        System.out.println(playingField);

        playingField.move(List.of(
                new Move(2,1),
                new Move(2,0),
                new Move(2,3),
                new Move(1,2),
                new Move(1,3)
        ));
        System.out.println(playingField);
        playingField.move(List.of(
                new Move(0,1)
        ));

        System.out.println(playingField);
        System.out.println("won? " + playingField.isWon());

        playingField.move(List.of(
                new Move(0,2)
        ));
        System.out.println(playingField);
        System.out.println(playingField.hashCode());
        playingField.move(new Move(1,0));
        System.out.println(playingField.hashCode());
        playingField.move(new Move(0,1));
        System.out.println(playingField.hashCode());
        System.out.println(playingField);
        System.out.println("won? " + playingField.isWon());
    }

    @Test
    public void playingFieldHashcodeEquals(){
        PlayingField playingField = new PlayingField(4);
        List<Container> containers = new ArrayList<>();
        containers.add(new Container(4, new ArrayList<>(List.of(Color.RED, Color.BLUE, Color.YELLOW, Color.YELLOW))));
        containers.add(new Container(4, new ArrayList<>(List.of(Color.BLUE, Color.YELLOW, Color.RED, Color.BLUE))));
        containers.add(new Container(4, new ArrayList<>(List.of(Color.RED, Color.YELLOW, Color.BLUE, Color.RED))));
        containers.add(new Container(4));
        playingField.setContainers(containers);


        PlayingField playingField2 = new PlayingField(4);
        List<Container> containers2 = new ArrayList<>();
        containers2.add(new Container(4, new ArrayList<>(List.of(Color.RED, Color.BLUE, Color.YELLOW))));
        containers2.add(new Container(4, new ArrayList<>(List.of(Color.BLUE, Color.YELLOW, Color.RED, Color.BLUE))));
        containers2.add(new Container(4, new ArrayList<>(List.of(Color.RED, Color.YELLOW, Color.BLUE, Color.RED))));
        containers2.add(new Container(4, new ArrayList<>(List.of(Color.YELLOW))));
        playingField2.setContainers(containers2);

        playingField2.move(new Move(3,0));

        System.out.println(playingField);
        System.out.println(playingField2);
        System.out.println(playingField.hashCode());
        System.out.println(playingField2.hashCode());
        assertThat(playingField.hashCode()).isEqualTo(playingField2.hashCode());
    }


    @Test
    void solve(){
        int height = 4;
        PlayingField playingField = new PlayingField(height);
        List<Container> containers = new ArrayList<>();
        containers.add(new Container(height, new ArrayList<>(List.of(Color.RED, Color.BLUE, Color.YELLOW, Color.YELLOW))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.BLUE, Color.YELLOW, Color.RED, Color.BLUE))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.RED, Color.YELLOW, Color.BLUE, Color.RED))));
        containers.add(new Container(height));
        playingField.setContainers(containers);

        System.out.println(playingField);

        List<List<Move>> solutions;
        solutions = StaticMethods.solve(playingField);
        System.out.println(playingField);
        for(var s: solutions)
            System.out.println(s);
        System.out.println(solutionsToInitializationString(solutions));

        assertThat(solutions).contains(List.of(new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(2, 0), new Move(2, 3), new Move(1, 2), new Move(1, 3), new Move(0, 1), new Move(0, 2)));
    }

    public String solutionsToInitializationString(List<List<Move>> solutions){
        StringBuilder stringBuilder = new StringBuilder();
        for(var s: solutions){
            stringBuilder.append("List.of(").append(s.stream().
                    map(m -> "new Move" + m).
                    collect(Collectors.joining(", "))).append(");\n");
        }
        return stringBuilder.toString();
    }

    @Test
    void solveDummyGame() {
        PlayingField playingField = StaticMethods.createDummyGame(new GameProperties(4, 10, 2));
        System.out.println(playingField);

        List<List<Move>> solutions = StaticMethods.solve(playingField);
        System.out.println(playingField);
        for(var s: solutions){
            System.out.println(s);
        }
        System.out.println(solutionsToInitializationString(solutions));

        System.out.println(playingField.toInitializationString());
    }

    @Test
    void littleTest(){
        PlayingField playingField = new PlayingField(4);
        List<Container> containers = new ArrayList<>();
        containers.add(new Container(4, new ArrayList<>(List.of(Color.RED, Color.RED, Color.ORANGE, Color.BLUE))));
        containers.add(new Container(4, new ArrayList<>(List.of(Color.PURPLE, Color.PURPLE, Color.GREY, Color.PURPLE))));
        containers.add(new Container(4, new ArrayList<>(List.of(Color.PURPLE, Color.BLUE, Color.RED, Color.YELLOW))));
        containers.add(new Container(4, new ArrayList<>(List.of(Color.GREY, Color.GREEN, Color.GREY, Color.GREY))));
        containers.add(new Container(4, new ArrayList<>(List.of(Color.ORANGE, Color.GREEN, Color.ORANGE, Color.BROWN))));
        containers.add(new Container(4, new ArrayList<>(List.of(Color.RED, Color.GREEN, Color.BROWN, Color.YELLOW))));
        containers.add(new Container(4, new ArrayList<>(List.of(Color.BROWN, Color.ORANGE, Color.YELLOW, Color.YELLOW))));
        containers.add(new Container(4, new ArrayList<>(List.of(Color.BLUE, Color.BLUE, Color.BROWN, Color.GREEN))));
        containers.add(new Container(4, new ArrayList<>(List.of())));
        containers.add(new Container(4, new ArrayList<>(List.of())));
        playingField.setContainers(containers);

        List<List<Move>> solutions = StaticMethods.solve(playingField);
        var sol = List.of(new Move(0, 8), new Move(2, 9), new Move(5, 9), new Move(4, 5), new Move(0, 4), new Move(0, 2), new Move(2, 0), new Move(6, 9), new Move(4, 6), new Move(7, 4), new Move(5, 7), new Move(8, 2), new Move(0, 8), new Move(3, 0), new Move(3, 4), new Move(0, 3), new Move(1, 0), new Move(1, 3), new Move(0, 1), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(1, 3), new Move(0, 1), new Move(2, 0), new Move(1, 2), new Move(0, 1), new Move(2, 0), new Move(3, 2), new Move(4, 3), new Move(6, 4), new Move(5, 6), new Move(3, 5), new Move(5, 3), new Move(7, 6), new Move(1, 7), new Move(0, 1), new Move(2, 0), new Move(1, 2), new Move(0, 1), new Move(2, 0), new Move(3, 2), new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(1, 3), new Move(0, 1), new Move(2, 0), new Move(1, 2), new Move(0, 1), new Move(2, 0), new Move(3, 2), new Move(4, 3), new Move(0, 4), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(1, 3), new Move(0, 1), new Move(2, 0), new Move(1, 2), new Move(0, 1), new Move(2, 0), new Move(3, 2), new Move(4, 3), new Move(1, 4), new Move(0, 1), new Move(2, 0), new Move(1, 2), new Move(0, 1), new Move(2, 0), new Move(3, 2), new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(1, 3), new Move(0, 1), new Move(2, 0), new Move(1, 2), new Move(0, 1), new Move(2, 0), new Move(3, 2), new Move(4, 3), new Move(2, 4), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(3, 1), new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(4, 2), new Move(5, 4), new Move(0, 5), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(1, 3), new Move(0, 1), new Move(2, 0), new Move(1, 2), new Move(0, 1), new Move(2, 0), new Move(3, 2), new Move(4, 3), new Move(0, 4), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(0, 8));

        assertThat(solutions).contains(sol);
    }
    @Test
    void littleTest2(){
        int height = 4;
        PlayingField playingField = new PlayingField(height);
        List<Container> containers = new ArrayList<>();
        containers.add(new Container(height, new ArrayList<>(List.of(Color.GREEN, Color.ORANGE, Color.PURPLE, Color.ORANGE))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.GREY, Color.BROWN, Color.GREEN, Color.GREEN))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.GREEN, Color.RED, Color.BLUE, Color.PURPLE))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.PURPLE, Color.RED, Color.YELLOW, Color.GREY))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.GREY, Color.BLUE, Color.RED, Color.RED))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.PURPLE, Color.ORANGE, Color.YELLOW, Color.YELLOW))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.GREY, Color.BROWN, Color.ORANGE, Color.BLUE))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.BROWN, Color.BROWN, Color.YELLOW, Color.BLUE))));
        containers.add(new Container(height, new ArrayList<>(List.of())));
        containers.add(new Container(height, new ArrayList<>(List.of())));
        playingField.setContainers(containers);

        List<List<Move>> solutions = StaticMethods.solve(playingField);
        var sol = List.of(new Move(0, 8), new Move(0, 9), new Move(2, 9), new Move(6, 2), new Move(6, 0), new Move(8, 0), new Move(2, 8), new Move(4, 2), new Move(4, 8), new Move(3, 4), new Move(5, 3), new Move(7, 8), new Move(3, 5), new Move(7, 3), new Move(6, 7), new Move(4, 6), new Move(0, 4), new Move(1, 0), new Move(1, 7), new Move(1, 6), new Move(0, 1), new Move(2, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(3, 0), new Move(1, 3), new Move(3, 1), new Move(3, 9), new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(4, 2), new Move(0, 4), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(1, 3), new Move(0, 1), new Move(2, 0), new Move(1, 2), new Move(0, 1), new Move(2, 0), new Move(3, 2), new Move(4, 3), new Move(0, 4), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(1, 3), new Move(0, 1), new Move(2, 0), new Move(1, 2), new Move(0, 1), new Move(2, 0), new Move(3, 2), new Move(5, 0), new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(0, 5), new Move(0, 1), new Move(2, 0), new Move(1, 2), new Move(0, 1), new Move(2, 0), new Move(3, 2), new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(1, 3), new Move(0, 1), new Move(2, 0), new Move(1, 2), new Move(0, 1), new Move(2, 0), new Move(3, 2), new Move(4, 3), new Move(0, 4), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(1, 3), new Move(0, 1), new Move(2, 0), new Move(1, 2), new Move(0, 1), new Move(2, 0), new Move(3, 2), new Move(4, 3), new Move(1, 4), new Move(0, 1), new Move(2, 0), new Move(1, 2), new Move(0, 1), new Move(2, 0), new Move(3, 2), new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(1, 3), new Move(0, 1), new Move(2, 0), new Move(1, 2), new Move(0, 1), new Move(2, 0), new Move(3, 2), new Move(4, 3), new Move(2, 4), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(3, 1), new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(4, 2), new Move(5, 2), new Move(0, 4), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(1, 3), new Move(0, 1), new Move(2, 0), new Move(1, 2), new Move(0, 1), new Move(2, 0), new Move(3, 2), new Move(4, 3), new Move(0, 4), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(1, 3), new Move(0, 1), new Move(2, 0), new Move(1, 2), new Move(0, 1), new Move(2, 0), new Move(3, 2), new Move(5, 3), new Move(0, 5), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(1, 0), new Move(2, 1), new Move(3, 2), new Move(1, 3), new Move(0, 1), new Move(2, 0), new Move(0, 9));

        assertThat(solutions).contains(sol);
    }



    @Test
    void testNewSolver() {
        GameProperties gameProperties = new GameProperties(4, 6, 2);
        PlayingField playingField = StaticMethods.createDummyGame(gameProperties);
        System.out.println(playingField);

        Solver solver = new Solver();
        solver.setup(playingField);
        solver.solve();
        if(solver.getSolutions().isEmpty()){
            System.out.println("no solution found");
        }else{
            System.out.println(solver.getSolutions().get(0).getMoves());
            System.out.println(solver.getSolutions().get(0).getPlayingField());
        }

//        System.out.println(solutionsToInitializationString(solver.getSolutions()));
        System.out.println("-----------");
        List<List<Move>> solutions = StaticMethods.solve(playingField);
        for(var s: solutions){
            System.out.println(s);
        }

        System.out.println(playingField.toInitializationString());
    }

}