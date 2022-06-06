package io.github.mysterix5.sortgame;

import java.util.ArrayList;
import java.util.List;

public class PlayingField {
    private List<Container> containers = new ArrayList<>();

    public List<Move> getLegalMoves(){
        List<Move> moves = new ArrayList<>();
        for(int i = 0; i< containers.size(); i++){
            for(int j = 0; j< containers.size(); j++){
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
        containers.get(move.getTo()).addColor(containers.get(move.getFrom()).pop());

        return false;
    }

    public boolean isWon(){
        for(var b: containers){
            if(!(b.isEmpty() || b.isFinished())){
                return false;
            }
        }
        return true;
    }

    private boolean isOk(int index){
        return index>=0 && index< containers.size();
    }
    /**
     * checks for: indizes ok, FROM not empty, TO not full, colors fit
     */
    private boolean isOk(Move move){
        if(!isOk(move.getFrom()) || !isOk(move.getTo()) || containers.get(move.getFrom()).isEmpty() || containers.get(move.getTo()).isFull()){
            return false;
        }
        if(!containers.get(move.getTo()).isEmpty()){
            if(containers.get(move.getFrom()).getTop().equals(containers.get(move.getFrom()).getTop())){
                return false;
            }
        }
        return true;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    public List<Container> getContainers() {
        return containers;
    }

    @Override
    public String toString() {
        return "PlayingField{" +
                "containers=" + containers +
                '}';
    }
}
