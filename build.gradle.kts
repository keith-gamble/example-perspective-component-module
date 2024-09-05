plugins {
	id("io.ia.sdk.modl") version("0.3.0")
}

allprojects {
    version = "0.0.1-SNAPSHOT"
}

ignitionModule {
    name.set("Example Component Library")
    fileName.set("Example-Component-Library.modl")
    id.set("dev.bwdesigngroup.perspective.examples.ExampleComponentLibrary")
    moduleVersion.set("${project.version}")
    license.set("LICENSE.txt")
    moduleDescription.set("A module that adds Example React components to Perspective.")
    requiredIgnitionVersion.set("8.1.32")

    projectScopes.putAll(
        mapOf(
        ":gateway" to "G",
        ":web" to "G",
        ":designer" to "D",
        ":common" to "GD"
		)
	)

    moduleDependencies.put("com.inductiveautomation.perspective", "GD")

    hooks.putAll(
        mapOf(
        "dev.bwdesigngroup.perspective.examples.gateway.ExampleComponentLibraryGatewayHook" to "G",
        "dev.bwdesigngroup.perspective.examples.designer.ExampleComponentLibraryDesignerHook" to "D"
		)
	)

    applyInductiveArtifactRepo.set(true)
    skipModlSigning.set(!findProperty("signModule").toString().toBoolean())
}

tasks.withType<io.ia.sdk.gradle.modl.task.Deploy>().configureEach {
    hostGateway.set(project.findProperty("hostGateway")?.toString() ?: "")
}

val deepClean by tasks.registering {
    dependsOn(allprojects.map { "${it.path}:clean" })
    description = "Executes clean tasks and remove node plugin caches."
    doLast {
        delete(file(".gradle"))
    }
}