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

import java.util.*;

@Service
public class GameService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private QuizService quizService;

    public Map<String, Queue<Question2>> questionsForGame = new HashMap<>();


    public void handleGameRequest(GameRequest gameRequest) {
        logger.info("Sender is " + gameRequest.getSender() + " and receiver is " + gameRequest.getReceiver());
        messagingTemplate.convertAndSend("/topic/gameRequest/" + gameRequest.getReceiver(), gameRequest);
    }

    public void handleGameResponse(GameInvitationResponse gameInvitationResponse) {
        if(gameInvitationResponse.hasConfirmed){
            final String gameStartsTopic = gameInvitationResponse.getSender() + "-" + gameInvitationResponse.getReceiver();
            questionsForGame.put(gameStartsTopic, new LinkedList<>(quizService.generateQuestions()));
            //TODO: fill the array list with questions

            messagingTemplate.convertAndSend("/topic/gameStarts/" + gameStartsTopic, gameInvitationResponse);
        }
        else{
            messagingTemplate.convertAndSend("/topic/gameResponse/" + gameInvitationResponse.getSender(), gameInvitationResponse);
        }
    }

    public Question2 getQuestionByTopic(String topic) {
        return questionsForGame.get(topic).poll();
    }

}
