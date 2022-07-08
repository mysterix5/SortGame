package io.github.mysterix5.sortgame.spring;

import io.github.mysterix5.sortgame.game.GameProperties;
import io.github.mysterix5.sortgame.game.PlayingField;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/solver")
public class SolverController {
    private final SolverService solverService;

    @PostMapping
    public PlayingField createGame(@RequestBody GameCreationData gameProperties){
        System.out.println("CREATE GAME MAPPING");
        System.out.println(gameProperties);
        return solverService.createGame(gameProperties);
    }
}
