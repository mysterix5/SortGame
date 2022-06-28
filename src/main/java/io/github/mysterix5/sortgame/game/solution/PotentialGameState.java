package io.github.mysterix5.sortgame.game.solution;

import io.github.mysterix5.sortgame.game.Move;
import io.github.mysterix5.sortgame.game.PlayingField;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class PotentialGameState {
    private PlayingField playingField;
    private List<Move> moves;

    public PotentialGameState getNextState(Move move){
        PlayingField playingFieldCopy = new PlayingField(playingField);
        playingFieldCopy.move(move);
        List<Move> movesCopy = new ArrayList<>(moves);
        movesCopy.add(move);
        return new PotentialGameState(playingFieldCopy, movesCopy);
    }
}
