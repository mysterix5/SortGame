package io.github.mysterix5.sortgame.models.security;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
public class SortGameUser {
    @Id
    private String id;
    private String username;
    private String password;
    private List<String> roles = new ArrayList<>();
}
