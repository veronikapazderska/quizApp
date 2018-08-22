package quiz.service;

import api.question.Question2;
import api.question.QuestionsRequest;
import api.question.SendQuestionsFailed;
import api.question.SendQuestionsSuccess;
import api.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class QuizService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    public List<Question2> questions = new ArrayList<>();
    public List<Question2> questionsToSend = new ArrayList<>();

    private List<String> ans = new ArrayList<>();
    @PostConstruct
    public void init() {
        ans.add("Copacabana");
        ans.add("Havana");
        ans.add("Santiago");
        ans.add("Caracas");
        this.questions.add(Question2.builder()
                .questionText("What is the capital of Chile?")
                .category("Geography")
                .correctAnswer("Santiago")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        this.logger.info(ans.toString());
        ans.clear();

        ans.add("Cow");
        ans.add("Bull");
        ans.add("Horse");
        ans.add("Eagle");
        this.questions.add(Question2.builder()
                .questionText("A minotaur is half human half what?")
                .category("Mythology")
                .correctAnswer("Bull")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();

        ans.add("Scorpio");
        ans.add("Zodiac");
        ans.add("Tiger Mafia");
        ans.add("The Union");
        this.questions.add(Question2.builder()
                .questionText("In Marvel Comics, Taurus is the founder and leader of which criminal organization?")
                .category("Entertainment")
                .correctAnswer("Zodiac")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();

        ans.add("Microsoft");
        ans.add("Atari");
        ans.add("Apple");
        ans.add("Commodore");
        this.questions.add(Question2.builder()
                .questionText("Which company was established on April 1st, 1976 by Steve Jobs, Steve Wozniak and Ronald Wayne?")
                .category("Science")
                .correctAnswer("Apple")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();

        ans.add("Scorpion");
        ans.add("Snake");
        ans.add("Bull");
        ans.add("Horse");
        this.questions.add(Question2.builder()
                .questionText("Which animal features on the logo for Abarth, the motorsport division of Fiat?")
                .category("Vehicles")
                .correctAnswer("Scorpion")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();

        ans.add("Ruth Jones");
        ans.add("Angelina Jolie");
        ans.add("Catherine Zeta-Jones");
        ans.add("Jennifer Aniston");
        this.questions.add(Question2.builder()
                .questionText("Which actress married Michael Douglas in 2000?")
                .category("Entertainment")
                .correctAnswer("Catherine Zeta-Jones")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();

        ans.add("60");
        ans.add("15");
        ans.add("90");
        ans.add("40");
        this.questions.add(Question2.builder()
                .questionText("In Roman Numerals, what does XL equate to?")
                .category("Science")
                .correctAnswer("40")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();

        ans.add("Greenwich Mean Time");
        ans.add("Global Meridian Time");
        ans.add("General Median Time");
        ans.add("Glasgow Man Time");
        this.questions.add(Question2.builder()
                .questionText("What do the letters in the GMT time zone stand for?")
                .category("Geography")
                .correctAnswer("Greenwich Mean Time")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();
    }


    public void generateQuestions() {

        if (questions != null && !questions.isEmpty()) {

            Collections.shuffle(questions);
            int randomSeriesLength = 3;
            questionsToSend = questions.subList(0, randomSeriesLength);
            this.logger.info(questionsToSend.toString());
            final SendQuestionsSuccess sendQuestionsSuccess = SendQuestionsSuccess.builder().questions(questionsToSend).build();
            this.messagingTemplate.convertAndSend("/topic/sendQuestionsSuccess", sendQuestionsSuccess);
            return;

        }
        final SendQuestionsFailed sendQuestionsFailed = SendQuestionsFailed.builder().message("Ooops. Something went wrong.").build();
        this.logger.info(sendQuestionsFailed.message);
        messagingTemplate.convertAndSend("/topic/sendQuestionsFailed", sendQuestionsFailed);
    }


}
