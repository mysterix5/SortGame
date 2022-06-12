package io.github.mysterix5.sortgame;

import java.util.UUID;

public class Game {

    private final String id = UUID.randomUUID().toString();
    private GameProperties gameProperties;
    private PlayingField initialPosition;
    private PlayingField actualPosition;

    public Game(){}
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
}
