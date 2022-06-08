package io.github.mysterix5.sortgame;

import lombok.Data;

import java.util.*;

@Data
public class Game {

    private GameProperties gameProperties;

    private PlayingField initialPosition = new PlayingField();
    private PlayingField actualPosition = new PlayingField();

    public Game(){}
    public Game(GameProperties gameProperties){
        this.gameProperties = gameProperties;
    }

    // TODO do not allow two times the same color on each other
    // TODO probability for same color raises on last bottles, together with the previous todo, this will even break the program
    // TODO this is probably not solvable
    public void createDummyGame(){
        this.initialPosition = new PlayingField();
        List<Color> colorChoices = new ArrayList<>();
        gameProperties.getColors().stream().forEach(c->{
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

        this.initialPosition.setContainers(containers);
        this.actualPosition = new PlayingField(initialPosition);
    }

    // TODO save for hint?
    /* TODO brute-force algorithm
        every legal move
        abort tree conditions:
            - no possible move
            - win
            - game state has been reached before
        how storage
     */
    public boolean isSolvable(){
        if(isWon()){
            return true;
        }

        return false;
    }

    // TODO same algorithm as for permutation kata possible?
    private void recursiveSolveSearch(){

    }

    public boolean isWon() {
        return actualPosition.isWon();
    }


}