package io.github.mysterix5.watercolor;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private final PlayingField playingField = new PlayingField();

    // TODO do not allow two times the same color on each other
    // TODO probability for same color raises on last bottles, together with the previous todo, this will even break the program
    // TODO this is probably not solvable
    public void createDummyGame(){
        List<Color> colorChoices = Arrays.stream(Color.values()).collect(Collectors.toList());
        List<Integer> colorStock = new ArrayList<>(colorChoices.size());
        List<Bottle> bottles = new ArrayList<>(colorChoices.size());
        for(int i = 0; i<colorChoices.size(); i++){
            bottles.add(new Bottle());
            colorStock.add(0);
        }

        int bottleIndex = 0;
        var ran = new Random();

        while(!colorChoices.isEmpty()){
            var color = colorChoices.get(ran.nextInt(colorChoices.size()));
            colorStock.set(color.ordinal(), colorStock.get(color.ordinal())+1);
            if(colorStock.get(color.ordinal())>=4){
                colorChoices.remove(color);
            }
            bottles.get(bottleIndex).addColor(color);
            if(bottles.get(bottleIndex).isFull()){
                bottleIndex++;
            }
        }

        this.playingField.setBottles(bottles);
    }

    // TODO save for hint?
    /* TODO brute-force algorithm
        every possible move of first bottle
        abort tree conditions:
            - no possible move
            - win
        how storage
     */
    public boolean isSolvable(){
        if(isWon()){
            return true;
        }

        return false;
    }

    public boolean isWon() {
        return playingField.isWon();
    }

    public PlayingField getPlayingField() {
        return playingField;
    }

    @Override
    public String toString() {
        return "Game{" +
                "playingField=" + playingField +
                '}';
    }

}
