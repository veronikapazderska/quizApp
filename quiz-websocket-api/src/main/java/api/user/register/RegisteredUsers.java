package api.user.register;

import api.user.User;
import lombok.*;

import java.util.List;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class RegisteredUsers {
    public List<User> registeredUsers;
}
