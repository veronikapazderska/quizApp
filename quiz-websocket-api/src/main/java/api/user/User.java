package api.user;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class User {
    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public int age;
    public int points;

}
