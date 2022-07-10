package io.github.mysterix5.sortgame.game.solution;

import io.github.mysterix5.sortgame.models.Move;
import io.github.mysterix5.sortgame.models.PlayingField;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class PotentialGameStateWeight {
    private List<Move> moves;
    private List<Move> legalMoves;
    private String identifier;

    public PotentialGameStateWeight(List<Move> legalMoves, List<Move> moves, String identifier){
        this.moves = moves;
        this.legalMoves = legalMoves;
        this.identifier = identifier;
    }

    public PotentialGameStateWeight getNextState(PlayingField playingField, Move move){
        PlayingField playingFieldCopy = new PlayingField(playingField);
        playingFieldCopy.move(move);
        List<Move> movesCopy = new ArrayList<>(moves);
        movesCopy.add(move);
        return new PotentialGameStateWeight(playingFieldCopy.getLegalMoves(), movesCopy, playingFieldCopy.permutationIgnoringIdentifier());
    }

    @Override
    public String toString() {
        return "PotentialGameStateDepth{\n" +
                "moves=" + moves +
                ", legalMoves=" + legalMoves +
                '}';
    }
}
