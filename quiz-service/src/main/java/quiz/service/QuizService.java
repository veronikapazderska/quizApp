package quiz.service;

import api.game.GameOverResponse;
import api.question.*;
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
    private UserService userService;

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

        ans.add("Plato");
        ans.add("Socrates");
        ans.add("Pythagoras");
        ans.add("Aristotle");
        this.questions.add(Question.builder()
                .questionText("With which Greek philosopher would you associate the phrase, \"I know that I know nothing.\"?")
                .category("History")
                .correctAnswer("Socrates")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();

        ans.add("32");
        ans.add("31");
        ans.add("50");
        ans.add("21");
        this.questions.add(Question.builder()
                .questionText("How many sonatas did Ludwig van Beethoven write?")
                .category("History")
                .correctAnswer("32")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();

        ans.add("February 6, 1889");
        ans.add("June 12, 1889");
        ans.add("April 20, 1889");
        ans.add("April 16, 1889");
        this.questions.add(Question.builder()
                .questionText("Adolf Hitler was born on which date?")
                .category("History")
                .correctAnswer("April 20, 1889")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();

        ans.add("Drug Overdose");
        ans.add("Knife Attack");
        ans.add("House Fire");
        ans.add("Gunshot");
        this.questions.add(Question.builder()
                .questionText("What was the cause of Marilyn Monroes death?")
                .category("Entertainment")
                .correctAnswer("Drug Overdose")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();

        ans.add("Ricky Martin");
        ans.add("Bruno Mars");
        ans.add("Joaquin Phoenix");
        ans.add("Charlie Sheen");
        this.questions.add(Question.builder()
                .questionText("By what name is Carlos Estevez better known?")
                .category("Entertainment")
                .correctAnswer("Charlie Sheen")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();

        ans.add("Travis Scott");
        ans.add("Lil Wayne");
        ans.add("Drake");
        ans.add("2 Chainz");
        this.questions.add(Question.builder()
                .questionText("Aubrey Graham is better known as?")
                .category("Entertainment")
                .correctAnswer("Drake")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();


        ans.add("1");
        ans.add("2");
        ans.add("8");
        ans.add("32");
        this.questions.add(Question.builder()
                .questionText("What amount of bits commonly equals one byte?")
                .category("Science")
                .correctAnswer("8")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();


        ans.add("Motherboard");
        ans.add("Central Processing Unit");
        ans.add("Hard Disk Drive");
        ans.add("Random Access Memory");
        this.questions.add(Question.builder()
                .questionText("Which computer hardware device provides an interface for all other connected devices to communicate?")
                .category("Science")
                .correctAnswer("Motherboard")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();


        ans.add("Trusty Tahr");
        ans.add("Mystic Mansion");
        ans.add("Utopic Unicorn");
        ans.add("Wily Werewolf");
        this.questions.add(Question.builder()
                .questionText("Which one of these is not an official development name for a Ubuntu release?")
                .category("Science")
                .correctAnswer("Mystic Mansion")
                .possibleAnswers(new ArrayList<>(ans))
                .build());
        ans.clear();


    }


    public List<Question> generateQuestions() {

        if (questions != null && !questions.isEmpty()) {
            Collections.shuffle(questions);
            int randomSeriesLength = 1;
            //TODO: change back the length to bigger number
            final List<Question> questionsToSend = questions.subList(0, randomSeriesLength);
            this.logger.info("The number of questions is " + questions.size());
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
        this.logger.info("==================" + questionRequest.toString() + "=======================");
        if (this.requests == null) {
            this.requests = new HashMap<>();
            this.requests.put(questionRequest.topic, 1);
            this.logger.info("=================== TOVA E PURVIQT IF ========================");
        } else {
            if (this.requests.containsKey(questionRequest.getTopic())) {
                this.logger.info("================== VLIZA V IF-a =====================");
                if (this.requests.get(questionRequest.getTopic()) == 1) {
                    this.logger.info("================ TOPIC = 1 =======================");
                    final Question questionToSend = gameService.getQuestionByTopic(questionRequest.getTopic());
                    if (questionToSend != null) {
                        messagingTemplate.convertAndSend("/topic/questionResponse/" + questionRequest.getTopic(), questionToSend);
                        this.requests.put(questionRequest.getTopic(), 2);
                        this.logger.info(questionToSend.toString());
                    } else {
                        this.logger.info("===================== EMPPTY==========================");
                        final GameOverResponse gameOverResponse = GameOverResponse.builder().message("Game Over").build();
                        messagingTemplate.convertAndSend("/topic/gameOver/" + questionRequest.getTopic(), gameOverResponse);
                        if(this.results != null){
                            messagingTemplate.convertAndSend("/topic/gameResults/" + questionRequest.getTopic(), this.results);
                        }
                    }
                } else {
                    this.logger.info("================ TOPIC = 2 =======================");
                    this.requests.put(questionRequest.getTopic(), this.requests.get(questionRequest.getTopic()) - 1);
                }
            }
            else {
                //this.requests.put(questionRequest.getTopic(), )
            }
            this.logger.info("================== NE VLIZA V IF-a =====================");
        }

    }


    public void handleQuestionAnswerRequest(QuestionAnswer questionAnswer) {
        final QuestionRequest questionRequest = QuestionRequest.builder().topic(questionAnswer.getTopic()).build();
        if (this.answers == null) {
            this.answers = new HashMap<>();
            this.answers.put(questionAnswer.topic, 1);
            this.checkAnswer(questionAnswer);
        } else {
            if (this.answers.get(questionAnswer.topic) == null) {
                this.answers.put(questionAnswer.topic, 1);
                this.checkAnswer(questionAnswer);
            } else {
                if (this.answers.get(questionAnswer.topic) == 1) {
                    this.answers.put(questionAnswer.topic, 2);
                    this.checkAnswer(questionAnswer);
                } else {
                    this.answers.put(questionAnswer.topic, this.answers.get(questionAnswer.topic) - 1);
                    this.checkAnswer(questionAnswer);
                }
            }
        }
        this.handleQuestionRequest(questionRequest);
    }

    public void handleNewQuestionRequest(NewQuestionRequest newQuestionRequest){
        List<String> answers = new ArrayList<>();
        answers.add(newQuestionRequest.correctAnswer);
        answers.add(newQuestionRequest.wrongAnswer1);
        answers.add(newQuestionRequest.wrongAnswer2);
        answers.add(newQuestionRequest.wrongAnswer3);
        final Question question = Question.builder().questionText(newQuestionRequest.questionText)
                .category(newQuestionRequest.category).correctAnswer(newQuestionRequest.correctAnswer)
                .possibleAnswers(new ArrayList<>(answers)).build();
        this.questions.add(question);

        for(Question q : this.questions){
            if(q.questionText.equals(newQuestionRequest.questionText)){
                final SendNewQuestionSuccessful sendNewQuestionSuccessful = SendNewQuestionSuccessful.builder().message("Your question was sent successfully").build();
                messagingTemplate.convertAndSend("/topic/sendNewQuestionSuccessful", sendNewQuestionSuccessful);
                return;
            }
        }
        final SendNewQuestionFailed sendNewQuestionFailed = SendNewQuestionFailed.builder().message("Your question wasn't sent. Please try again!").build();
        messagingTemplate.convertAndSend("/topic/sendNewQuestionFailed", sendNewQuestionFailed);
    }

    private void checkAnswer(QuestionAnswer questionAnswer) {

        if(questionAnswer.getCorrectAnswer().equals(questionAnswer.getAnswerChosen())){
            if (this.results.containsKey(questionAnswer.getUsername())) {
                this.results.put(questionAnswer.getUsername(), this.results.get(questionAnswer.getUsername()) + 10);
            } else {
                this.results.put(questionAnswer.getUsername(), 10);
            }
        }
        else {
            if (this.results.containsKey(questionAnswer.getUsername())) {
                this.results.put(questionAnswer.getUsername(), this.results.get(questionAnswer.getUsername()) + 0);
            } else {
                this.results.put(questionAnswer.getUsername(), 0);
            }
        }
    }




}