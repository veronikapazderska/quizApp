package quiz.service;

import api.game.GameOverResponse;
import api.question.Question;
import api.question.QuestionAnswer;
import api.question.QuestionRequest;
import api.question.SendQuestionsFailed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class QuizService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private GameService gameService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    private List<Question> questions = new ArrayList<>();

    private List<String> ans = new ArrayList<>();
    private Map<String, Integer> answers;
    private Map<String, Integer> requests;
    private Map<String, Integer> results = new HashMap<>();

    @PostConstruct
    public void init() {
        ans.add("Copacabana");
        ans.add("Havana");
        ans.add("Santiago");
        ans.add("Caracas");
        this.questions.add(Question.builder()
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
        this.questions.add(Question.builder()
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
        this.questions.add(Question.builder()
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
        this.questions.add(Question.builder()
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
        this.questions.add(Question.builder()
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
        this.questions.add(Question.builder()
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
        this.questions.add(Question.builder()
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
        this.questions.add(Question.builder()
                .questionText("What do the letters in the GMT time zone stand for?")
                .category("Geography")
                .correctAnswer("Greenwich Mean Time")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();

        ans.add("Apollo");
        ans.add("Jupiter");
        ans.add("Vulcan");
        ans.add("Mercury");
        this.questions.add(Question.builder()
                .questionText("Who was the Roman god of fire?")
                .category("Mythology")
                .correctAnswer("Vulcan")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();

        ans.add("Perseus");
        ans.add("Hercules");
        ans.add("Orpheus");
        ans.add("Daedalus");
        this.questions.add(Question.builder()
                .questionText("Which figure from Greek mythology traveled to the underworld to return his wife Eurydice to the land of the living?")
                .category("Mythology")
                .correctAnswer("Orpheus")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();

        ans.add("Goldfinger");
        ans.add("Dr. No");
        ans.add("From Russia With Love");
        ans.add("Thunderball");
        this.questions.add(Question.builder()
                .questionText("What was the first James Bond film?")
                .category("Entertainment")
                .correctAnswer("Dr. No")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();

        ans.add("Pink Floyd");
        ans.add("AC/DC");
        ans.add("Metallica");
        ans.add("Red Hot Chili Peppers");
        this.questions.add(Question.builder()
                .questionText("Which of these bands is the oldest?")
                .category("Entertainment")
                .correctAnswer("Pink Floyd")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();

        ans.add("Power Supply");
        ans.add("Processor");
        ans.add("Hard Drive");
        ans.add("Video Card");
        this.questions.add(Question.builder()
                .questionText("Generally, which component of a computer draws the most power?")
                .category("Science")
                .correctAnswer("Video Card")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();
    }


    public List<Question> generateQuestions() {

        if (questions != null && !questions.isEmpty()) {
            Collections.shuffle(questions);
            int randomSeriesLength = 4;
            final List<Question> questionsToSend = questions.subList(0, randomSeriesLength);
            this.logger.info(questionsToSend.toString());
            //final SendQuestionsSuccess sendQuestionsSuccess = SendQuestionsSuccess.builder().questions(questionsToSend).build();
            //this.messagingTemplate.convertAndSend("/topic/sendQuestionsSuccess", sendQuestionsSuccess);
            return questionsToSend;
        }

        final SendQuestionsFailed sendQuestionsFailed = SendQuestionsFailed.builder().message("Ooops. Something went wrong.").build();
        this.logger.info(sendQuestionsFailed.message);
        messagingTemplate.convertAndSend("/topic/sendQuestionsFailed", sendQuestionsFailed);
        return null;
    }

    public synchronized void handleQuestionRequest(QuestionRequest questionRequest) {
        if (this.requests == null) {
            this.requests = new HashMap<>();
            this.requests.put(questionRequest.topic, 1);
        } else {
            if (this.requests.containsKey(questionRequest.getTopic())) {
                if (this.requests.get(questionRequest.getTopic()) == 1) {
                    final Question questionToSend = gameService.getQuestionByTopic(questionRequest.getTopic());
                    if (questionToSend != null) {
                        messagingTemplate.convertAndSend("/topic/questionResponse/" + questionRequest.getTopic(), questionToSend);
                        this.requests.put(questionRequest.getTopic(), 2);
                    } else {
                        final GameOverResponse gameOverResponse = GameOverResponse.builder().message("Game Over").build();
                        messagingTemplate.convertAndSend("/topic/gameOver/" + questionRequest.getTopic(), gameOverResponse);
                        //TODO: publish game over message
                    }
                } else {
                    this.requests.put(questionRequest.getTopic(), this.requests.get(questionRequest.getTopic()) - 1);
                }
            }
        }
    }


    public void handleQuestionAnswerRequest(QuestionAnswer questionAnswer) {
        final QuestionRequest questionRequest = QuestionRequest.builder().topic(questionAnswer.getTopic()).build();
        if (this.answers == null) {
            this.answers = new HashMap<>();
            this.answers.put(questionAnswer.topic, 1);
        } else {
            if (this.answers.get(questionAnswer.topic) == null) {
                this.answers.put(questionAnswer.topic, 1);
            } else {
                if (this.answers.get(questionAnswer.topic) == 1) {
                    this.checkAnswer(questionAnswer);
                    this.answers.put(questionAnswer.topic, 2);
                } else {
                    this.answers.put(questionAnswer.topic, this.answers.get(questionAnswer.topic) - 1);
                    this.checkAnswer(questionAnswer);
                }
            }
        }
        if(this.results != null){
            this.logger.info("Results: " + this.results.toString());
        }
        this.handleQuestionRequest(questionRequest);

    }

    private void checkAnswer(QuestionAnswer questionAnswer) {
       /* for (Question question : this.questions) {
            if (question.questionText.equals(questionAnswer.getQuestionText())) {
                if (questionAnswer.getCorrectAnswer().equals(question.getCorrectAnswer())) {
                    if (this.results.containsKey(questionAnswer.getUsername())) {
                        this.results.put(questionAnswer.getUsername(), this.results.get(questionAnswer.getUsername()) + 10);
                    } else {
                        this.results.put(questionAnswer.getUsername(), 10);
                    }
                    messagingTemplate.convertAndSend("/topic/correctAnswer/" + questionAnswer.getUsername() + "/" + questionAnswer.getCorrectAnswer(), questionAnswer);
                }
            }
        }*/

        if(questionAnswer.getCorrectAnswer().equals(questionAnswer.getAnswerChosen())){
            if (this.results.containsKey(questionAnswer.getUsername())) {
                this.results.put(questionAnswer.getUsername(), this.results.get(questionAnswer.getUsername()) + 10);
                this.logger.info("The results are: " + this.results.toString());
            } else {
                this.results.put(questionAnswer.getUsername(), 10);
                this.logger.info("The results are: " + this.results.toString());
            }
        }
        else {
            if (this.results.containsKey(questionAnswer.getUsername())) {
                this.results.put(questionAnswer.getUsername(), this.results.get(questionAnswer.getUsername()) + 0);
            } else {
                this.results.put(questionAnswer.getUsername(), 0);
            }

        }
        //messagingTemplate.convertAndSend("/topic/correctAnswer/" + questionAnswer.getUsername() + "/" + questionAnswer.getCorrectAnswer(), questionAnswer);
    }

}