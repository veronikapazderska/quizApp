package quiz.controller;

import api.game.GameInvitationResponse;
import api.game.GameRequest;
import api.question.QuestionAnswer;
import api.question.QuestionRequest;
import api.question.QuestionsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import quiz.service.GameService;
import quiz.service.QuizService;

@Controller
public class QuizWebSocketController {
    private static final Logger logger = LoggerFactory.getLogger(QuizWebSocketController.class);

    @Autowired
    private QuizService quizService;

    @MessageMapping("/getQuestionsRequest")
    public void getQuestionsHandler(@Payload QuestionsRequest questionsRequest){
        quizService.generateQuestions();
    }

    @MessageMapping("/requestQuestion")
    public void handleQuestionRequest(@Payload QuestionRequest questionRequest){
        quizService.handleQuestionRequest(questionRequest);
    }

    @MessageMapping("/answerQuestion")
    public void handleQuestionAnswer(@Payload QuestionAnswer questionAnswer){
        quizService.handleQuestionAnswerRequest(questionAnswer);

    }



}
