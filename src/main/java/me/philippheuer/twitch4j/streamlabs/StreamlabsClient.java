package me.philippheuer.twitch4j.streamlabs;

import lombok.Builder;
import lombok.Singular;
import me.philippheuer.twitch4j.helper.QueryRequestInterceptor;
import me.philippheuer.twitch4j.helper.RestClient;
import me.philippheuer.twitch4j.streamlabs.endpoints.DonationEndpoint;
import me.philippheuer.twitch4j.streamlabs.endpoints.UserEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.Getter;
import lombok.Setter;
import me.philippheuer.twitch4j.pubsub.TwitchPubSub;
import org.springframework.util.Assert;

@Getter
@Setter
public class StreamlabsClient {

	/**
	 * Logger
	 */
	private final Logger logger = LoggerFactory.getLogger(TwitchPubSub.class);

	/**
	 * Rest Client
	 */
	private RestClient restClient = new RestClient();

	/**
	 * Streamlabs API Endpoint
	 */
	public final String streamlabsEndpoint = "https://www.twitchalerts.com/api";

	/**
	 * Streamlabs API Version
	 */
	public final String streamlabsEndpointVersion = "v1.0";

	/**
	 * Streamlabs Client Id
	 */
	@Singular
	private String clientId;

	/**
	 * Streamlabs Client Secret
	 */
	@Singular
	private String clientSecret;

	/**
	 * Constructor
	 */
	public StreamlabsClient(String clientId, String clientSecret) {
		super();

		setClientId(clientId);
		setClientSecret(clientSecret);

		// Initialize REST Client
		getRestClient().putRestInterceptor(new QueryRequestInterceptor("access_token", getClientId()));
	}

	/**
	 * Client Builder
	 */
	@Builder(builderMethodName = "builder")
	public static StreamlabsClient streamlabsClientBuilder(String clientId, String clientSecret) {
		// Reqired Parameters
		Assert.notNull(clientId, "You need to provide a client id!");
		Assert.notNull(clientSecret, "You need to provide a client secret!");

		// Initalize instance
		final StreamlabsClient streamlabsClient = new StreamlabsClient(clientId, clientSecret);

		// Return builded instance
		return streamlabsClient;
	}

	/**
	 * Get the full api endpoint address.
	 * @return The full api endpoint url.
	 */
	public String getEndpointUrl() {
		return String.format("%s/%s", getStreamlabsEndpoint(), getStreamlabsEndpointVersion());
	}

	/**
	 * Get User Endpoint
	 */
	public UserEndpoint getUserEndpoint() {
		return new UserEndpoint(this);
	}

	/**
	 * Get User Endpoint
	 */
	public DonationEndpoint getDonationEndpoint() {
		return new DonationEndpoint(this);
	}
}
