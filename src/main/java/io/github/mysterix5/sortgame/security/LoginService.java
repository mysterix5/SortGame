package io.github.mysterix5.sortgame.security;

import io.github.mysterix5.sortgame.models.security.LoginResponse;
import io.github.mysterix5.sortgame.models.security.SortGameUser;
import io.github.mysterix5.sortgame.models.security.UserAuthenticationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public LoginResponse login(SortGameUser user, UserAuthenticationDTO loginData) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginData.getUsername(), loginData.getPassword()));
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles());
        claims.put("username", user.getUsername());
        return new LoginResponse(jwtService.createJwt(claims, user.getId()));
    }
}
