package api.question;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class SendQuestionsSuccess {
    public final List<Question2> questions;
}
