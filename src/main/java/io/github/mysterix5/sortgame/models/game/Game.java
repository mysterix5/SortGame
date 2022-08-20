package io.github.mysterix5.sortgame.models.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Game {
    @Id
    private String id;
    private GameProperties gameProperties;
    private PlayingField initialPosition;
    private PlayingField actualPosition;

    public Game(GameProperties gameProperties){
        this.gameProperties = gameProperties;
    }

    public GameProperties getGameProperties() {
        return gameProperties;
    }

    public PlayingField getInitialPosition() {
        return initialPosition;
    }

    public PlayingField getActualPosition() {
        return actualPosition;
    }

    public void setPosition(PlayingField playingField){
        this.initialPosition = playingField;
        this.actualPosition = new PlayingField(playingField);
    }

    public boolean isWon() {
        return actualPosition.isWon();
    }


    public String getId() {
        return id;
    }

    public void reset() {
        setPosition(this.getInitialPosition());
    }


}
