package io.github.mysterix5.watercolor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WaterColorGame_IT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void integrationTest(){
        ResponseEntity<WaterColorBottle[]> response;

        // initial get todos via api should be empty
        response = restTemplate.getForEntity("/api/game", WaterColorBottle[].class);
        System.out.println(Arrays.toString(response.getBody()));
    }
}
