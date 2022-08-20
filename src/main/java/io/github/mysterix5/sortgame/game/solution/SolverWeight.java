package io.github.mysterix5.sortgame.game.solution;

import io.github.mysterix5.sortgame.models.game.Move;
import io.github.mysterix5.sortgame.models.game.PlayingField;
import lombok.Data;

import java.util.*;

@Data
public class SolverWeight {
    private PlayingField initialPlayingField;
    private List<PotentialGameStateWeight> solutions = new ArrayList<>();
    private List<PotentialGameStateWeight> gameStatesToVisit = new ArrayList<>();
    private Set<String> visitedStates = new HashSet<>();

    int againOnVisitedState = 0;

    public void setup(PlayingField playingField) {
        initialPlayingField = playingField;
        solutions.clear();
        gameStatesToVisit.clear();
        visitedStates.clear();
        gameStatesToVisit.add(new PotentialGameStateWeight(initialPlayingField.getLegalMoves(), new ArrayList<>(), initialPlayingField.permutationIgnoringIdentifier()));
    }

    public void solve(boolean onlyFirst) {
        try {
            while (!gameStatesToVisit.isEmpty()) {
                PotentialGameStateWeight gameState = gameStatesToVisit.get(gameStatesToVisit.size() - 1);
                PlayingField playingField = new PlayingField(initialPlayingField);
                playingField.move(gameState.getMoves());

//                System.out.println("---- enter main while again -----  " + gameState.getIdentifier());

                if (playingField.isWon()) {
//                    System.out.println("found solution!");
                    solutions.add(gameState);
                    gameStatesToVisit.remove(gameState);
                    if(onlyFirst){
                        break;
                    }else{
                        continue;
                    }
                }

                visitedStates.add(playingField.permutationIgnoringIdentifier());
//            System.out.println("nLegitMoves: " + gameState.getLegitMoves().size());

                if (gameState.getLegalMoves().size() < 2) {
//                    System.out.println("remove state because not enough legal moves");
                    gameStatesToVisit.remove(gameState);
                }

                while (!gameState.getLegalMoves().isEmpty()) {
//                    System.out.println("add new state with move: " + gameState.getLegalMoves().get(0));
                    PotentialGameStateWeight gameStateTmp = gameState.getNextState(playingField, gameState.getLegalMoves().remove(0));
                    if (visitedStates.contains(gameStateTmp.getIdentifier())) {
//                        System.out.println("state already visited!");
                        againOnVisitedState++;
                    } else if (gameStateTmp.getLegalMoves().isEmpty()) {
//                        System.out.println("no legal moves in this position");
                    } else {
                        gameStatesToVisit.add(gameStateTmp);
                        break;
                    }

                }
            }
        } catch (OutOfMemoryError error) {
            System.out.println(error);
        }
        if(solutions.isEmpty()){
            System.out.println("NO SOLUTION FOUND");
        }
        System.out.println("states to visit: " + gameStatesToVisit.size());
        System.out.println("visited states: " + visitedStates.size());
        System.out.println("againOnVisitedState: " + againOnVisitedState);
    }

    public List<List<Move>> getSolutionsAsSortedMoveLists(){
        return getSolutions().stream().map(PotentialGameStateWeight::getMoves).sorted(Comparator.comparingInt(List::size)).toList();
    }
}
