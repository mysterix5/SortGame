package io.github.mysterix5.sortgame.spring;

import io.github.mysterix5.sortgame.game.PlayingField;
import io.github.mysterix5.sortgame.game.solution.StaticMethods;
import io.github.mysterix5.sortgame.game.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/game")
public class SortGameController {
    private final SortGameService gameService;

    @GetMapping
    public List<String> getSavedGamesOverview(){
        return gameService.getAllSavedGamesIds();
    }

    @GetMapping("/{id}")
    public Optional<GameInfo> getGameById(@PathVariable String id) {
        return gameService.getGameById( id);
    }

    @PutMapping("/move")
    public void move(@RequestBody GamePutter gamePutter){
        System.out.println(gamePutter);
        gameService.move(gamePutter);
    }
    @PutMapping("/reset")
    public void resetGame(@RequestBody GamePutter gamePutter){
        System.out.println(gamePutter);
        gameService.resetGame(gamePutter.getId());
    }

    @GetMapping("/dummytestshit")
    public Game getPlayingField(){
        GameProperties gameProperties = new GameProperties(4, 6, 2);
        Game game = new Game(gameProperties);

        List<Container> dummyGameWon = new ArrayList<>();
        Container container = new Container(gameProperties.getContainerHeight());
        container.addColor(Color.BLUE);
        container.addColor(Color.BLUE);
        container.addColor(Color.BLUE);
        container.addColor(Color.BLUE);
        dummyGameWon.add(container);
        dummyGameWon.add(new Container(gameProperties.getContainerHeight()));
        PlayingField playingField = new PlayingField(gameProperties.getContainerHeight());
        playingField.setContainers(dummyGameWon);
        game.setPosition(playingField);
        System.out.println("won? " + game.isWon());
//        System.out.println("solvable? " + game.isSolvable());
        System.out.println(game);


        game.setPosition(StaticMethods.createDummyGame(gameProperties));
        System.out.println("won? " + game.isWon());
//        System.out.println("solvable? " + game.isSolvable());
        return game;
    }
}
