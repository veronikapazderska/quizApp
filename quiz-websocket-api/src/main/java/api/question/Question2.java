package api.question;

import lombok.*;

import java.util.List;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class Question2 {

    public String questionText;
    public String category;
    public String correctAnswer;
    public List<String> possibleAnswers;

}
