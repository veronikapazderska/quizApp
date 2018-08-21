package api.game;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class GameResponse {
    public boolean hasConfirmed;
    public String sender;
    public String receiver;

}
