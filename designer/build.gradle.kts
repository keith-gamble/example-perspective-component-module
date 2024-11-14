// Apply the java-library plugin for library development
plugins {
    `java-library`
}

// Configure Java compilation settings
java {
    toolchain {
        // Set Java version to 17
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    // api dependencies are exposed to consumers of this project
    // Include the common project's classes
    api(projects.common)
    // Include the gateway project's classes
	api(projects.gateway)

    // compileOnly dependencies (Ignition runtime provides these)
    compileOnly(libs.ignition.common)  // Core Ignition classes
    compileOnly(libs.ignition.designer.api)  // Designer-specific API
    compileOnly(libs.ignition.perspective.common)  // Perspective common classes
    compileOnly(libs.ignition.perspective.designer)  // Perspective designer classes
    compileOnly(libs.google.guava)  // Google Guava library
}