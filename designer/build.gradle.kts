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
	api(projects.gateway)
    compileOnly(libs.ignition.common)
    compileOnly(libs.ignition.designer.api)
    compileOnly(libs.ignition.perspective.common)
    compileOnly(libs.ignition.perspective.designer)
    compileOnly(libs.google.guava)
}