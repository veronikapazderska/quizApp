package api.user;

import api.user.LeaderboardResponse;
import lombok.*;

import java.util.List;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class LeaderboardResponseList {
    public List<LeaderboardResponse> leaderboardResponseList;
}
