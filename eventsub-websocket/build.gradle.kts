// In this section you declare the dependencies for your production and test code
dependencies {
	// Twitch4J Modules
	api(project(":eventsub-common"))
}

publishing.publications.withType<MavenPublication> {
	artifactId = "twitch4j-eventsub-websocket"
	pom {
		name.set("Twitch4J API - EventSub WebSocket Module")
		description.set("EventSub WebSocket dependency")
	}
}
