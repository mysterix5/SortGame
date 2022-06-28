package io.github.mysterix5.sortgame.game;

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
}
