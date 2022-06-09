package io.github.mysterix5.sortgame;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GameTest {

    @Test
    void createDummyGame() {
        Game game = new Game(new GameProperties(4, 3, 2));
        game.createDummyGame();
        System.out.println(game);
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
        game.setInitialPosition(playingField);

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
        containers2.add(new Container(4, new ArrayList<>(List.of(Color.RED, Color.BLUE, Color.YELLOW, Color.YELLOW))));
        containers2.add(new Container(4, new ArrayList<>(List.of(Color.BLUE, Color.YELLOW, Color.RED, Color.BLUE))));
        containers2.add(new Container(4, new ArrayList<>(List.of(Color.RED, Color.YELLOW, Color.BLUE, Color.RED))));
        containers2.add(new Container(4));
        playingField2.setContainers(containers2);

        System.out.println(playingField.hashCode());
        System.out.println(playingField2.hashCode());
        assertThat(playingField.hashCode()).isEqualTo(playingField2.hashCode());
    }
}