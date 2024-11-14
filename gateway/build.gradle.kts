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
    // Regular implementation dependency - included in the final jar
    implementation(projects.common)

    // Special configuration for module dependencies
	// This ensures the web project's output is included in the module
	modlImplementation(projects.web)

    // Runtime dependencies provided by Ignition
    compileOnly(libs.ignition.common)  // Core Ignition classes
    compileOnly(libs.ignition.gateway.api)  // Gateway-specific API
    // Perspective dependencies that need to be included
    implementation(libs.ignition.perspective.gateway)
    implementation(libs.ignition.perspective.common)
}