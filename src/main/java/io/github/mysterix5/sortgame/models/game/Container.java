package io.github.mysterix5.sortgame.models.game;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class Container implements Comparable<Container> {
    private List<Color> colorList = new ArrayList<>();
    private int height = 4;

    public Container(int height){
        this.height = height;
    }
    public Container(int height, List<Color> colors){
        this.height = height;
        colorList = colors;
    }

    public Container(Container container){
        this.height = container.getHeight();
        this.colorList.addAll(container.getColorList());
    }

    @Override
    public String toString() {
        return "Container: " + colorList;
    }

    public String toInitializationString() {
        return "List.of(" +
                colorList.stream()
                        .map(color -> "Color." + color)
                        .collect(Collectors.joining(", ")) +
                ")";
    }

    public Color getTop(){
        return !isEmpty() ? colorList.get(colorList.size()-1) : null;
    }

    public void addColor(Color color){
        if(isFull()) throw new RuntimeException("cant add another color to this bottle");
        colorList.add(color);
    }

    public int getActualSize() {
        return colorList.size();
    }
    public boolean isFull(){
        return colorList.size()>=height;
    }
    public boolean isEmpty(){
        return colorList.isEmpty();
    }

    public boolean isFinished(){
        if(!isFull()){
            return false;
        }
        Color color = colorList.get(0);
        for(var c: colorList){
            if(c!=color){
                return false;
            }
        }
        return true;
    }

    public Color pop() {
        return !isEmpty() ? colorList.remove(colorList.size()-1) : null;
    }

    @Override
    public int compareTo(Container o) {
        int thisl = colorList.size();
        int thatl = o.getColorList().size();
        if(thisl != thatl){
            return thisl-thatl;
        }
        for(int i = 0; i<thisl; i++){
            int thisc = colorList.get(i).ordinal();
            int thatc = o.getColorList().get(i).ordinal();
            if(thisc != thatc){
                return thisc-thatc;
            }
        }
        return 0;
    }
}
