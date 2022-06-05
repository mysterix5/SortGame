package io.github.mysterix5.watercolor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/game")
public class WaterColorController {
    @GetMapping
    public List<Bottle> getPlayingField(){
        var game = new Game();

        List<Bottle> dummyGameWon = new ArrayList<>();
        var bottle1 = new Bottle();
        bottle1.addColor(Color.BLUE);
        bottle1.addColor(Color.BLUE);
        bottle1.addColor(Color.BLUE);
        bottle1.addColor(Color.BLUE);
        dummyGameWon.add(bottle1);
        dummyGameWon.add(new Bottle());
        game.getPlayingField().setBottles(dummyGameWon);
        System.out.println("won? " + game.isWon());
        System.out.println("solvable? " + game.isSolvable());
        System.out.println(game);


        game.createDummyGame();
        System.out.println("won? " + game.isWon());
        System.out.println("solvable? " + game.isSolvable());
        return game.getPlayingField().getBottles();
    }
}
