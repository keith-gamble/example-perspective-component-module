# Glossary of Terms

This glossary defines key terms and concepts used in Ignition module development, specifically for Perspective modules. Each term includes explanations, examples, and references for additional information.

---

## Table of Contents

- [Ignition Platform](#ignition-platform)
- [Module](#module)
- [Perspective Module](#perspective-module)
- [Gateway](#gateway)
- [Designer](#designer)
- [Client](#client)
- [Hook](#hook)
- [Scope](#scope)
- [Component](#component)
- [Component Meta](#component-meta)
- [Props](#props)
- [Event](#event)
- [Gradle](#gradle)
- [Gradle Kotlin DSL](#gradle-kotlin-dsl)
- [Dependency Management](#dependency-management)
- [Ignition SDK](#ignition-sdk)
- [MODL File](#modl-file)
- [Resource Mounting](#resource-mounting)
- [Browser Resource](#browser-resource)
- [Webpack](#webpack)
- [TypeScript](#typescript)
- [React](#react)
- [Node.js](#nodejs)
- [NPM](#npm)
- [Docker](#docker)
- [Version Catalog](#version-catalog)
- [Kotlin](#kotlin)
- [Java](#java)

---

## Ignition Platform

**Definition:** A unified industrial application platform by Inductive Automation used for building solutions in HMI, SCADA, MES, and IIoT.

**Example:** Ignition allows developers to create dashboards for monitoring industrial processes.

**Reference:** [Inductive Automation - What is Ignition?](https://inductiveautomation.com/what-is-ignition)

---

## Module

**Definition:** A package that extends the functionality of the Ignition platform. Modules can add new features, components, and services.

**Example:** The Perspective Module adds web and mobile visualization capabilities to Ignition.

**Reference:** [Inductive Automation - Modules](https://inductiveautomation.com/ignition/modules)

---

## Perspective Module

**Definition:** An Ignition module that enables the creation of HTML5-compliant applications for web and mobile devices using React components.

**Example:** Developing a custom chart component for use in Ignition Perspective projects.

**Reference:** [Perspective Module Overview](https://docs.inductiveautomation.com/display/DOC81/Perspective+Module)

---

## Gateway

**Definition:** The central server application in Ignition that manages projects, modules, and connections to devices and databases.

**Example:** The Ignition Gateway runs on a server and hosts all client sessions and designer instances.

**Reference:** [Ignition Gateway Introduction](https://docs.inductiveautomation.com/display/DOC81/Ignition+Gateway)

---

## Designer

**Definition:** An application used to design and configure projects within Ignition, including windows, scripts, and components.

**Example:** Using the Ignition Designer to lay out a user interface for a SCADA application.

**Reference:** [Ignition Designer Overview](https://docs.inductiveautomation.com/display/DOC81/Ignition+Designer)

---

## Client

**Definition:** An instance of a project running on a user's device, such as a web browser or mobile app in the context of Perspective.

**Example:** A plant manager views live production data through a Perspective client in their web browser.

**Reference:** [Ignition Clients Explained](https://docs.inductiveautomation.com/display/DOC81/Clients)

---

## Hook

**Definition:** A Java class that initializes and manages module functionality within a specific scope (Gateway or Designer).

**Example:** The `ExampleComponentLibraryGatewayHook` class initializes resources when the module is loaded on the Gateway.

**Reference:** [Module Hook Classes](https://docs.inductiveautomation.com/display/SDK/Module+Hook+Classes)

---

## Scope

**Definition:** The context in which code or resources are executed: Gateway (`G`), Designer (`D`), or Client (`C`).

**Example:** A component's rendering code runs in the Client scope, while its business logic may run in the Gateway scope.

**Reference:** [Understanding Scopes](https://docs.inductiveautomation.com/display/SDK/Scope)

---

## Component

**Definition:** A reusable UI element in Ignition Perspective, typically built using React and TypeScript.

**Example:** A `MyWidget` component that displays custom data visualization.

**Reference:** [Developing Perspective Components](https://docs.inductiveautomation.com/display/SDK/Component+Development)

---

## Component Meta

**Definition:** An interface or class that provides metadata about a Perspective component, such as its type and default properties.

**Example:**

```typescript
export class MyWidgetMeta implements ComponentMeta {
  getComponentType(): string {
    return "examples.display.mywidget";
  }
}
```

**Reference:** [Component Meta Interface](https://sdkdocs.inductiveautomation.com/ComponentMeta)

---

## Props

**Definition:** Properties that define a component's configuration, appearance, and behavior.

**Example:** A `Button` component might have props like `label`, `onClick`, and `style`.

**Reference:** [Perspective Component Props](https://docs.inductiveautomation.com/display/DOC81/Component+Properties)

---

## Event

**Definition:** An action or occurrence recognized by software, often used to trigger functions or scripts.

**Example:** Handling a `onClick` event when a user clicks a button component.

**Reference:** [Event Handling in Perspective](https://docs.inductiveautomation.com/display/DOC81/Event+Types)

---

## Gradle

**Definition:** A build automation tool used for compiling code, managing dependencies, and packaging applications.

**Example:** Using Gradle to build an Ignition module into a `.modl` file.

**Reference:** [Gradle Official Site](https://gradle.org/)

---

## Gradle Kotlin DSL

**Definition:** A way to write Gradle build scripts using Kotlin instead of Groovy, offering improved syntax and IDE support.

**Example:** Writing `build.gradle.kts` files with type-safe code completion in IntelliJ IDEA.

**Reference:** [Gradle Kotlin DSL Documentation](https://docs.gradle.org/current/userguide/kotlin_dsl.html)

---

## Dependency Management

**Definition:** The process of specifying, resolving, and retrieving the libraries and modules that a project depends on.

**Example:** Declaring a dependency on `ignition-common` in the `build.gradle.kts` file.

**Reference:** [Managing Dependencies with Gradle](https://docs.gradle.org/current/userguide/dependency_management.html)

---

## Ignition SDK

**Definition:** A collection of tools, libraries, and documentation provided by Inductive Automation for developing Ignition modules.

**Example:** Using the SDK to access Ignition APIs for module development.

**Reference:** [Ignition SDK Documentation](https://sdkdocs.inductiveautomation.com/)

---

## MODL File

**Definition:** The file format (`.modl`) used by Ignition to package and distribute modules.

**Example:** Installing `Example-Component-Library.modl` into the Ignition Gateway to add new components.

**Reference:** [Module File Format](https://docs.inductiveautomation.com/display/SDK/Building+Modules)

---

## Resource Mounting

**Definition:** The process of making resources like JavaScript and CSS files available to the web browser from the Gateway.

**Example:** Serving a custom `ExampleComponents.js` file to clients for rendering components.

**Reference:** [Resource Mounting in Modules](https://docs.inductiveautomation.com/display/SDK/Resource+Mounting)

---

## Browser Resource

**Definition:** A resource (e.g., JS, CSS) that is served to the client's browser for use in Perspective sessions.

**Example:**

```java
new BrowserResource(
  "example-components-js",
  "/res/example-components/ExampleComponents.js",
  BrowserResource.ResourceType.JS
);
```

**Reference:** [BrowserResource Class](https://sdkdocs.inductiveautomation.com/BrowserResource)

---

## Webpack

**Definition:** A module bundler for JavaScript applications, used to compile and bundle frontend assets.

**Example:** Using Webpack to bundle React components and dependencies into a single JavaScript file.

**Reference:** [Webpack Official Site](https://webpack.js.org/)

---

## TypeScript

**Definition:** A strongly typed programming language that builds on JavaScript, adding static type definitions.

**Example:** Writing component code in `MyWidget.tsx` with type safety and IntelliSense support.

**Reference:** [TypeScript Official Site](https://www.typescriptlang.org/)

---

## React

**Definition:** A JavaScript library for building user interfaces, particularly single-page applications.

**Example:** Creating a custom component by extending `React.Component` in TypeScript.

**Reference:** [React Official Site](https://reactjs.org/)

---

## Node.js

**Definition:** A JavaScript runtime built on Chrome's V8 engine, allowing JavaScript to be run server-side.

**Example:** Running Webpack and other build tools in the development environment.

**Reference:** [Node.js Official Site](https://nodejs.org/)

---

## NPM

**Definition:** Node Package Manager; a package manager for JavaScript that allows developers to share and reuse code.

**Example:** Installing dependencies with `npm install` in the `web` directory.

**Reference:** [NPM Official Site](https://www.npmjs.com/)

---

## Docker

**Definition:** A platform that uses OS-level virtualization to deliver software in packages called containers.

**Example:** Running an Ignition Gateway in a Docker container for consistent development environments.

**Reference:** [Docker Official Site](https://www.docker.com/)

---

## Version Catalog

**Definition:** A Gradle feature that centralizes dependency versions in a `libs.versions.toml` file.

**Example:**

```toml
[versions]
ignition = "8.1.44"
```

**Reference:** [Gradle Version Catalogs](https://docs.gradle.org/current/userguide/platforms.html)

---

## Kotlin

**Definition:** A statically typed programming language that runs on the JVM and is interoperable with Java.

**Example:** Writing build scripts in Kotlin for improved syntax and tooling support.

**Reference:** [Kotlin Official Site](https://kotlinlang.org/)

---

## Java

**Definition:** A high-level, class-based, object-oriented programming language widely used for building applications.

**Example:** Writing module hooks and backend logic in Java for the Ignition module.

**Reference:** [Java Official Site](https://www.oracle.com/java/)

---

By familiarizing yourself with these terms and concepts, you'll have a solid foundation for developing Ignition modules and extending the functionality of the Ignition platform.
