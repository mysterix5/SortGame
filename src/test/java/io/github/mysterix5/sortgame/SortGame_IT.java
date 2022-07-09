package io.github.mysterix5.sortgame;

import io.github.mysterix5.sortgame.models.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SortGame_IT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void integrationTest(){
        ResponseEntity<Game> response;

        // initial get todos via api should be empty
        response = restTemplate.getForEntity("/api/game", Game.class);
        System.out.println(response.getBody());
    }
}
