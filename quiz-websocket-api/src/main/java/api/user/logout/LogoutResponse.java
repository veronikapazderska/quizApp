package api.user.logout;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class LogoutResponse {
    public final String message;
}
