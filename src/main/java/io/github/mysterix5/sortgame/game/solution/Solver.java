package io.github.mysterix5.sortgame.game.solution;

import io.github.mysterix5.sortgame.game.Move;
import io.github.mysterix5.sortgame.game.PlayingField;
import lombok.Data;

import java.util.*;

@Data
public class Solver {
    private PlayingField initialPlayingField;
    private List<PotentialGameState> solutions = new ArrayList<>();
    private List<PotentialGameState> gameStatesToVisit = new ArrayList<>();
    private Set<Integer> visitedStates = new HashSet<>();

    int againOnVisitedState = 0;

    public void setup(PlayingField playingField) {
        initialPlayingField = playingField;
        solutions.clear();
        gameStatesToVisit.clear();
        visitedStates.clear();
        gameStatesToVisit.add(new PotentialGameState(initialPlayingField, new ArrayList<>()));
    }

    public void solve() {

        while (!gameStatesToVisit.isEmpty()) {
            PotentialGameState gameState = gameStatesToVisit.remove(gameStatesToVisit.size()-1);
//            PotentialGameState gameState = gameStatesToVisit.remove(0);

            int hc = gameState.getPlayingField().hashCode();
            if (visitedStates.contains(hc)) {
                againOnVisitedState++;
                continue;
            }
            visitedStates.add(hc);

            if (gameState.getPlayingField().isWon()) {
                solutions.add(gameState);
                // change this to continue and find fastest solution later
                break;
            }

            for (Move m : gameState.getPlayingField().getLegalMoves()) {
                gameStatesToVisit.add(gameState.getNextState(m));
            }
        }
        System.out.println("states to visit: " + gameStatesToVisit.size());
        System.out.println("visited states: " + visitedStates.size());
        System.out.println("againOnVisitedState: " + againOnVisitedState);
    }
}
