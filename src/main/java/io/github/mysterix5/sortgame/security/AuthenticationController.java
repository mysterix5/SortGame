package io.github.mysterix5.sortgame.security;

import io.github.mysterix5.sortgame.models.other.MultipleSubErrorException;
import io.github.mysterix5.sortgame.models.other.SortGameErrorDTO;
import io.github.mysterix5.sortgame.models.security.LoginResponse;
import io.github.mysterix5.sortgame.models.security.SortGameUser;
import io.github.mysterix5.sortgame.models.security.UserAuthenticationDTO;
import io.github.mysterix5.sortgame.models.security.UserRegisterDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final UserService userService;
    private final LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserRegisterDTO registerData){
        try {
            log.info("REGISTER: user '{}'", registerData.getUsername());
            userService.createUser(registerData);
            log.info("REGISTERING user '{}' was successfully", registerData.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (MultipleSubErrorException e){
            log.warn("registering user {} failed. {}", registerData.getUsername(), e.toInlineMessage());
            return ResponseEntity.badRequest().body(new SortGameErrorDTO(e));
        } catch (DuplicateKeyException e) {
            log.warn("registering user {} failed, because a user of this name existed. ", registerData.getUsername());
            return ResponseEntity.badRequest().body(new SortGameErrorDTO("registering user " + registerData.getUsername() + " failed", "database rejected because this user already exists"));
        }catch (Exception e) {
            log.warn("registering user {} failed, because of an internal error", registerData.getUsername(), e);
            return ResponseEntity.internalServerError().body(new SortGameErrorDTO("registering user " + registerData.getUsername() + " failed due to some server problem"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserAuthenticationDTO loginData) {
        try{
            log.info("LOGIN: user '{}'", loginData.getUsername());
            SortGameUser user = userService.findByUsername(loginData.getUsername()).orElseThrow();
            LoginResponse token = loginService.login(user, loginData);
            log.info("LOGIN of user '{}' successfully", loginData.getUsername());
            return ResponseEntity.ok(token);
        }catch(NoSuchElementException e){
            log.warn("login user {} failed, user not found", loginData.getUsername());
            return ResponseEntity.badRequest().body(new SortGameErrorDTO("Login failed", "This user does not exist"));
        }catch(BadCredentialsException e){
            log.warn("login user {} failed, authentication failed", loginData.getUsername());
            return ResponseEntity.badRequest().body(new SortGameErrorDTO("Login failed", "It was not possible to authenticate this 'user', 'password' combination", "Are you sure your credentials are correct?"));
        }catch (Exception e) {
            log.warn("login user {} failed, because of an internal error", loginData.getUsername(), e);
            return ResponseEntity.internalServerError().body(new SortGameErrorDTO("registering user " + loginData.getUsername() + " failed due to some server problem"));
        }
    }

}
