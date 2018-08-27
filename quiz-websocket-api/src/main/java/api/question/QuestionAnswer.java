package api.question;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class QuestionAnswer {
    public String topic;
    public String questionText;
    public String correctAnswer;
    public String answerChosen;
    public String username;
}
