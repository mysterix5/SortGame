package io.github.mysterix5.sortgame.models.game;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Builder
public class StartedLevel {
    @Id
    private String id;
    private String gameId;
    private String playerId;
    private boolean started;
    private PlayingField actualPosition;
    private List<Move> playedMoves;
}
