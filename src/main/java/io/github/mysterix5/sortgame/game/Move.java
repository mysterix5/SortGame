package io.github.mysterix5.sortgame.game;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Move {
    private int from;
    private int to;

    @Override
    public String toString() {
        return "(" + from + ", " + to + ")";
    }
}
