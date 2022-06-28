package io.github.mysterix5.sortgame.spring;

import io.github.mysterix5.sortgame.game.PlayingField;
import io.github.mysterix5.sortgame.game.Color;
import io.github.mysterix5.sortgame.game.Container;
import io.github.mysterix5.sortgame.game.Game;
import io.github.mysterix5.sortgame.game.GameProperties;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class SortGameRepository {
    private final Map<String, Game> savedGames = new HashMap<>();

    public SortGameRepository(){
        fillWithPreparedGame1();
        fillWithPreparedGame2();
    }

    public Optional<Game> findById(String id) {
        System.out.println(savedGames);
        return Optional.ofNullable(savedGames.get(id));
    }
    public List<Game> findAll(){
        return savedGames.values().stream().toList();
    }

    public void fillWithPreparedGame1(){
        GameProperties gameProperties = new GameProperties(4, 8, 2);
        int height = gameProperties.getContainerHeight();

        PlayingField playingField = new PlayingField(height);
        List<Container> containers = new ArrayList<>();
        containers.add(new Container(height, new ArrayList<>(List.of(Color.GREEN, Color.ORANGE, Color.PURPLE, Color.ORANGE))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.GREY, Color.BROWN, Color.GREEN, Color.GREEN))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.GREEN, Color.RED, Color.BLUE, Color.PURPLE))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.PURPLE, Color.RED, Color.YELLOW, Color.GREY))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.GREY, Color.BLUE, Color.RED, Color.RED))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.PURPLE, Color.ORANGE, Color.YELLOW, Color.YELLOW))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.GREY, Color.BROWN, Color.ORANGE, Color.BLUE))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.BROWN, Color.BROWN, Color.YELLOW, Color.BLUE))));
        containers.add(new Container(height, new ArrayList<>(List.of())));
        containers.add(new Container(height, new ArrayList<>(List.of())));
        playingField.setContainers(containers);

        Game game = new Game(gameProperties);
        game.setPosition(playingField);

        savedGames.put(game.getId(), game);
    }
    public void fillWithPreparedGame2(){
        GameProperties gameProperties = new GameProperties(4, 8, 2);
        int height = gameProperties.getContainerHeight();

        PlayingField playingField = new PlayingField(height);
        List<Container> containers = new ArrayList<>();
        containers.add(new Container(height, new ArrayList<>(List.of(Color.PURPLE, Color.PURPLE, Color.BROWN, Color.PURPLE))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.ORANGE, Color.GREEN, Color.YELLOW, Color.BLUE))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.BROWN, Color.GREY, Color.RED, Color.GREY))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.RED, Color.BROWN, Color.GREEN, Color.GREY))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.BLUE, Color.ORANGE, Color.RED, Color.ORANGE))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.GREY, Color.GREEN, Color.YELLOW, Color.BLUE))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.BROWN, Color.YELLOW, Color.BLUE, Color.GREEN))));
        containers.add(new Container(height, new ArrayList<>(List.of(Color.RED, Color.ORANGE, Color.PURPLE, Color.YELLOW))));
        containers.add(new Container(height, new ArrayList<>(List.of())));
        containers.add(new Container(height, new ArrayList<>(List.of())));
        playingField.setContainers(containers);

        Game game = new Game(gameProperties);
        game.setPosition(playingField);

        savedGames.put(game.getId(), game);
    }

    public void save(Game game) {
        savedGames.put(game.getId(), game);
    }
}
