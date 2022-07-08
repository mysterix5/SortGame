package io.github.mysterix5.sortgame.game.solution;

import io.github.mysterix5.sortgame.game.Move;
import io.github.mysterix5.sortgame.game.PlayingField;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class PotentialGameStateDepth {
    private List<Move> moves;
    private List<Move> legalMoves;
    private int state;

    public PotentialGameStateDepth(List<Move> legalMoves, List<Move> moves, int state){
        this.moves = moves;
        this.legalMoves = legalMoves;
        this.state = state;
    }

    public PotentialGameStateDepth getNextState(PlayingField playingField, Move move){
        PlayingField playingFieldCopy = new PlayingField(playingField);
        playingFieldCopy.move(move);
        List<Move> movesCopy = new ArrayList<>(moves);
        movesCopy.add(move);
        return new PotentialGameStateDepth(playingFieldCopy.getLegalMoves(), movesCopy, playingFieldCopy.hashCode());
    }

    @Override
    public String toString() {
        return "PotentialGameStateDepth{\n" +
                "moves=" + moves +
                ", legalMoves=" + legalMoves +
                '}';
    }
}
