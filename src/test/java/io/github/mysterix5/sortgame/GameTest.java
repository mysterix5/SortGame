package io.github.mysterix5.sortgame;

import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    void createDummyGame() {
        Game game = new Game();
        game.createDummyGame();
        System.out.println(game);
    }
}