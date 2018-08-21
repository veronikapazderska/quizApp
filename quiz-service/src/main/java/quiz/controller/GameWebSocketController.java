package quiz.controller;

import api.game.GameRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import quiz.service.GameService;

@Controller
public class GameWebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(GameWebSocketController.class);

    @Autowired
    private GameService gameService;

    @MessageMapping("/gameRequest")
    public void gameRequestHandler(@Payload GameRequest gameRequest){
        gameService.handleGameRequest(gameRequest);
    }
}
