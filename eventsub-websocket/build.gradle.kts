// In this section you declare the dependencies for your production and test code
dependencies {
	// Twitch4J Modules
	api(project(":twitch4j-eventsub-common"))
}

base {
	archivesName.set("twitch4j-eventsub-websocket")
}

publishing.publications.withType<MavenPublication> {
	pom {
		name.set("Twitch4J API - EventSub WebSocket Module")
		description.set("EventSub WebSocket dependency")
	}
}
