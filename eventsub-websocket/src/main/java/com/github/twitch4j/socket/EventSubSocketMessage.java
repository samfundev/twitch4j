package com.github.twitch4j.socket;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.PRIVATE)
public class EventSubSocketMessage {

    private SocketMessageMetadata metadata;

    private SocketPayload payload;

}
