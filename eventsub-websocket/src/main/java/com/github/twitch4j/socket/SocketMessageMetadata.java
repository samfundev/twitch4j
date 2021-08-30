package com.github.twitch4j.socket;

import com.github.twitch4j.eventsub.subscriptions.SubscriptionType;
import com.github.twitch4j.eventsub.subscriptions.SubscriptionTypes;
import com.github.twitch4j.socket.enums.SocketMessageType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

@Data
@Setter(AccessLevel.PRIVATE)
public class SocketMessageMetadata {

    private String messageId;

    private SocketMessageType messageType;

    private Instant messageTimestamp;

    @Nullable
    private String subscriptionType; // present on notification status

    @Nullable
    private String subscriptionVersion; // present on notification status

    @Nullable
    public SubscriptionType<?, ?, ?> getParsedSubscriptionType() {
        return subscriptionType != null ? SubscriptionTypes.getSubscriptionType(subscriptionType, subscriptionVersion) : null;
    }

}
