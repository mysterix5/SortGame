package io.github.mysterix5.sortgame.models.game;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StartedLevel {
    private String gameId;
    private String playerId;
    private boolean started = false;
    private PlayingField actualPosition;
    private List<Move> playedMoves = new ArrayList<>();
}
