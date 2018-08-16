package api.user;

import lombok.*;

import java.util.List;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class ActiveUsers {
    public List<ActiveUser> activeUsers;
}
