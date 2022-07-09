package io.github.mysterix5.sortgame.game.solution;

import io.github.mysterix5.sortgame.models.PlayingField;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class SolverWeight {
    private PlayingField initialPlayingField;
    private List<PotentialGameStateWeight> solutions = new ArrayList<>();
    private List<PotentialGameStateWeight> gameStatesToVisit = new ArrayList<>();
    private Set<Integer> visitedStates = new HashSet<>();

    int againOnVisitedState = 0;

    public void setup(PlayingField playingField) {
        initialPlayingField = playingField;
        solutions.clear();
        gameStatesToVisit.clear();
        visitedStates.clear();
        gameStatesToVisit.add(new PotentialGameStateWeight(initialPlayingField.getLegalMoves(), new ArrayList<>(), initialPlayingField.hashCodeIgnoreOrder()));
    }

    public void solve(boolean best) {
        try {
            while (!gameStatesToVisit.isEmpty()) {
                PotentialGameStateWeight gameState = gameStatesToVisit.get(gameStatesToVisit.size() - 1);
                PlayingField playingField = new PlayingField(initialPlayingField);
                playingField.move(gameState.getMoves());

//                System.out.println("---- enter main while again -----  " + playingField.hashCode() + "\n" + playingField);

                if (playingField.isWon()) {
//                    System.out.println("found solution!");
                    solutions.add(gameState);
                    gameStatesToVisit.remove(gameState);
                    // TODO change this to continue and find fastest solution
                    continue;
                }

                visitedStates.add(playingField.hashCodeIgnoreOrder());
//            System.out.println("nLegitMoves: " + gameState.getLegitMoves().size());

                if (gameState.getLegalMoves().size() < 2) {
//                    System.out.println("remove state because not enough legal moves");
                    gameStatesToVisit.remove(gameState);
                }

                while (!gameState.getLegalMoves().isEmpty()) {
//                    System.out.println("add new state with move: " + gameState.getLegalMoves().get(0));
                    PotentialGameStateWeight gameStateTmp = gameState.getNextState(playingField, gameState.getLegalMoves().remove(0));
                    if (visitedStates.contains(gameStateTmp.getState())) {
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
        System.out.println("states to visit: " + gameStatesToVisit.size());
        System.out.println("visited states: " + visitedStates.size());
        System.out.println("againOnVisitedState: " + againOnVisitedState);
    }
}
