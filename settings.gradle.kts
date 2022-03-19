rootProject.name = "Twitch4J"

include(
	":common",
	":auth",
	":chat",
	":eventsub-common",
	":eventsub-websocket",
	":rest-extensions",
	":rest-helix",
	":rest-kraken",
	":rest-tmi",
	":pubsub",
	":graphql",
	":twitch4j"
)

project(":common").name = "twitch4j-common"
project(":auth").name = "twitch4j-auth"
project(":chat").name = "twitch4j-chat"
project(":eventsub-common").name = "twitch4j-eventsub-common"
project(":eventsub-websocket").name = "twitch4j-eventsub-websocket"
project(":rest-extensions").name = "twitch4j-extensions"
project(":rest-helix").name = "twitch4j-helix"
project(":rest-kraken").name = "twitch4j-kraken"
project(":rest-tmi").name = "twitch4j-messaginginterface"
project(":pubsub").name = "twitch4j-pubsub"
project(":graphql").name = "twitch4j-graphql"
project(":twitch4j").name = "twitch4j"
