package api.user.logout;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class LogoutRequest {
    public final String username;
}
