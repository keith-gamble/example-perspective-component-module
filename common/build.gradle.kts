// Apply the java-library plugin which is appropriate for Java libraries
// that are consumed by other projects
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
    // compileOnly dependencies are only needed at compile time and won't be included in the final jar
    // This is equivalent to Maven's 'provided' scope
    // These dependencies are provided by the Ignition runtime
    
    // Core Ignition SDK classes
	compileOnly(libs.ignition.common)
    // Perspective-specific common classes
    compileOnly(libs.ignition.perspective.common)
    // Google's Guava library (provided by Ignition)
    compileOnly(libs.google.guava)
}