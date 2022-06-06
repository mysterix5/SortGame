package io.github.mysterix5.sortgame;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Move {
    private int from;
    private int to;
}
