// Apply the IA Module SDK plugin for building Ignition modules
// and the Eclipse plugin for better IDE support
plugins {
    // The main plugin that provides tasks for building and deploying Ignition modules
	id("io.ia.sdk.modl") version("0.5.0")
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
    requiredIgnitionVersion.set("8.3.0")  // Minimum Ignition version required

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

// Ignition 8.3 removed module hot-swapping and the developer upload servlet that the plugin's
// `deployModl` task posts to. This task uses the 8.3 REST API instead: it uploads the module,
// accepts its certificate and license, and stages the install. The gateway applies staged
// installs on its next restart.
val deployModule by tasks.registering {
    group = "ignition"
    description = "Uploads the module to an Ignition 8.3 gateway and stages it for install on the next restart."
    dependsOn(tasks.named("build"))

    doLast {
        // Gateway URL and API token, from gradle.properties or -P flags
        val gatewayUrl = (findProperty("hostGateway")?.toString() ?: "").trimEnd('/')
        val apiToken = findProperty("ignitionApiToken")?.toString() ?: ""
        if (gatewayUrl.isBlank()) {
            throw GradleException("hostGateway is not set. Configure it in gradle.properties or pass -PhostGateway=<url>")
        }
        if (apiToken.isBlank()) {
            throw GradleException("ignitionApiToken is not set. Configure it in gradle.properties or pass -PignitionApiToken=<token>")
        }

        // Prefer the signed module when signing is enabled, otherwise the unsigned one
        val baseName = ignitionModule.fileName.get().removeSuffix(".modl")
        val signed = layout.buildDirectory.file("$baseName.modl").get().asFile
        val unsigned = layout.buildDirectory.file("$baseName.unsigned.modl").get().asFile
        val modlFile = listOf(signed, unsigned).firstOrNull { it.exists() }
            ?: throw GradleException("No module found at ${signed.path} or ${unsigned.path}")

        // Sends one authenticated POST, returning the status code and response body
        fun post(path: String, body: File? = null): Pair<Int, String> {
            val conn = java.net.URI("$gatewayUrl/data/api/v1/modules/$path").toURL()
                .openConnection() as java.net.HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("X-Ignition-API-Token", apiToken)
            conn.connectTimeout = 30_000
            conn.readTimeout = 60_000
            if (body != null) {
                conn.doOutput = true
                conn.setRequestProperty("Content-Type", "application/octet-stream")
                body.inputStream().use { input -> conn.outputStream.use { input.copyTo(it) } }
            }
            val code = conn.responseCode
            val stream = if (code < 400) conn.inputStream else conn.errorStream
            return code to (stream?.bufferedReader()?.readText() ?: "")
        }

        logger.lifecycle("Uploading ${modlFile.name} to $gatewayUrl")
        val (uploadCode, uploadBody) = post("upload?fileName=${modlFile.name}", modlFile)
        if (uploadCode != 200) throw GradleException("Upload failed ($uploadCode): $uploadBody")
        val moduleId = Regex(""""moduleId"\s*:\s*"([^"]+)"""").find(uploadBody)?.groupValues?.get(1)
            ?: throw GradleException("No moduleId in upload response: $uploadBody")
        val encodedId = java.net.URLEncoder.encode(moduleId, "UTF-8")

        // 200 = accepted, 409 = already accepted, 400 = nothing to accept
        for (step in listOf("certificate", "eula")) {
            val (code, body) = post("$step?moduleId=$encodedId")
            if (code !in listOf(200, 400, 409)) logger.warn("Accepting $step returned $code: $body")
        }

        val (installCode, installBody) = post("install?moduleId=$encodedId")
        if (installCode != 200) throw GradleException("Install failed ($installCode): $installBody")

        // Optionally restart the gateway so the staged install takes effect (-PrestartGateway=true)
        if (findProperty("restartGateway")?.toString().toBoolean()) {
            val conn = java.net.URI("$gatewayUrl/data/api/v1/restart-tasks/restart?confirm=true").toURL()
                .openConnection() as java.net.HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("X-Ignition-API-Token", apiToken)
            val code = conn.responseCode
            if (code >= 400) throw GradleException("Gateway restart request failed ($code)")
            logger.lifecycle("Staged $moduleId for install and requested a gateway restart.")
        } else {
            logger.lifecycle("Staged $moduleId for install. Restart the gateway to load it, or rerun with -PrestartGateway=true.")
        }
    }
}

// The plugin's `deployModl` task posts to the 8.1 developer upload servlet, which 8.3 removed.
// Fail fast with a pointer to `deployModule` instead of failing with an HTTP error.
tasks.named("deployModl") {
    doFirst {
        throw GradleException("deployModl does not work on Ignition 8.3. Use `./gradlew deployModule`, or rebuild and restart the Docker gateway.")
    }
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