package io.github.mysterix5.sortgame.game.solution;

import io.github.mysterix5.sortgame.game.Move;
import io.github.mysterix5.sortgame.game.PlayingField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@Document(collection = "solvingGameStates")
public class PotentialGameStateDb {
    @Id
    private String state;
    private int gameId;
    private List<Move> moves;
    private List<Move> legalMoves;

    public PotentialGameStateDb(List<Move> legalMoves, List<Move> moves, String state){
        this.moves = moves;
        this.legalMoves = legalMoves;
        this.state = state;
    }

    public PotentialGameStateDb getNextState(PlayingField playingField, Move move){
        PlayingField playingFieldCopy = new PlayingField(playingField);
        playingFieldCopy.move(move);
        List<Move> movesCopy = new ArrayList<>(moves);
        movesCopy.add(move);
        return new PotentialGameStateDb(playingFieldCopy.getLegalMoves(), movesCopy, Integer.toString(playingFieldCopy.hashCode()));
    }

    @Override
    public String toString() {
        return "PotentialGameStateDepth{\n" +
                "moves=" + moves +
                ", legalMoves=" + legalMoves +
                '}';
    }
}
