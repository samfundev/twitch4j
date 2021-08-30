package com.github.twitch4j.socket;

import com.github.twitch4j.socket.enums.EventSubSocketStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

@Data
@Setter(AccessLevel.PRIVATE)
public class EventSubSocketInformation {

    private String id;

    private EventSubSocketStatus status;

    @Nullable
    private String disconnectReason; // present on disconnected status

    private Integer minimumMessageFrequencySeconds;

    @Nullable
    private String url; // present on reconnecting status

    private Instant connectedAt;

    @Nullable
    private Instant reconnectingAt; // present on reconnecting status

    @Nullable
    private Instant disconnectedAt; // present on disconnected status

}
