package quiz.service;

import api.question.Question2;
import api.user.ActiveUser;
import api.user.ActiveUsers;
import api.user.User;
import api.user.login.LoginFailed;
import api.user.login.LoginRequest;
import api.user.login.LoginSuccessful;
import api.user.logout.LogoutRequest;
import api.user.logout.LogoutResponse;
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

    //private List<Question2> questions = new ArrayList<>();

    private ActiveUsers activeUsers = ActiveUsers.builder().activeUsers(new ArrayList<>()).build();

    @PostConstruct
    public void init() {
        this.registeredUsers.add(User.builder()
                .username("veronika")
                .password("veronika")
                .firstName("Veronika")
                .lastName("Pazderska")
                .age(22)
                .isActive(false)
                .points(150).build());
        this.registeredUsers.add(User.builder()
                .username("iliyan")
                .password("iliyan")
                .firstName("Iliyan")
                .lastName("Bachiyski")
                .age(23)
                .isActive(false)
                .points(100).build());
        this.registeredUsers.add(User.builder()
                .username("stanka")
                .password("stanka")
                .firstName("Stanislava")
                .lastName("Zhelyazkova")
                .age(23)
                .points(120)
                .isActive(false)
                .build());

    }


    public void checkLogin(LoginRequest loginRequest) {

        if (checkForUsername(loginRequest.getUsername())) {
            User u = findUserByUsername(loginRequest.getUsername());

            if (u.getPassword().equals(loginRequest.getPassword()) && !this.checkForActiveUser(loginRequest.getUsername())) {
                final LoginSuccessful loginSuccessful = LoginSuccessful.builder().username(u.getUsername())
                        .firstName(u.getFirstName()).lastName(u.getLastName()).points(u.getPoints())
                        .isActive(true).build();
                this.activeUsers.activeUsers.add(ActiveUser.builder().username(u.getUsername())
                        .firstName(u.getFirstName()).lastName(u.getLastName()).age(u.getAge()).points(u.getPoints()).build());
                messagingTemplate.convertAndSend("/topic/logSuccess/" + loginRequest.getUsername(), loginSuccessful);
                publishActiveUsers();
                return;
            }
        }

        final LoginFailed loginFailed = LoginFailed.builder().message("Invalid Login!").build();
        this.logger.info(loginFailed.toString());
        messagingTemplate.convertAndSend("/topic/logFailed/" + loginRequest.getUsername(), loginFailed);

    }

    public void registerUser(RegisterRequest registerRequest) {
        if (checkUserRegistration(registerRequest)) {
            final User u = User.builder().username(registerRequest.getUsername()).password(registerRequest.getPassword())
                    .firstName(registerRequest.getFirstName()).lastName(registerRequest.getLastName()).age(registerRequest.getAge())
                    .points(0).isActive(true).build();
            registeredUsers.add(u);
            final RegisterSuccessful registerSuccessful = RegisterSuccessful.builder().username(registerRequest.getUsername())
                    .password(registerRequest.getPassword()).firstName(registerRequest.getFirstName())
                    .lastName(registerRequest.getLastName()).age(registerRequest.getAge())
                    .points(0).isActive(true).build();
            this.activeUsers.activeUsers.add(ActiveUser.builder().username(u.getUsername())
                    .firstName(u.getFirstName()).lastName(u.getLastName()).points(u.getPoints()).age(u.getAge()).build());
            final String topic = "/topic/regSuccess/" + registerRequest.getUsername();
            this.logger.info("Topic: {}", topic);
            messagingTemplate.convertAndSend(topic, registerSuccessful);
            this.logger.info("User successfully registered!");

        }
    }

    public void publishActiveUsers() {
        messagingTemplate.convertAndSend("/topic/activeUsers", activeUsers);
    }

    public void logOut(LogoutRequest logoutRequest) {
        ActiveUser activeUser = findActiveUserByUsername(logoutRequest.getUsername());
        this.activeUsers.activeUsers.remove(activeUser);
        publishActiveUsers();
        final LogoutResponse logoutResponse = LogoutResponse.builder().message("User has logged out").build();
        messagingTemplate.convertAndSend("/topic/logOut/" + logoutRequest.getUsername(), logoutResponse);
        this.logger.info(logoutResponse.message);

    }

    private boolean checkUserRegistration(RegisterRequest registerRequest) {
        if (checkForUsername(registerRequest.getUsername())) {
            final RegisterFailed registerFailed = RegisterFailed.builder().message("User with such username already exists").build();
            messagingTemplate.convertAndSend("/topic/regFailed/" + registerRequest.getUsername(), registerFailed);
            this.logger.info("User not successfully registered! - user with such username already exists");
            this.logger.info(registeredUsers.toString());
            return false;
        }
        if (registerRequest.getUsername() == null || registerRequest.getUsername().isEmpty() || registerRequest.getUsername().length() < 4) {
            final RegisterFailed registerFailed = RegisterFailed.builder().message("Invalid username").build();
            messagingTemplate.convertAndSend("/topic/regFailed/" + registerRequest.getUsername(), registerFailed);
            this.logger.info("User not successfully registered! - invalid username");
            this.logger.info(registeredUsers.toString());
            return false;
        }
        if (registerRequest.getPassword() == null || registerRequest.getPassword().isEmpty() || registerRequest.getPassword().length() < 8) {
            final RegisterFailed registerFailed = RegisterFailed.builder().message("Invalid password").build();
            messagingTemplate.convertAndSend("/topic/regFailed/" + registerRequest.getUsername(), registerFailed);
            this.logger.info("User not successfully registered! - invalid password");
            this.logger.info(registeredUsers.toString());
            return false;
        }
        if (registerRequest.getAge() < 14) {
            final RegisterFailed registerFailed = RegisterFailed.builder().message("User not old enough to join the game").build();
            messagingTemplate.convertAndSend("/topic/regFailed/" + registerRequest.getUsername(), registerFailed);
            this.logger.info("User not successfully registered! - too young");
            this.logger.info(registeredUsers.toString());
            return false;
        }
        return true;
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

    private ActiveUser findActiveUserByUsername(String username) {
        for (ActiveUser u : activeUsers.activeUsers) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    private boolean checkForActiveUser(String username) {
        for (ActiveUser u : activeUsers.activeUsers) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

}
