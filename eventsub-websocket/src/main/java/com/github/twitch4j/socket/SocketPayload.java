package com.github.twitch4j.socket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.twitch4j.common.util.TypeConvert;
import com.github.twitch4j.eventsub.EventSubSubscription;
import com.github.twitch4j.eventsub.events.EventSubEvent;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

@Data
@Setter(AccessLevel.PRIVATE)
public class SocketPayload {

    @Nullable
    private EventSubSocketInformation websocket; // not present on notification

    @Nullable
    private EventSubSubscription subscription; // is present on notification

    @Nullable
    @JsonProperty("event")
    private Object eventData; // is present on notification

    @Nullable
    public EventSubEvent getParsedEvent() {
        // this approach is computationally inefficient but avoids writing a custom deserializer
        return subscription != null ? TypeConvert.convertValue(eventData, subscription.getType().getEventClass()) : null;
    }

}
