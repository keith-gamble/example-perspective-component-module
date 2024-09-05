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
   sdk install java 11.0.24-zulu
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

### 7. Unsecure Designer Login

It is possible to auto-login to the designer for testing purposes, however it is noted that this is not secure and should not be used in a production environment. To enable this, add the following JVM args to your designer launcher config for this gateway:

```sh
-Dautologin.username=some_username;
-Dautologin.password=some_password;
-Djavaws.ignition.loglevel=INFO;
-Djavaws.ignition.debug=true;
-Dproject.name=some_project_name;
```

### 8. Mapping Files into the Gateway for Development

It is possible to map your built js files into the gateway, so that you dont need to keep rebuilding the module every time you make a change. To do this, the following mounts, and JVM args have been added to the `docker/docker-compose.yml` file:

#### `docker/docker-compose.yml`
```yml
	volumes:
	  - ../web:/web-resources
	# ... other volumes

	commands: 
	# ... other commands
	--
	-Dres.path.dev.bwdesigngroup.perspective.examples.ExampleComponentLibrary=/web-resources/build/generated-resources/mounted
```

The reason that this is mounted at the `./web` level, instead of all the way down to the mounted content, is because when the webpack copy happens if you do a `clean` first it will delete the `build` directory inside of `./web` which will throw off permissions on the finally created content. This just makes it easier to ensure that you have consistent permissions the whole time.

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