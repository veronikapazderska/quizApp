package api.user.logout;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class LoginRequest {
    public final String username;
}
