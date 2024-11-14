# Module Build System Guide

This guide explains the build system configuration used in the Example Component Library module. We'll break down how the Inductive Automation (IA) plugin works and detail the build configuration.

## Table of Contents

1. [Build System Overview](#build-system-overview)
2. [IA Module Plugin](#ia-module-plugin)
3. [Build Configuration Breakdown](#build-configuration-breakdown)
4. [Module Properties](#module-properties)
5. [Project Structure](#project-structure)
6. [Common Issues](#common-issues)

## Build System Overview

The build system is based on Gradle using the Kotlin DSL, with the IA Module Plugin (`io.ia.sdk.modl`) providing specialized tasks for building Ignition modules.

### Key Files

- `build.gradle.kts`: Main build configuration
- `settings.gradle.kts`: Project structure and repository configuration
- `gradle.properties`: Build properties and configuration values
- `gradle/libs.versions.toml`: Dependency version management

## IA Module Plugin

The IA Module Plugin (`io.ia.sdk.modl`) is the core tool for building Ignition modules. It's applied in the root `build.gradle.kts`:

```kotlin
plugins {
    id("io.ia.sdk.modl") version("0.3.0")
}
```

### Plugin Features

1. **Tasks**:

   - `build`: Builds the module
   - `signModule`: Signs the module with a certificate
   - `deployModl`: Deploys the module to a gateway

2. **Configurations**:

   - `ignitionModule`: Main module configuration
   - `modlImplementation`: Dependencies to include in the module

3. **Properties**:
   - Module metadata (name, version, ID)
   - Scope definitions
   - Resource mounting

## Build Configuration Breakdown

### Module Configuration in build.gradle.kts

```kotlin
ignitionModule {
    // Basic module information
    name.set("Example Component Library")
    fileName.set("Example-Component-Library.modl")
    id.set("dev.kgamble.perspective.examples.ExampleComponentLibrary")
    moduleVersion.set("${project.version}")

    // Module requirements
    requiredIgnitionVersion.set("8.1.44")

    // Scope definitions
    projectScopes.putAll(
        mapOf(
            ":gateway" to "G",
            ":web" to "G",
            ":designer" to "D",
            ":common" to "GD"
        )
    )

    // Dependencies and hooks
    moduleDependencies.put("com.inductiveautomation.perspective", "GD")
    hooks.putAll(
        mapOf(
            "dev.kgamble.perspective.examples.gateway.ExampleComponentLibraryGatewayHook" to "G",
            "dev.kgamble.perspective.examples.designer.ExampleComponentLibraryDesignerHook" to "D"
        )
    )
}
```

### Understanding Scopes

The `projectScopes` configuration defines where each subproject's code runs:

- `G`: Gateway scope
- `D`: Designer scope
- `GD`: Both Gateway and Designer scopes

## Module Properties

### gradle.properties

```properties
hostGateway=http://localhost:8088
signModule=false
```

### Version Catalog (gradle/libs.versions.toml)

```toml
[versions]
ignition = "8.1.44"

[libraries]
ignition-common = { module = "com.inductiveautomation.ignitionsdk:ignition-common", version.ref = "ignition" }
```

## Project Structure

The project structure is defined in `settings.gradle.kts`:

```kotlin
rootProject.name = "example-component-library"

include(":common", ":gateway", ":designer", ":web")
```

### Subproject Build Files

#### common/build.gradle.kts

```kotlin
plugins {
    `java-library`
}

dependencies {
    compileOnly(libs.ignition.common)
    compileOnly(libs.ignition.perspective.common)
}
```

#### web/build.gradle.kts

```kotlin
plugins {
    java
    id("com.github.node-gradle.node")
}

node {
    version.set("22.6.0")
    npmVersion.set("10.8.2")
    download.set(true)
}
```
