// In this section you declare the dependencies for your production and test code
dependencies {
	// Jackson (JSON)
	api(group = "com.fasterxml.jackson.core", name = "jackson-databind")
	api(group = "com.fasterxml.jackson.datatype", name = "jackson-datatype-jsr310")

	// Guava
	api(group = "com.google.guava", name = "guava")

	// Twitch4J Modules
	api(project(":twitch4j-common"))
}

tasks.javadoc {
	options {
		title = "Twitch4J (v${version}) - EventSub Common Module API"
		windowTitle = "Twitch4J (v${version}) - EventSub Common Module API"
	}
}

publishing.publications.withType<MavenPublication> {
	pom {
		name.set("Twitch4J API - EventSub Common Module")
		description.set("EventSub Common dependency")
	}
}
