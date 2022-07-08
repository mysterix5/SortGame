package io.github.mysterix5.sortgame.game.solution;

import io.github.mysterix5.sortgame.game.PlayingField;
import io.github.mysterix5.sortgame.spring.SolverRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Component
@RequiredArgsConstructor
public class SolverDb {
    private final SolverRepository solverRepository;

    private PlayingField initialPlayingField;
    private List<PotentialGameStateDb> solutions = new ArrayList<>();
    private List<String> gameStatesToVisit = new ArrayList<>();
    private Set<String> visitedStates = new HashSet<>();

    int againOnVisitedState = 0;

    public void setup(PlayingField playingField) {
        initialPlayingField = playingField;
        solutions.clear();
        gameStatesToVisit.clear();
        visitedStates.clear();
        var stateTmp = new PotentialGameStateDb(initialPlayingField.getLegalMoves(), new ArrayList<>(), Integer.toString(initialPlayingField.hashCode()));
        gameStatesToVisit.add(stateTmp.getState());
        solverRepository.save(stateTmp);
    }

    public void solve(boolean best) {
        try {
            while (!gameStatesToVisit.isEmpty()) {
                PotentialGameStateDb gameState = solverRepository.findById(gameStatesToVisit.get(gameStatesToVisit.size() - 1)).orElseThrow();
                PlayingField playingField = new PlayingField(initialPlayingField);
                playingField.move(gameState.getMoves());

//                System.out.println("---- enter main while again -----  " + playingField.hashCode() + "\n" + playingField);

                if (playingField.isWon()) {
                    System.out.println("found solution!");
                    solutions.add(gameState);
                    // TODO change this to continue and find fastest solution
                    break;
                }

                visitedStates.add(gameState.getState());
//            System.out.println("nLegitMoves: " + gameState.getLegitMoves().size());

                if (gameState.getLegalMoves().size() < 2) {
//                    System.out.println("remove state because not enough legal moves");
                    gameStatesToVisit.remove(gameStatesToVisit.size() - 1);
                    solverRepository.delete(gameState);
                }

                while (!gameState.getLegalMoves().isEmpty()) {
//                    System.out.println("add new state with move: " + gameState.getLegalMoves().get(0));
                    PotentialGameStateDb gameStateTmp = gameState.getNextState(playingField, gameState.getLegalMoves().remove(0));
                    if (visitedStates.contains(gameStateTmp.getState())) {
//                        System.out.println("state already visited!");
                        againOnVisitedState++;
                    } else if (gameStateTmp.getLegalMoves().isEmpty()) {
//                        System.out.println("no legal moves in this position");
                    } else {
                        gameStatesToVisit.add(gameStateTmp.getState());
                        solverRepository.save(gameStateTmp);
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
