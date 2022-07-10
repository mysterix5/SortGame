package io.github.mysterix5.sortgame;

import io.github.mysterix5.sortgame.game.solution.PotentialGameStateWeight;
import io.github.mysterix5.sortgame.game.solution.SolverWeight;
import io.github.mysterix5.sortgame.game.solution.StaticMethods;
import io.github.mysterix5.sortgame.models.*;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class GameTest {

    @Test
    void createDummyGame() {
        PlayingField playingField = StaticMethods.createRandomGame(new GameProperties(4, 3, 2));
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
    void hashcodeMappingTest(){
        int nColors = 3;
        int height = 2;
        int nContainers = 1;

        int combinations = (int) Math.pow(nColors, nContainers*height);
        Set<Integer> hashes = new HashSet<>();

        for(int cont = 0; cont<nContainers; cont++){
            for(int h = 0; h<height; h++){
                // choose every color combination
                for(int c = 0; c<nColors; c++){
                    //TODO try some permutation stuff
                }
            }
        }
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

        SolverWeight solver = new SolverWeight();
        solver.setup(playingField);
        solver.solve(false);
        var solutions = solver.getSolutionsAsSortedMoveLists();

        for(var s: solutions)
            System.out.println(s);
        System.out.println(solutionsToInitializationString(solutions));

        var sol = List.of(new Move(0, 3), new Move(1, 0), new Move(2, 1), new Move(0, 2), new Move(2, 0), new Move(2, 3), new Move(1, 2), new Move(1, 3), new Move(0, 1), new Move(0, 2));

        assertThat(solutions).contains(sol);
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

        SolverWeight solver = new SolverWeight();
        solver.setup(playingField);
        solver.solve(false);
        var solutions = solver.getSolutionsAsSortedMoveLists();

        var sol = List.of(new Move(0, 8), new Move(2, 9), new Move(5, 9), new Move(4, 5), new Move(0, 4), new Move(0, 2), new Move(2, 0), new Move(6, 9), new Move(4, 6), new Move(7, 4), new Move(5, 7), new Move(8, 2), new Move(3, 8), new Move(3, 4), new Move(3, 8), new Move(4, 3), new Move(6, 4), new Move(5, 6), new Move(3, 5), new Move(5, 3), new Move(0, 5), new Move(1, 0), new Move(1, 8), new Move(7, 6), new Move(2, 7), new Move(0, 2), new Move(1, 2));

        System.out.println(solutionsToInitializationString(solutions));

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

        SolverWeight solver = new SolverWeight();
        solver.setup(playingField);
        solver.solve(false);
        var solutions = solver.getSolutionsAsSortedMoveLists();

        System.out.println(solutionsToInitializationString(solutions));
        var sol = List.of(new Move(0, 8), new Move(2, 0), new Move(2, 9), new Move(6, 9), new Move(7, 9), new Move(5, 7), new Move(8, 6), new Move(5, 8), new Move(6, 5), new Move(7, 8), new Move(6, 7), new Move(3, 6), new Move(3, 8), new Move(2, 3), new Move(1, 2), new Move(1, 7), new Move(1, 6), new Move(4, 1), new Move(4, 9), new Move(4, 6), new Move(5, 4), new Move(0, 5), new Move(0, 4), new Move(0, 2), new Move(3, 0), new Move(3, 5), new Move(0, 1));

        assertThat(solutions).contains(sol);
    }

    @Test
    public void hashcodeTest(){
        int height = 4;
        PlayingField playingField = new PlayingField(height);
        List<Container> containers = new ArrayList<>();
        containers.add(new Container(height, new ArrayList<>(List.of(Color.PURPLE, Color.BLUE, Color.RED, Color.GREY))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.PURPLE, Color.BLUE, Color.PURPLE, Color.BLUE))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.GREY, Color.YELLOW, Color.GREEN, Color.YELLOW))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.GREEN, Color.GREEN, Color.RED, Color.GREY))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.PURPLE, Color.BLUE, Color.YELLOW, Color.GREY))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.RED, Color.YELLOW, Color.GREEN, Color.RED))));
        containers.add(new Container(height, new ArrayList<>(List.of())));
        containers.add(new Container(height, new ArrayList<>(List.of())));
        playingField.setContainers(containers);

        PlayingField playingField2 = new PlayingField(height);
        containers = new ArrayList<>();
        containers.add(new Container(height, new ArrayList<>(List.of(Color.PURPLE, Color.BLUE, Color.RED, Color.GREY))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.PURPLE, Color.BLUE, Color.PURPLE, Color.BLUE))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.GREY, Color.YELLOW, Color.GREEN, Color.YELLOW))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.GREEN, Color.GREEN, Color.RED, Color.GREY))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.PURPLE, Color.BLUE, Color.YELLOW, Color.GREY))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.RED, Color.YELLOW, Color.GREEN, Color.RED))));
        containers.add(new Container(height, new ArrayList<>(List.of())));
        containers.add(new Container(height, new ArrayList<>(List.of())));
        playingField2.setContainers(containers);

        playingField.move(new Move(0,6));
        playingField2.move(new Move(0,6));

        playingField2.move(new Move(6,7));
        playingField2.move(new Move(7,6));

        System.out.println(playingField.hashCode());
        System.out.println(playingField2.hashCode());
        assertThat(playingField.hashCode()).isEqualTo(playingField2.hashCode());
    }

}