package io.github.mysterix5.sortgame.security;

import io.github.mysterix5.sortgame.models.other.MultipleSubErrorException;
import io.github.mysterix5.sortgame.models.security.SortGameUser;
import io.github.mysterix5.sortgame.models.security.UserRegisterDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final SortGameUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidator passwordValidator;

    public void createUser(UserRegisterDTO userCreationDTO) {
        if (userCreationDTO.getUsername() == null || userCreationDTO.getUsername().isBlank()) {
            throw new MultipleSubErrorException("username is blank");
        }
        if(!isUsername(userCreationDTO.getUsername())){
            throw new MultipleSubErrorException("Your username is not valid");
        }
        if (userRepository.existsByUsernameIgnoreCase(userCreationDTO.getUsername())) {
            throw new MultipleSubErrorException("a user with this name already exists");
        }
        var tmp = new PasswordData(userCreationDTO.getUsername(), userCreationDTO.getPassword());
        RuleResult passwordValidationResult = passwordValidator.validate(tmp);
        if (!passwordValidationResult.isValid()) {
            throw new MultipleSubErrorException("Your password is not secure enough", passwordValidator.getMessages(passwordValidationResult));
        }
        if (!userCreationDTO.getPassword().equals(userCreationDTO.getPasswordRepeat())){
            throw new MultipleSubErrorException("Passwords have to match");
        }

        SortGameUser user = new SortGameUser();
        user.setUsername(userCreationDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userCreationDTO.getPassword()));
        user.setRoles(List.of("user"));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username)
                .map(user -> new User(user.getUsername(), user.getPassword(), List.of()))
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }

    public Optional<SortGameUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private boolean isUsername(String username) {
        String usernameRegex = "^(?=.{3,20}$)(?![_-])(?!.*[_-]{2})[a-zA-Z0-9-_]+(?<![_-])$";
        return username.matches(usernameRegex);
    }
}
