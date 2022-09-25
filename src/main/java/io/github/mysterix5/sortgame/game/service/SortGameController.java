package io.github.mysterix5.sortgame.game.service;

import io.github.mysterix5.sortgame.models.game.GameCreationData;
import io.github.mysterix5.sortgame.models.game.GameInfo;
import io.github.mysterix5.sortgame.models.game.GamesList;
import io.github.mysterix5.sortgame.models.game.Move;
import io.github.mysterix5.sortgame.security.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/game")
@Slf4j
public class SortGameController {
    private final SortGameService gameService;
    private final JwtService jwtService;

    @GetMapping
    public List<GameInfo> getSavedGamesOverview(){
        return gameService.getAllSavedGames();
    }

    @GetMapping("/getall")
    public GamesList getGamesOverview(Principal principal) {
        return gameService.getAllGames(principal.getName());
    }

    @GetMapping("/{levelId}")
    public GameInfo getGameById(@PathVariable String levelId, Principal principal, @RequestHeader (name="Authorization") String token) {
        log.info("token: {}", token.split(" ")[1]);
        Claims claims = jwtService.extractAllClaims(token.split(" ")[1]);
        log.info("claims: {}", claims);
        String userId = (String) claims.get("userid");
        log.info("id: {}", userId);
        return gameService.getGameById(principal.getName(), levelId);
    }

    @PutMapping("/{lvlId}/move")
    public void move(@RequestBody Move move, @PathVariable String lvlId, Principal principal){
        gameService.move(lvlId, principal.getName(), move);
    }
    @PutMapping("/{levelId}/reset")
    public void resetGame(@PathVariable String levelId, Principal principal){
        gameService.resetGame(principal.getName(), levelId);
    }

    @GetMapping("/{levelId}/hint")
    public ResponseEntity<Move> getHint(@PathVariable String levelId, Principal principal){
        try {
            Move hint = gameService.getHint(principal.getName(), levelId);
            log.info("hint: {} -> {}", hint.getFrom(), hint.getTo());
            return ResponseEntity.ok().body(hint);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public GameInfo createGame(@RequestBody GameCreationData gameProperties){
        // TODO add check if user finished all levels
        System.out.println("CREATE GAME MAPPING");
        System.out.println(gameProperties);
        return gameService.createGame(gameProperties);
    }

    @DeleteMapping("/deleteall")
    public void deleteAllLevels() {
        gameService.deleteAllLevels();
    }

    @GetMapping("/test")
    public void test(Principal principal) {
        log.info("principal: {}", principal.toString());
    }
}
