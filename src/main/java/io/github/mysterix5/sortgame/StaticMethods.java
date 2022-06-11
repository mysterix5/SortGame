package io.github.mysterix5.sortgame;

import java.util.*;

public class StaticMethods {

    // TODO save for hint?
    /* TODO brute-force algorithm
        every legal move
        abort tree conditions:
            - no possible move
            - win
            - game state has been reached before
        how storage
     */
    private static boolean solutionFound = false;
    public static List<List<Move>> solve(PlayingField playingField){
        solutionFound = false;
        List<List<Move>> solutions = new ArrayList<>();
        recursiveSolutionFinder(playingField, new HashMap<>(), new ArrayList<>(), solutions);

        return solutions;
    }
    private static void recursiveSolutionFinder(PlayingField playingField, Map<Integer, Integer> states, List<Move> moves, List<List<Move>> solutions){
        if(solutionFound)
            return;
        var legalMoves = playingField.getLegalMoves();
        if(playingField.isWon()){
            solutionFound = true;
            if(solutions.isEmpty()){
                solutions.add(moves);
            }else {
                if (moves.size() < solutions.get(0).size()){
                    solutions.set(0, moves);
                }
            }
//            solutions.add(moves);
            return;
        }
        if(legalMoves.isEmpty()){
            return;
        }
        if(states.containsKey(playingField.hashCode())){
            // TODO state already reached, which has less moves? determine 'different' and 'good' solutions
            // but maybe just ignore for now
            return;
        }
        // TODO add to states
        states.put(playingField.hashCode(), moves.size());
        for(Move lm: legalMoves){
            // add move to moves - NEW INSTANCE
            List<Move> movesCopy = new ArrayList<>(moves);
            movesCopy.add(lm);
            // apply move to playingfield - NEW INSTANCE
            PlayingField playingFieldCopy = new PlayingField(playingField);
            playingFieldCopy.move(lm);
            // call recursive
            recursiveSolutionFinder(playingFieldCopy, states, movesCopy, solutions);
        }
    }


    // TODO do not allow two times the same color on each other
    // TODO probability for same color raises on last bottles, together with the previous todo, this will even break the program
    // TODO this is probably not solvable
    public static PlayingField createDummyGame(GameProperties gameProperties){
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
