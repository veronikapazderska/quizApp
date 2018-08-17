package quiz.controller;//import api.user.login.LogoutRequest;
        //import api.user.logout.LogoutRequest;
       // import api.user.register.RegisterRequest;
       // import communication.service.websocket.UserService;

        import api.user.ActiveUsersRequest;
        import api.user.User;
        import api.user.login.LoginRequest;
        import api.user.logout.LogoutRequest;
        import api.user.register.RegisterRequest;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.messaging.handler.annotation.MessageMapping;
        import org.springframework.messaging.handler.annotation.Payload;
        import org.springframework.messaging.simp.SimpMessageSendingOperations;
        import org.springframework.stereotype.Controller;
        import quiz.service.UserService;

/**
 * Created by iliyan.bachiyski on 26.4.2018 г..
 */

@Controller
public class UserWebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(UserWebSocketController.class);

    @Autowired
    private UserService userService;

  /*  @Autowired
    private UserService userService;

    @MessageMapping("/user/register")
    public void userRegisterHandler(@Payload RegisterRequest request) {
        this.userService.registerUser(request);
    }

    @MessageMapping("/user/logOut")
    public void userLogOutHandler(@Payload LogoutRequest request) {
        this.userService.logOut(request);
    }

    @MessageMapping("/user/logIn")
    public void userLoginHandler(@Payload LogoutRequest request) {
        this.userService.logIn(request);
    }

    @MessageMapping("/user/requestActiveUsers")
    public void requestActiveUsersHandler() {
        this.userService.publishActiveUsers();
    } */

    @MessageMapping("/loginRequest")
    public void userLoginHandler(@Payload LoginRequest loginRequest) {
        userService.checkLogin(loginRequest);
    }

    @MessageMapping("/registerRequest")
    public void userRegisterHandler(@Payload RegisterRequest registerRequest) {
        userService.registerUser(registerRequest);
    }

    @MessageMapping("/activeUsersRequest")
    public void activeUsersHandler(@Payload ActiveUsersRequest activeUsersRequest) {
        userService.publishActiveUsers();
    }

    @MessageMapping("/logoutRequest")
    public void logoutHandler(@Payload LogoutRequest logoutRequest) {
        userService.logOut(logoutRequest);
    }


}