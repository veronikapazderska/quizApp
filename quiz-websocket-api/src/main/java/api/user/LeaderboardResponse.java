package api.user;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class LeaderboardResponse {
    public String username;
    public int points;
    public String firstName;
    public String lastName;
}
