package io.github.mysterix5.watercolor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/game")
public class WaterColorController {
    @GetMapping
    public List<WaterColorBottle> getPlayingField(){
        var game = new Game();
        return game.createDummyGame();
    }
}
