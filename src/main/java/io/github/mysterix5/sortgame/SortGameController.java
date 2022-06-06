package io.github.mysterix5.sortgame;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/game")
public class SortGameController {
    @GetMapping
    public List<Container> getPlayingField(){
        var game = new Game();

        List<Container> dummyGameWon = new ArrayList<>();
        var container = new Container();
        container.addColor(Color.BLUE);
        container.addColor(Color.BLUE);
        container.addColor(Color.BLUE);
        container.addColor(Color.BLUE);
        dummyGameWon.add(container);
        dummyGameWon.add(new Container());
        game.getPlayingField().setContainers(dummyGameWon);
        System.out.println("won? " + game.isWon());
        System.out.println("solvable? " + game.isSolvable());
        System.out.println(game);


        game.createDummyGame();
        System.out.println("won? " + game.isWon());
        System.out.println("solvable? " + game.isSolvable());
        return game.getPlayingField().getContainers();
    }
}
