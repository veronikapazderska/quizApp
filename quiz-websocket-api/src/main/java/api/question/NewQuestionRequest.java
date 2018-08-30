package api.question;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class NewQuestionRequest {
    public String username;
    public String questionText;
    public String category;
    public String correctAnswer;
    public String wrongAnswer1;
    public String wrongAnswer2;
    public String wrongAnswer3;

}
