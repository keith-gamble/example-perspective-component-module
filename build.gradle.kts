// Apply the IA Module SDK plugin for building Ignition modules
// and the Eclipse plugin for better IDE support
plugins {
    // The main plugin that provides tasks for building and deploying Ignition modules
	id("io.ia.sdk.modl") version("0.3.0")
    // Added for better IDE support with Eclipse & VS Code
    id("eclipse")
}

// Configure settings that apply to all projects in the build
allprojects {
    // Set the version for all projects. Used in artifact naming and module version
    version = "0.0.1-SNAPSHOT"
    // Apply the eclipse plugin to all projects for consistent IDE support
    apply(plugin = "eclipse")
}

// Configure the Ignition module build settings
ignitionModule {
    // Basic module metadata
    name.set("Example Component Library")  // The human-readable name shown in the Gateway
    fileName.set("Example-Component-Library.modl")  // The output file name
    id.set("dev.kgamble.perspective.examples.ExampleComponentLibrary")  // Unique module identifier
    moduleVersion.set("${project.version}")  // Version from allprojects block
    license.set("LICENSE.txt")  // License file to include
    moduleDescription.set("A module that adds Example React components to Perspective.")
    requiredIgnitionVersion.set("8.1.44")  // Minimum Ignition version required

    // Define where each subproject's code should run
    // G = Gateway, D = Designer, GD = Both Gateway and Designer
    projectScopes.putAll(
        mapOf(
        ":gateway" to "G",    // Gateway project runs in Gateway
        ":web" to "G",        // Web resources are needed in Gateway
        ":designer" to "D",   // Designer project runs in Designer
        ":common" to "GD"     // Common code runs in both
		)
	)

    // Declare dependencies on other Ignition modules
    // This module depends on Perspective in both Gateway and Designer
    moduleDependencies.put("com.inductiveautomation.perspective", "GD")

    // Register the module hooks that initialize the module in each scope
    hooks.putAll(
        mapOf(
        "dev.kgamble.perspective.examples.gateway.ExampleComponentLibraryGatewayHook" to "G",
        "dev.kgamble.perspective.examples.designer.ExampleComponentLibraryDesignerHook" to "D"
		)
	)

    // Enable access to the IA artifact repository
    applyInductiveArtifactRepo.set(true)
    // Control module signing based on the 'signModule' property
    skipModlSigning.set(!findProperty("signModule").toString().toBoolean())
}

// Configure the Deploy task provided by the IA plugin
tasks.withType<io.ia.sdk.gradle.modl.task.Deploy>().configureEach {
    // Set the target gateway URL from gradle.properties or empty if not defined
    hostGateway.set(project.findProperty("hostGateway")?.toString() ?: "")
}

// Custom task for deep cleaning the project
val deepClean by tasks.registering {
    // Make this task depend on the clean task of all subprojects
    dependsOn(allprojects.map { "${it.path}:clean" })
    description = "Executes clean tasks and remove node plugin caches."
    // Additionally remove the Gradle cache directory
    doLast {
        delete(file(".gradle"))
    }
}