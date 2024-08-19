# Web Scope - Example Component Library

This directory contains the frontend code for the Example Component Library. It includes the React components, build configuration, and other web-related files necessary for creating custom Perspective components.

## Key Files and Directories

- `src/components/Button.tsx`: The React implementation of our Button component.
- `src/css/styles.css`: CSS styles for our components.
- `src/index.ts`: The entry point for our web bundle.
- `webpack.config.js`: Webpack configuration for building our web resources.
- `package.json`: npm package configuration and dependencies.
- `tsconfig.json`: TypeScript configuration.

## React Components

### Button Component (`src/components/Button.tsx`)

This file contains the React implementation of our Button component:

- It defines the `ButtonProps` interface for type-checking the component's properties. This is essentially a mock of the Perspective component properties.
- The `Button` class extends `Component<ComponentProps<ButtonProps>, any>` to create a Perspective-compatible component.
- It implements the `render` method to define the component's appearance. This updates whenever the component's properties change and it re-renders in the browser.
- It includes an `onActionPerformed` method to handle button clicks.

### Component Meta

The `ButtonMeta` class implements `ComponentMeta`, defining metadata for the Button component:

- `getComponentType()`: Returns the unique identifier for this component type.
- `getViewComponent()`: Returns the React component class.
- `getDefaultSize()`: Specifies the default size of the component.
- `getPropsReducer()`: Defines how to map the component's properties from the Perspective property tree to React props.

## Styles (`src/css/styles.css`)

This file contains CSS styles for our components. It defines the appearance of our Button component, including hover and disabled states.

## Entry Point (`src/index.ts`)

This file serves as the entry point for our web bundle:

- It imports and exports our React components.
- It imports our CSS styles.
- It registers our components with the Perspective `ComponentRegistry`.

## Build Configuration

### Webpack (`webpack.config.js`)

Our Webpack configuration:

- Defines the entry point and output for our bundle.
- Sets up TypeScript compilation.
- Configures CSS extraction.
- Defines external dependencies (React, ReactDOM, etc.).
- Includes a custom plugin to copy built files to the Gradle resources directory.

### TypeScript (`tsconfig.json`)

TypeScript configuration for our project, including compiler options and file inclusion/exclusion rules.

### npm (`package.json`)

Defines our project's npm dependencies and scripts:

- `clean`: Removes build artifacts.
- `build`: Cleans and then builds the project using Webpack.

## Building the Web Components

To build the web components:

1. Ensure you have Node.js and npm installed.
2. Run `npm install` to install dependencies.
3. Run `npm run build` to build the components.

This will create `ExampleComponents.js` and `ExampleComponents.css` in the `dist` directory, which are then copied to the Gradle resources directory for inclusion in the module.

## Integration with Java Module

The built JavaScript and CSS files are included in the module's resources, making them available to the Perspective runtime. The Java side of the module (in the Gateway scope) is responsible for registering these resources so that they're loaded when needed in Perspective sessions.

## Best Practices

1. Use of TypeScript for type safety and better developer experience.
2. Separation of concerns: React component, styles, and build configuration are kept separate.
3. Following Perspective's component structure for seamless integration.
4. Use of Webpack for efficient bundling and resource management.

This web scope is crucial for creating the actual frontend implementation of our custom components. It bridges the gap between the Java-based module structure and the React-based Perspective frontend, allowing us to create rich, interactive components for use in Perspective projects.