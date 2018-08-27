package quiz.service;

import api.game.GameInvitationResponse;
import api.game.GameRequest;
import api.question.Question;
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

    public Map<String, Queue<Question>> questionsForGame = new HashMap<>();


    public void handleGameRequest(GameRequest gameRequest) {
        logger.info("Sender is " + gameRequest.getSender() + " and receiver is " + gameRequest.getReceiver());
        messagingTemplate.convertAndSend("/topic/gameRequest/" + gameRequest.getReceiver(), gameRequest);
    }

    public void handleGameResponse(GameInvitationResponse gameInvitationResponse) {
        if(gameInvitationResponse.hasConfirmed){
            final String gameStartsTopic = gameInvitationResponse.getSender() + "-" + gameInvitationResponse.getReceiver();
            questionsForGame.put(gameStartsTopic, new LinkedList<>(quizService.generateQuestions()));
            messagingTemplate.convertAndSend("/topic/gameStarts/" + gameStartsTopic, gameInvitationResponse);
        }
        else{
            this.logger.info(gameInvitationResponse.getReceiver());
            messagingTemplate.convertAndSend("/topic/gameRefused/" + gameInvitationResponse.getReceiver(), gameInvitationResponse);
        }
    }

    public Question getQuestionByTopic(String topic) {
        return questionsForGame.get(topic).poll();
    }

}
