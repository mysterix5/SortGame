package io.github.mysterix5.sortgame;

import lombok.Data;

import java.util.*;

@Data
public class Game {

    private GameProperties gameProperties;

    private PlayingField initialPosition;
    private PlayingField actualPosition;

    public Game(){}
    public Game(GameProperties gameProperties){
        this.gameProperties = gameProperties;
    }

    // TODO do not allow two times the same color on each other
    // TODO probability for same color raises on last bottles, together with the previous todo, this will even break the program
    // TODO this is probably not solvable
    public void createDummyGame(){
        this.initialPosition = new PlayingField(gameProperties.getContainerHeight());
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
    private void recursiveSolutionFinder(PlayingField playingField, Map<Integer, Integer> states, List<Move> moves, List<List<Move>> solves){
        var legalMoves = playingField.getLegalMoves();
        if(legalMoves.isEmpty()){

            return;
        }
        if(playingField.isWon()){
            // add to solvings
            return;
        }
        if(states.containsKey(playingField.hashCode())){
            // TODO state already reached, which has less moves?
            // but maybe just ignore for now
            return;
        }
        else {
            // TODO add to states
        }
        // For every legal move call recursive
        // add move to moves - NEW INSTANCE
        // apply move to playingfield - NEW INSTANCE
        // call
    }

    // TODO same algorithm as for permutation kata possible?
    private void recursiveSolveSearch(){

    }

    public boolean isWon() {
        return actualPosition.isWon();
    }


}
