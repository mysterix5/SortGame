package io.github.mysterix5.watercolor;

import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    void createDummyGame() {
        Game game = new Game();
        var field = game.createDummyGame();
        System.out.println(field);
    }
}