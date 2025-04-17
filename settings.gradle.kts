// Configure how Gradle finds and uses plugins
pluginManagement {
    repositories {
        gradlePluginPortal()  // Standard Gradle plugin repository
        mavenCentral()        // Central Maven repository
        mavenLocal()          // Local Maven repository
        // add the IA repo to pull in the module-signer artifact.  Can be removed if the module-signer is maven
        // published locally from its source-code and loaded via mavenLocal.
        maven {
            url = uri("https://nexus.inductiveautomation.com/repository/public/")
        }
    }
}

// Configure how Gradle resolves dependencies
dependencyResolutionManagement {
    // Prefer repositories defined in settings over project-specific ones
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)

    repositories {
        mavenLocal()  // Check local Maven repository first
        // IA's repository for SDK dependencies
        maven {
            url = uri("https://nexus.inductiveautomation.com/repository/public/")
        }

        // Declare the Node.js download repository.  We do this here so that we can continue to have repositoryMode set
        // to 'PREFER SETTINGS', as the node plugin will respect that and not set the node repo, meaning we can't
        // resolve the node runtime we need for building the web packages.
        ivy {
            name = "Node.js"
            setUrl("https://nodejs.org/dist/")
            patternLayout {
                // Define the pattern for finding Node.js artifacts
                artifact("v[revision]/[artifact](-v[revision]-[classifier]).[ext]")
            }
            metadataSources {
                artifact()  // Only look for artifacts, no ivy.xml files
            }
            content {
                // Only use this repository for Node.js
                includeModule("org.nodejs", "node")
            }
        }
    }
}

// Enable type-safe project accessors for cleaner build scripts
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// Set the root project name
rootProject.name = "example-component-library"

// Include all subprojects in the build
include(":", ":common", ":gateway", ":designer", ":web")