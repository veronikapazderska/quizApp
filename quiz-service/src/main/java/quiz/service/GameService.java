package quiz.service;

import api.game.GameRequest;
import api.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private UserService userService;

    public void handleGameRequest(GameRequest gameRequest){
        messagingTemplate.convertAndSend("/topic/gameRequest/" + gameRequest.getReceiver(), gameRequest);
    }

}
