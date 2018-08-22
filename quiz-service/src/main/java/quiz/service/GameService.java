package quiz.service;

import api.game.Game;
import api.game.GameInvitationResponse;
import api.game.GameRequest;
import api.question.Question2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class GameService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private UserService userService;

    @Autowired QuizService quizService;

    public Map<String, List<Question2>> questionsForGame;


    public void handleGameRequest(GameRequest gameRequest) {
        logger.info("Sender is " + gameRequest.getSender() + " and receiver is " + gameRequest.getReceiver());
        messagingTemplate.convertAndSend("/topic/gameRequest/" + gameRequest.getReceiver(), gameRequest);
    }

    public void handleGameResponse(GameInvitationResponse gameInvitationResponse) {
        if(gameInvitationResponse.hasConfirmed){
           // quizService.generateQuestions();
            messagingTemplate.convertAndSend("/topic/gameStarts/" + gameInvitationResponse.getSender() + gameInvitationResponse.getReceiver());
        }
        else{
            messagingTemplate.convertAndSend("/topic/gameResponse/" + gameInvitationResponse.getSender(), gameInvitationResponse);
        }
    }


}
