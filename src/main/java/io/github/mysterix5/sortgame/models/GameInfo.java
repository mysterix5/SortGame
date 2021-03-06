package io.github.mysterix5.sortgame.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameInfo {
    private String id;
    private int totalContainers;
    private int colors;
    private List<Container> playingField;

    public GameInfo(Game game){
        this.id = game.getId();
        this.totalContainers = game.getGameProperties().getNEmptyContainers() + game.getGameProperties().getNColors();
        this.colors = game.getGameProperties().getNColors();
        this.playingField = game.getActualPosition().getContainers();
    }
}
