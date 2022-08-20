package io.github.mysterix5.sortgame.game.solution;

import io.github.mysterix5.sortgame.models.game.Color;
import io.github.mysterix5.sortgame.models.game.Container;
import io.github.mysterix5.sortgame.models.game.GameProperties;
import io.github.mysterix5.sortgame.models.game.PlayingField;

import java.util.*;

public class StaticMethods {

    // TODO do not allow two times the same color on each other
    // TODO probability for same color raises on last bottles, together with the previous todo, this will even break the program
    // TODO this is probably not solvable
    public static PlayingField createRandomGame(GameProperties gameProperties){
        PlayingField playingField = new PlayingField(gameProperties.getContainerHeight());
        List<Color> colorChoices = new ArrayList<>();
        gameProperties.getColors().forEach(c->{
            for(int i = 0; i<gameProperties.getContainerHeight(); i++)
                colorChoices.add(c);
        });

        List<Container> containers = new ArrayList<>(colorChoices.size());
        for(int i = 0; i<gameProperties.getNColors(); i++){
            containers.add(new Container(gameProperties.getContainerHeight()));
        }
        for(int i = 0; i<gameProperties.getNEmptyContainers(); i++){
            containers.add(new Container(gameProperties.getContainerHeight()));
        }

        int containerIndex = 0;
        var ran = new Random();

        while(!colorChoices.isEmpty()){
            Color color = colorChoices.remove(ran.nextInt(colorChoices.size()));
            containers.get(containerIndex).addColor(color);
            if(containers.get(containerIndex).isFull()){
                containerIndex++;
            }
        }

        playingField.setContainers(containers);
        return playingField;
    }

}
