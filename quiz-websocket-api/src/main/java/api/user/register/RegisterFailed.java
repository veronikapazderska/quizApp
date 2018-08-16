package api.user.register;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class RegisterFailed {
    public final String message;
}
