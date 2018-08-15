package api.user.login;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class LoginSuccessful {
    public final String firstName;
    public final String lastName;
    public final int points;
}
