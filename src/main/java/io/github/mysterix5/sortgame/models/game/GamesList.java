package io.github.mysterix5.sortgame.models.game;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GamesList {
    private List<GameInfo> startedLevels = new ArrayList<>();
    private List<GameInfo> newLevels = new ArrayList<>();
    private List<GameInfo> finishedLevels = new ArrayList<>();
}
