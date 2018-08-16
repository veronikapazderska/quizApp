package api.user;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class ActiveUser {

    public String username;
    public String firstName;
    public String lastName;
    public int age;
    public int points;

}
