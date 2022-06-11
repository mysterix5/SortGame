package io.github.mysterix5.sortgame;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
public class PlayingField {
    int containerHeight;
    private List<Container> containers = new ArrayList<>();

    public PlayingField(int containerHeight){
        this.containerHeight = containerHeight;
    }

    public PlayingField(PlayingField playingField){
        for(Container c: playingField.getContainers()){
            this.containers.add(new Container(c));
        }
        this.containerHeight = playingField.containerHeight;
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
        if(!isLegit(move)){
            return false;
        }
        Container from = containers.get(move.getFrom());
        Container to = containers.get(move.getTo());
        to.addColor(from.pop());
        while(isLegit(move) && from.getTop().equals(to.getTop())){
            to.addColor(from.pop());
        }
        return true;
    }

    public boolean move(List<Move> moves){
        boolean anyMoveFailed = true;
        for(Move m: moves){
            anyMoveFailed = anyMoveFailed && move(m);
        }
        return anyMoveFailed;
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
            if(!containers.get(move.getFrom()).getTop().equals(containers.get(move.getTo()).getTop())){
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
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = containerHeight-1; i>=-1; i--){
            for(Container c: containers){
                if(i==-1){
                    stringBuilder.append("---");
                    stringBuilder.append(" \t");
                    continue;
                }
                if(c.getActualSize()>i){
                    stringBuilder.append(c.getColorList().get(i));
                }else{
                    stringBuilder.append("   ");
                }
                stringBuilder.append(" \t");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
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
