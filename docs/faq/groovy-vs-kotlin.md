# FAQ: Groovy vs Kotlin DSL for Gradle

## Why should we use Kotlin DSL over Groovy DSL for Gradle build scripts?

While both Groovy and Kotlin can be used for Gradle build scripts, there are several compelling reasons to prefer Kotlin DSL:

1. **Better IDE Support**: 
   - Kotlin DSL provides superior IDE support, including better code completion, refactoring, and navigation.
   - This leads to a more efficient development experience, especially in complex build scripts.

2. **Type Safety**:
   - Kotlin is a statically typed language, which means many errors can be caught at compile-time rather than runtime.
   - This reduces the likelihood of build script errors and makes debugging easier.

3. **Consistency with Kotlin Projects**:
   - If your project is already using Kotlin, using Kotlin DSL for Gradle scripts maintains language consistency across your codebase.

4. **Improved Readability**:
   - Kotlin's syntax is often more concise and readable, especially for developers already familiar with Java or Kotlin.
   - This can make build scripts easier to understand and maintain.

5. **Better Refactoring Support**:
   - Kotlin's strong typing and IDE integration make it easier to refactor build scripts as your project evolves.

6. **Null Safety**:
   - Kotlin's null safety features help prevent null pointer exceptions in build scripts.

7. **Access to Kotlin Features**:
   - You can use Kotlin's powerful features like extension functions, which can lead to more expressive and maintainable build scripts.

8. **Improved Performance**:
   - Kotlin DSL can offer better performance in terms of script compilation and execution, especially for larger projects.

9. **Future-Proofing**:
   - Gradle is increasingly focusing on Kotlin DSL, with some newer features being developed primarily for Kotlin DSL.

10. **Learning Curve**:
    - While there's an initial learning curve, knowledge of Kotlin DSL is more transferable to other Kotlin-based projects and tools.

## Are there any downsides to using Kotlin DSL?

While Kotlin DSL offers many advantages, there are a few potential downsides to consider:

1. **Initial Learning Curve**: If your team is more familiar with Groovy, there may be a short-term productivity hit as developers learn Kotlin syntax.

2. **Migration Effort**: Converting existing Groovy-based build scripts to Kotlin can be time-consuming for large projects.

3. **Community Resources**: While growing, there might still be more community resources and examples available for Groovy DSL.

## Conclusion

For most projects, especially new ones or those already using Kotlin, the benefits of Kotlin DSL for Gradle build scripts outweigh the drawbacks. The improved IDE support, type safety, and readability make it a strong choice for maintaining and scaling build scripts over time.

### Groovy DSL

Since this example repo uses Kotlin DSL, here is a quick example of each of the build scripts in Groovy DSL:

#### ./build.gradle
```groovy
plugins {
	id("io.ia.sdk.modl") version "0.3.0"
}

ext {
    sdk_version = "8.1.34"
}

allprojects {
    version = "0.0.1-SNAPSHOT"
}

ignitionModule {
    name = "Example Component Library"
    fileName = "Example-Component-Library.modl"
    id = "dev.bwdesigngroup.perspective.examples.ExampleComponentLibrary"
    moduleVersion = version
    license = "LICENSE.txt"
    moduleDescription = "A module that adds Example React components to Perspective."
    requiredIgnitionVersion = "8.1.0"

    projectScopes = [
        ":gateway": "G",
        ":web": "G",
        ":designer": "D",
        ":common": "GD"
    ]

    moduleDependencies = [ "com.inductiveautomation.perspective": "GD" ]

    hooks = [
        "dev.bwdesigngroup.perspective.examples.gateway.ExampleComponentLibraryGatewayHook": "G",
        "dev.bwdesigngroup.perspective.examples.designer.ExampleComponentLibraryDesignerHook": "D"
    ]

    applyInductiveArtifactRepo = true
    skipModlSigning = !findProperty("signModule").toString().toBoolean()
}

task deepClean() {
    dependsOn allprojects.collect { "${it.path}:clean" }
    description "Executes clean tasks and remove node plugin caches."
    doLast {
        delete(file(".gradle"))
    }
}

tasks.withType(io.ia.sdk.gradle.modl.task.Deploy).configureEach {
    hostGateway = project.hostGateway
}
```

#### ./settings.gradle
```
pluginManagement {
    repositories {
        gradlePluginPortal()
		mavenCentral()
        maven {
            url 'https://nexus.inductiveautomation.com/repository/public'
        }
    }
}

rootProject.name = "example-component-library"

include(
	":",
    ":common",
    ":gateway",
    ":designer",
    ":web"
)
```

#### ./common/build.gradle
```groovy
plugins {
    id "java-library"
}

sourceCompatibility = JavaVersion.VERSION_11
targetCompatibility = JavaVersion.VERSION_11

dependencies {
	compileOnly("com.inductiveautomation.ignitionsdk:ignition-common:${sdk_version}")
	compileOnly("com.inductiveautomation.ignitionsdk:perspective-common:${sdk_version}")
	compileOnly("com.google.guava:guava")
}

```

#### ./designer/build.gradle
```groovy

plugins {
    id "java-library"
}

sourceCompatibility = JavaVersion.VERSION_11
targetCompatibility = JavaVersion.VERSION_11

dependencies {
	api(project(":common"))
	api(project(":gateway"))

	compileOnly("com.inductiveautomation.ignitionsdk:ignition-common:${sdk_version}")
    compileOnly("com.inductiveautomation.ignitionsdk:designer-api:${sdk_version}")
	compileOnly("com.inductiveautomation.ignitionsdk:perspective-common:${sdk_version}")
	compileOnly("com.inductiveautomation.ignitionsdk:perspective-designer:${sdk_version}")
	compileOnly("com.google.guava:guava")
}

```

#### ./gateway/build.gradle
```groovy
plugins {
    id "java-library"
}

sourceCompatibility = JavaVersion.VERSION_11
targetCompatibility = JavaVersion.VERSION_11

dependencies {
    api(project(":common"))
	
	// This is required to get the :web project .jar added into the modl file
    modlImplementation(project(":web"))

    compileOnly("com.inductiveautomation.ignitionsdk:ignition-common:${sdk_version}")
    compileOnly("com.inductiveautomation.ignitionsdk:gateway-api:${sdk_version}")
    implementation("com.inductiveautomation.ignitionsdk:perspective-gateway:${sdk_version}")
    implementation("com.inductiveautomation.ignitionsdk:perspective-common:${sdk_version}")
}
```

#### ./web/build.gradle
```groovy
plugins {
    id 'java'
    id 'com.github.node-gradle.node' version '3.2.1'
}

sourceCompatibility = JavaVersion.VERSION_11
targetCompatibility = JavaVersion.VERSION_11

def projectOutput = "$buildDir/generated-resources/"

node {
    version = '22.6.0'
    npmVersion = '10.8.2'
    download = true
    nodeProjectDir = file("$projectDir")
}

task installDependencies(type: NpmTask) {
    args = ['install']
}

task webpack(type: NpmTask) {
    args = ['run', 'build']
    dependsOn installDependencies
    
    inputs.files(project.fileTree(".").matching {
        exclude("**/node_modules/**", "**/dist/**", "**/.awcache/**", "**/yarn-error.log")
    }.toList())

    outputs.files(fileTree(projectOutput))
}

processResources {
    dependsOn webpack
}

clean {
    delete 'dist'
}

task deepClean {
    doLast {
        delete(file(".gradle"))
        delete(file("node_modules"))
    }

    dependsOn clean
}

project(':gateway')?.tasks?.named('processResources')?.configure {
    dependsOn webpack
}

sourceSets {
    main {
        resources {
            srcDir projectOutput
        }
    }
}
```
