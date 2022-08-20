package io.github.mysterix5.sortgame.models.other;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class SortGameErrorDTO {
    private String message;
    private List<String> subMessages = new ArrayList<>();

    public SortGameErrorDTO(MultipleSubErrorException e){
        message = e.getMessage();
        subMessages = e.getSubMessages();
    }

    public SortGameErrorDTO(Exception e){
        message = e.getMessage();
    }

    public SortGameErrorDTO(String message, String ...subMessages){
        this.message = message;
        Collections.addAll(this.subMessages, subMessages);
    }
}
