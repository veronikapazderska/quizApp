package api.question;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class SendNewQuestionSuccessful {
    public String message;
}
