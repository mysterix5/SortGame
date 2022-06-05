package io.github.mysterix5.watercolor;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WaterColorBottle {
    private List<WaterColorEnum> colorList = new ArrayList<>();
    public static final int size = 4;

    @Override
    public String toString() {
        return "WaterColorBottle{" +
                "colorList=" + colorList +
                '}';
    }

    public void addColor(WaterColorEnum color){
        if(isFull()) throw new RuntimeException("cant add another color to this bottle");
        colorList.add(color);
    }

    public boolean isFull(){
        return colorList.size()>=4;
    }
    public boolean isEmpty(){
        return colorList.isEmpty();
    }

    public boolean isFinished(){
        if(!isFull()){
            return false;
        }
        WaterColorEnum color = colorList.get(0);
        for(var c: colorList){
            if(c!=color){
                return false;
            }
        }
        return true;
    }
}
