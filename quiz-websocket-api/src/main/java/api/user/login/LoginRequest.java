package api.user.login;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class LoginRequest {
    public String username;
    public String password;
}
