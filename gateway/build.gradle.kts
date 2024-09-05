plugins {
    `java-library`
}


java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    api(projects.common)

	// This is required to get the :web project .jar added into the modl file
	modlImplementation(projects.web)

    compileOnly(libs.ignition.common)
    compileOnly(libs.ignition.gateway.api)
    implementation(libs.ignition.perspective.gateway)
    implementation(libs.ignition.perspective.common)
}