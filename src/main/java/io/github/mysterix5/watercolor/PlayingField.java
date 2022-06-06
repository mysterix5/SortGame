package io.github.mysterix5.watercolor;

import java.util.ArrayList;
import java.util.List;

public class PlayingField {
    private List<Bottle> bottles = new ArrayList<>();

    public List<Move> getLegalMoves(){
        List<Move> moves = new ArrayList<>();
        for(int i = 0; i< bottles.size(); i++){
            for(int j = 0; j< bottles.size(); j++){
                Move move = new Move(i,j);
                if(i!=j && isOk(move)){
                    moves.add(move);
                }
            }
        }
        return moves;
    }

    public boolean move(Move move){
        if(!isOk(move)){ return false; }
        bottles.get(move.getTo()).addColor(bottles.get(move.getFrom()).pop());

        return false;
    }

    public boolean isWon(){
        for(var b: bottles){
            if(!(b.isEmpty() || b.isFinished())){
                return false;
            }
        }
        return true;
    }

    private boolean isOk(int index){
        return index>=0 && index<bottles.size();
    }
    /**
     * checks for: indizes ok, FROM not empty, TO not full, colors fit
     */
    private boolean isOk(Move move){
        if(!isOk(move.getFrom()) || !isOk(move.getTo()) || bottles.get(move.getFrom()).isEmpty() || bottles.get(move.getTo()).isFull()){
            return false;
        }
        if(!bottles.get(move.getTo()).isEmpty()){
            if(bottles.get(move.getFrom()).getTop().equals(bottles.get(move.getFrom()).getTop())){
                return false;
            }
        }
        return true;
    }

    public void setBottles(List<Bottle> bottles) {
        this.bottles = bottles;
    }

    public List<Bottle> getBottles() {
        return bottles;
    }

    @Override
    public String toString() {
        return "PlayingField{" +
                "bottles=" + bottles +
                '}';
    }
}