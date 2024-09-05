# Example Component Library for Ignition Perspective

This project demonstrates how to create custom components for Ignition Perspective, Inductive Automation's web-based application platform. It includes a sample Button component and showcases the structure required for developing, building, and integrating custom components into Ignition Perspective.

## Project Structure

The project is organized into several key directories:

- `common`: Shared code used by both the Gateway and Designer
- `designer`: Designer-specific code
- `gateway`: Gateway-specific code
- `web`: Frontend React components and build configuration
- `docker`: Contains Docker-related files for development and testing

### Key Files

- `build.gradle.kts`: Main Gradle build file
- `settings.gradle.kts`: Gradle settings file
- `common/build.gradle.kts`, `designer/build.gradle.kts`, `gateway/build.gradle.kts`, `web/build.gradle.kts`: Subproject-specific build files
- `web/package.json`: npm dependencies and scripts for the web components
- `web/webpack.config.js`: Webpack configuration for building the web components
- `web/tsconfig.json`: TypeScript configuration

## Components

Currently, this project includes one example component:

- `Button`: A customizable button component (`web/src/components/Button.tsx`)

## Tools and Technologies

- Java 11
- Gradle for building the module
- npm and Webpack for managing and building web components
- TypeScript for type-safe JavaScript development
- React for building user interface components

## Building the Project

1. Ensure you have Java 11, Gradle, and Node.js installed.

2. Clone the repository:
   ```
   git clone https://github.com/design-group/example-perspective-component-module.git
   cd example-perspective-component-module
   ```

3. Build the web components to confirm you have the necessary tools and dependencies:
   ```
   cd web
   npm install
   npm run build
   ```

4. Build the entire module:
   ```
   cd ..
   ./gradlew build
   ```

This will produce a `.modl` file in the `build/` directory, which can be installed in Ignition.

## Development Workflow

1. Create your `gradle.properties` file based off the template provided.
   1. If you dont have the necessary tools to sign the module, then set `signModule=false`.
2. Make changes to the Java code in `common`, `designer`, or `gateway` directories as needed.
3. Develop React components in the `web/src/components` directory.
4. Use `npm run watch` in the `web` directory to keep your changes up-to-date.
5. With the Designer External Debugger open press `CMD + R` (`CTRL + R` on Windows) to refresh the designer and see your changes. 
6. If you change any Java code, you will need to run `./gradlew build` to compile the Java code and redeploy the module to see the changes.
7. Run `./gradlew build` in the root directory to build the entire module.
8. Install the resulting `.modl` file in your Ignition gateway for testing.
9. If you want to auto-deploy the module to your local gateway, set the `hostGateway` property in your `gradle.properties` file and run `./gradlew build deployModl`.

## Docker Development Environment

A Docker environment is provided for development and testing. To use it:

1. Navigate to the `docker` directory.
2. Run `docker-compose up` to start the Ignition gateway.
3. Set the `hostGateway` value in `gradle.properties` to the address of the Docker container (e.g., `hostGateway=http://localhost:8088`).
4. Access the Ignition gateway at `http://localhost:8088`.
5. After the first time you build the module, you'll need to go accept the license certificate in the gateway.
6. After the license is accepted, download a new backup to replace the existing one in the `docker/backups` directory.
7. Now run `./gradlew build deployModl` to build and deploy the module to the gateway automatically.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request with your changes.

## License

This project is licensed under the MIT License - see the LICENSE.txt file for details.
