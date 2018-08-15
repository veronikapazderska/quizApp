package api.user.register;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class RegisterRequest {
    public final String username;
    public final String password;
    public final String firstName;
    public final String lastName;
    public final int age;
    public final int points;
}
