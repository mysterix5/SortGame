package io.github.mysterix5.sortgame;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
public class GameProperties {
    private int containerHeight;
    private int nEmptyContainers;
    private final List<Color> colors = new ArrayList<>();
    public GameProperties(int containerHeight, int nColors, int nEmptyContainers){
        this.containerHeight = containerHeight;
        this.nEmptyContainers = nEmptyContainers;
        if(nColors<=1 || nColors >=Color.values().length){
            throw new RuntimeException("choose between 2 and " + Color.values().length + " colors for your game.");
        }
        colors.addAll(Arrays.stream(Color.values()).toList().subList(0,nColors));
    }

    public int getNColors(){
        return colors.size();
    }
}
