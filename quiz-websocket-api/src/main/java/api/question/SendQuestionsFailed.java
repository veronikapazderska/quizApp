package api.question;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class SendQuestionsFailed {
    public final String message;
}
