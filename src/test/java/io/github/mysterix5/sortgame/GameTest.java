package io.github.mysterix5.sortgame;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
    }
}