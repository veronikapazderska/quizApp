package quiz.service;

import api.user.User;
import api.user.login.LoginFailed;
import api.user.login.LoginRequest;
import api.user.login.LoginSuccessful;
import api.user.register.RegisterFailed;
import api.user.register.RegisterRequest;
import api.user.register.RegisterSuccessful;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    private List<User> registeredUsers = new ArrayList<>();

    @PostConstruct
    public void init() {
        this.registeredUsers.add(User.builder()
                .username("veronika").password("veronika")
                .firstName("Veronika")
                .lastName("Pazderska")
                .age(22)
                .points(150).build());
        this.registeredUsers.add(User.builder()
                .username("iliyan").password("iliyan")
                .firstName("Iliyan")
                .lastName("Bachiyski")
                .age(23)
                .points(100).build());
        this.registeredUsers.add(User.builder()
                .username("stanka").password("stanka")
                .firstName("Stanislava")
                .lastName("Zhelyazkova")
                .age(23)
                .points(120).build());
    }


    public void checkLogin(LoginRequest loginRequest) {

        if (checkForUsername(loginRequest.getUsername())) {
            User u = findUserByUsername(loginRequest.getUsername());
            if (u != null) {
                if (u.getPassword().equals(loginRequest.getPassword())) {
                    final LoginSuccessful loginSuccessful = LoginSuccessful.builder()
                            .firstName(u.getFirstName()).lastName(u.getLastName()).points(u.getPoints()).build();
                    messagingTemplate.convertAndSend("/topic/logSuccess/" + loginRequest.getUsername(), loginSuccessful);
                } else {
                    final LoginFailed loginFailed = LoginFailed.builder().message("Invalid Credentials!").build();
                    messagingTemplate.convertAndSend("topic/logFailed/" + loginRequest.getUsername(), loginFailed);
                }
            }
        }
    }


    public void registerUser(RegisterRequest registerRequest) {
        if (checkForUsername(registerRequest.getUsername())) {
            final RegisterFailed registerFailed = RegisterFailed.builder().message("User with such username already exists").build();
            messagingTemplate.convertAndSend("topic/regFailed/" + registerRequest.getUsername(), registerFailed);
            return;
        }
        if (registerRequest.getUsername() == null || registerRequest.getUsername().isEmpty() || registerRequest.getUsername().length() < 4) {
            final RegisterFailed registerFailed = RegisterFailed.builder().message("Invalid username").build();
            messagingTemplate.convertAndSend("topic/regFailed/" + registerRequest.getUsername(), registerFailed);
            return;
        }
        if (registerRequest.getPassword() == null || registerRequest.getPassword().isEmpty() || registerRequest.getPassword().length() < 8) {
            final RegisterFailed registerFailed = RegisterFailed.builder().message("Invalid password").build();
            messagingTemplate.convertAndSend("topic/regFailed/" + registerRequest.getUsername(), registerFailed);
            return;
        }
        if (registerRequest.getAge() < 14) {
            final RegisterFailed registerFailed = RegisterFailed.builder().message("User not old enough to join the game").build();
            messagingTemplate.convertAndSend("topic/regFailed/" + registerRequest.getUsername(), registerFailed);
            return;
        }
        final RegisterSuccessful registerSuccessful = RegisterSuccessful.builder().username(registerRequest.getUsername())
                .password(registerRequest.getPassword()).firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName()).age(registerRequest.getAge())
                .points(0).build();
        messagingTemplate.convertAndSend("topic/regSuccess/" + registerRequest.getUsername(), registerSuccessful);
    }

    private boolean checkForUsername(String username) {
        for (User u : registeredUsers) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private User findUserByUsername(String username) {
        for (User u : registeredUsers) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }


}
