# Detailed Guide: Building the Example Component Library Module

This guide provides an in-depth explanation of how the Example Component Library module is built using Gradle, the Ignition plugin, NPM, and how the various project components are integrated.

## Overview of the Build Process

The build process for the Example Component Library module involves several steps:

1. Compiling Java code for the common, gateway, and designer projects
2. Building web resources (TypeScript/React components)
3. Packaging everything into a `.modl` file

Let's dive into each of these steps and the tools involved.

## Gradle and the Ignition Plugin

### Gradle

Gradle is the build automation tool used in this project. It manages dependencies, compiles code, runs tests, and packages the module.

The main Gradle files in the project are:

- `build.gradle`: The root project build file, this defines the Ignition module details that will be created, and the included subprojects
- `settings.gradle`: Defines the project structure and links to the IA module building plugin
- `gradle.properties`: Contains project-wide properties
- `common/build.gradle`, `designer/build.gradle`, `gateway/build.gradle`, `web/build.gradle`: Subproject-specific build files

The repository comes with a `gradle.properties.template` that can be used to create a `gradle.properties` file with the necessary properties for the project. Helpful comments are included to explain the purpose of each property.

### Ignition Module Plugin

The Ignition Module Plugin is a custom Gradle plugin that provides tasks specific to building Ignition modules. It's applied in the root `build.gradle` file:

```groovy
plugins {
    id("io.ia.sdk.modl") version "0.3.0"
}
```

This plugin provides tasks like `ignitionModule` and `deployModl`. This is what will package the module into a `.modl` file and allow us to auto-deploy it to an Ignition Gateway.

## Project Structure and Dependencies

The project is structured into several subprojects, which are not specifically named and can be customized to fit your needs. A common structure is:

- `common`: Shared code for both Gateway and Designer
  - This is optional for code organization, but can be useful for sharing code between the different module scopes.
- `designer`: Designer-specific code
  - The SDK actually cares about this scope, and is treated as the "code that runs in the designer"
- `gateway`: Gateway-specific code
  - The SDK actually cares about this scope, and is treated as the "code that runs in the gateway"
- `web`: Frontend React components and build configuration
  - This is optional for code organization, but is useful for keeping frontend code separate from backend code, since it is built with different tools.

### Subproject Dependencies

The dependencies between subprojects are defined in their respective `build.gradle` files. For example, in `gateway/build.gradle`:

```groovy
dependencies {
	// This allows the code built in the :common project to be used in the :gateway project
    api(project(":common"))
	
	// This is required to get the :web project .jar added into the modl file
    modlImplementation(project(":web"))

    // Other dependencies...
}
```

This tells Gradle that the `gateway` project depends on the `common` project, and should include the files from the `web` project in the final module.

## Web Resource Compilation

The `web` project contains the TypeScript/React components. These need to be compiled and included in the final module.

### Webpack Configuration

The `web` project uses Webpack for building the frontend resources. The configuration is in `web/webpack.config.js`. Key points in this file:

```javascript
output: {
    path: path.join(__dirname, "dist"),
    filename: `${LibName}.js`,
},
```

This specifies that the compiled JavaScript should be output to the `dist` directory.

### Gradle Integration

The `web/build.gradle` file includes tasks to run npm and Webpack:

```groovy
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
```

These tasks are made dependencies of the `:gateway` project's `build` task through the `processResources` task:

```groovy
project(':gateway')?.tasks?.named('processResources')?.configure {
    dependsOn webpack
}
```

### Resource Copying

After the web resources are built, they need to be copied to a location where the `:gateway` project can find them. This is handled by setting out `output` directory in the `webpack` task.

```groovy

task webpack(type: NpmTask) {
    // Build the files here


    outputs.files(fileTree(projectOutput))
}
```

This task copies the compiled web resources to the `gateway` project's resources directory.

## Building the Module

When you run `./gradlew build`, Gradle performs these steps:

1. Compiles Java code for `common`, `designer`, and `gateway` projects
2. Builds web resources (running npm and Webpack)
3. Copies web resources to the `gateway` project
4. Packages everything into a `.modl` file using the Ignition Module Plugin

The resulting `.modl` file will be in the root `build/` directory.

## Deploying the Module

The `deployModule` task, provided by the Ignition Module Plugin, can deploy the built module to a running Ignition Gateway:

```
./gradlew build deployModule
```

This task uses the `gateway` properties defined in `gradle.properties` to connect to and deploy the module to the specified Ignition Gateway.

## Best Practices and Tips

1. Always run a clean build when making significant changes: `./gradlew clean build`
2. Use the `--info` or `--debug` flag with Gradle commands for more detailed output if you're troubleshooting build issues
3. Keep your `gradle.properties` file up to date, especially when working with different Ignition versions or Gateway URLs
4. Regularly update the Ignition SDK and Module Plugin versions to stay current with the latest features and bug fixes

## Troubleshooting Common Issues

- If the module fails to load in Ignition, check the Gateway logs for detailed error messages
- For web component issues, check the browser console and the compiled JavaScript in the `.modl` file

Remember, the build process integrates multiple technologies and steps. Understanding each part of this process will help you troubleshoot issues and extend the module effectively.