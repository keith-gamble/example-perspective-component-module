# Environment Setup Guide

This guide will walk you through setting up a development environment for Java and Node.js. We'll be using the following tools:

- VS Code
- SDKMAN! (for Java version management)
- Gradle
- NVM (Node Version Manager)
- Node.js
- Docker and Docker Compose

## Steps

### 1. Install SDKMAN! and Java

1. In your terminal, install SDKMAN! by running:
   ```
   curl -s "https://get.sdkman.io" | bash
   ```
2. Restart your terminal or run:
   ```
   source "$HOME/.sdkman/bin/sdkman-init.sh"
   ```
3. Install Java using SDKMAN!:
   ```
   sdk install java 17.0.11-zulu
   ```

### 2. Install Gradle

With SDKMAN! installed, you can easily install Gradle:

```
sdk install gradle 7.6
```

### 3. Install NVM and Node.js

1. Install NVM by running:
   ```
   curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.3/install.sh | bash
   ```
2. Restart your terminal or run:
   ```
   source ~/.bashrc
   ```
3. Install Node.js using NVM:
   ```
   nvm install --lts
   ```

### 4. Configure VS Code

1. Open VS Code.
2. Install the following VS Code extensions:
   - [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
   - [Gradle for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-gradle)

### 5. Setup Gradle Properties

1. Copy the `gradle.properties.template` file to `gradle.properties` in the root of the project.
2. Update the `gradle.properties` file with your own values.
3. If you do not have the signing files, you can set `signModule=false`.

### 6. Automated Module Deployment

1. Start the docker compose project included in the repository
2. Set your `hostGateway` in your `gradle.properties` file to match the address you set for your docker compose project
3. Run the following command to deploy the module to the local environment
   ```
   ./gradlew build deployModl
   ```
4. The module will be installed, but not accepted by default. You must go to the modules configuration page in the Gateway webpage and accept the license.
5. After accepting the license, take a new backup and replace the one in `docker/backups` so that its auto-accepted in the future.

## Verification

To verify your setup:

1. Open a new WSL terminal and run:
   ```
   java -version
   gradle -version
   node -v
   npm -v
   docker --version
   docker-compose --version
   ```
2. All commands should return version information without errors.
3. Build the module to test the Gradle setup:
   ```
   ./gradlew clean build
   ```

## Next Steps

Now that your environment is set up, you're ready to start developing! Check out our [Getting Started Guide](getting-started.md) for next steps on how to begin working with our project.

If you encounter any issues during setup, please refer to the official documentation for each tool or reach out to our support team.