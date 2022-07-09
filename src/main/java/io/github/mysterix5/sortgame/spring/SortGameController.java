package io.github.mysterix5.sortgame.spring;

import io.github.mysterix5.sortgame.models.GameCreationData;
import io.github.mysterix5.sortgame.models.GameInfo;
import io.github.mysterix5.sortgame.models.GamePutter;
import io.github.mysterix5.sortgame.models.Move;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/game")
public class SortGameController {
    private final SortGameService gameService;

    @GetMapping
    public List<GameInfo> getSavedGamesOverview(){
        return gameService.getAllSavedGames();
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

    @GetMapping("/{id}/hint")
    public ResponseEntity<Move> getHint(@PathVariable String id){
        try {
            return ResponseEntity.ok().body(gameService.getHint(id));
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public GameInfo createGame(@RequestBody GameCreationData gameProperties){
        System.out.println("CREATE GAME MAPPING");
        System.out.println(gameProperties);
        return gameService.createGame(gameProperties);
    }
}
