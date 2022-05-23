package com.github.twitch4j.helix.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Request;
import feign.Response;
import feign.jackson.JacksonDecoder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;

import static com.github.twitch4j.helix.interceptor.TwitchHelixClientIdInterceptor.AUTH_HEADER;
import static com.github.twitch4j.helix.interceptor.TwitchHelixClientIdInterceptor.BEARER_PREFIX;
import static com.github.twitch4j.helix.interceptor.TwitchHelixClientIdInterceptor.CLIENT_HEADER;
import static com.github.twitch4j.helix.interceptor.TwitchHelixHttpClient.getExtensionPubSubTarget;

public class TwitchHelixDecoder extends JacksonDecoder {

    public static final String REMAINING_HEADER = "Ratelimit-Remaining";

    private final TwitchHelixRateLimitTracker rateLimitTracker;

    public TwitchHelixDecoder(ObjectMapper mapper, TwitchHelixRateLimitTracker rateLimitTracker) {
        super(mapper);
        this.rateLimitTracker = rateLimitTracker;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException {
        // track rate limit for token
        Request request = response.request();
        String token = singleFirst(request.headers().get(AUTH_HEADER));
        if (token != null && token.startsWith(BEARER_PREFIX)) {
            // Parse remaining
            String remainingStr = singleFirst(response.headers().get(REMAINING_HEADER));
            Integer remaining;
            try {
                remaining = Integer.parseInt(remainingStr);
            } catch (NumberFormatException ignored) {
                remaining = null;
            }

            // Synchronize library buckets with twitch data
            if (remaining != null) {
                String bearer = token.substring(BEARER_PREFIX.length());
                if (request.httpMethod() == Request.HttpMethod.POST && request.requestTemplate().path().endsWith("/clips")) {
                    // Create Clip has a separate rate limit to synchronize
                    rateLimitTracker.updateRemainingCreateClip(bearer, remaining);
                } else if (request.httpMethod() == Request.HttpMethod.POST && request.requestTemplate().path().endsWith("/extensions/chat")) {
                    // Send Extension Chat Message rate limit
                    String clientId = request.headers().getOrDefault(CLIENT_HEADER, Collections.emptyList()).iterator().next();
                    String channelId = request.requestTemplate().queries().getOrDefault("broadcaster_id", Collections.emptyList()).iterator().next();
                    rateLimitTracker.updateRemainingExtensionChat(clientId, channelId, remaining);
                } else if (request.httpMethod() == Request.HttpMethod.POST && request.requestTemplate().path().endsWith("/extensions/pubsub")) {
                    // Send Extension PubSub Message rate limit
                    String clientId = request.headers().getOrDefault(CLIENT_HEADER, Collections.emptyList()).iterator().next();
                    String target = getExtensionPubSubTarget(request.body());
                    rateLimitTracker.updateRemainingExtensionPubSub(clientId, target, remaining);
                } else {
                    // Normal/global helix rate limit synchronization
                    rateLimitTracker.updateRemaining(bearer, remaining);
                }
            }
        }

        // delegate to JacksonDecoder
        return super.decode(response, type);
    }

    static String singleFirst(Collection<String> collection) {
        if (collection == null || collection.size() != 1) return null;
        return collection.toArray(new String[1])[0];
    }

}
