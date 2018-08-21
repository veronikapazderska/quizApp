package api.game;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class GameRequest {
    public String sender;
    public String receiver;
}
