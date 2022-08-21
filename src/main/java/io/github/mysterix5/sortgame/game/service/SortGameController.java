package io.github.mysterix5.sortgame.game.service;

import io.github.mysterix5.sortgame.models.game.GameCreationData;
import io.github.mysterix5.sortgame.models.game.GameInfo;
import io.github.mysterix5.sortgame.models.game.Move;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/game")
@Slf4j
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

    @PutMapping("/{id}/move")
    public void move(@RequestBody Move move, @PathVariable String id){
        gameService.move(id, move);
    }
    @PutMapping("/{id}/reset")
    public void resetGame(@PathVariable String id){
        gameService.resetGame(id);
    }

    @GetMapping("/{id}/hint")
    public ResponseEntity<Move> getHint(@PathVariable String id){
        try {
            Move hint = gameService.getHint(id);
            log.info("hint: {} -> {}", hint.getFrom(), hint.getTo());
            return ResponseEntity.ok().body(hint);
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

    @GetMapping("/test")
    public void test(Principal principal) {
        log.info("principal: {}", principal.toString());
    }
}
