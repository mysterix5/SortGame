package io.github.mysterix5.sortgame.models.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameInfo {
    private String id;
    private List<Container> playingField;

    public GameInfo(Game game){
        this.id = game.getId();
        this.playingField = game.getActualPosition().getContainers();
    }

    public GameInfo(StartedLevel startedLevel) {
        this.id = startedLevel.getGameId();
        this.playingField = startedLevel.getActualPosition().getContainers();
    }
}
