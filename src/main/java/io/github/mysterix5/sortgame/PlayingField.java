package io.github.mysterix5.sortgame;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
public class PlayingField {
    private List<Container> containers = new ArrayList<>();

    public PlayingField(PlayingField playingField){
        this.containers = new ArrayList<>(playingField.containers);
    }
    public List<Move> getLegalMoves(){
        List<Move> moves = new ArrayList<>();
        for(int i = 0; i< containers.size(); i++){
            for(int j = 0; j< containers.size(); j++){
                Move move = new Move(i,j);
                if(i!=j && isLegit(move)){
                    moves.add(move);
                }
            }
        }
        return moves;
    }


    /** moves as much top color as possible from one container to another
     * checks: FROM not empty, TO not full, TO empty or colors fit
     */
    public boolean move(Move move){
        if(!isLegit(move)){ return false; }
        containers.get(move.getTo()).addColor(containers.get(move.getFrom()).pop());
        while(containers.get(move.getFrom()).getTop()==containers.get(move.getTo()).getTop()
                && isLegit(move)){
            containers.get(move.getTo()).addColor(containers.get(move.getFrom()).pop());
        }
        return false;
    }

    public boolean isWon(){
        for(Container container: containers){
            if(!(container.isEmpty() || container.isFinished())){
                return false;
            }
        }
        return true;
    }

    private boolean isLegit(int index){
        return index>=0 && index< containers.size();
    }
    /**
     * checks for: indizes ok, FROM not empty, TO not full, colors fit
     */
    private boolean isLegit(Move move){
        if(!isLegit(move.getFrom()) || !isLegit(move.getTo()) || containers.get(move.getFrom()).isEmpty() || containers.get(move.getTo()).isFull()){
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayingField that = (PlayingField) o;
        return Objects.equals(containers, that.containers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(containers);
    }

}
