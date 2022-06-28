package io.github.mysterix5.sortgame.spring;

import io.github.mysterix5.sortgame.game.Move;
import lombok.Data;

@Data
public class GamePutter {
    private String id;
    private Move move;
}
