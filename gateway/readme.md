# Gateway Scope - Example Component Library

This directory contains the gateway-specific code for the Example Component Library. The gateway scope is responsible for registering components with the Perspective system and handling any server-side logic related to these components.

## Key Files

- `ExampleComponentLibraryGatewayHook.java`: The main entry point for the gateway module.

## ExampleComponentLibraryGatewayHook

This class extends `AbstractGatewayModuleHook`, the hook provided by the Ignition SDK that acts as the entrypoint for our Java code, and is responsible for the lifecycle management of our module in the gateway scope.

### Key Methods

1. `setup(GatewayContext context)`: 
   - Called before startup.
   - Used to add extension points and update persistent records and schemas.
   - We store the `GatewayContext` for later use.

2. `startup(LicenseState activationState)`:
   - Called to initialize the module.
   - We acquire the `PerspectiveContext` and `ComponentRegistry`.
   - We register our components (in this case, the Button component) with the registry.

3. `shutdown()`:
   - Called when the module is being shut down.
   - We unregister our components from the registry.

4. `getMountedResourceFolder()`:
   - Specifies the folder in the module's gateway jar files that should be mounted at `/res/${module-id}/foldername`.
   - We return "mounted" to make our resources available.
   - What this allows is for us to be able to access our resources (JavaScript and CSS files) from the frontend via a URL like `/res/example-component-library/js/button.js`.

5. `getMountPathAlias()`:
   - Provides an alternate mounting path instead of the module ID.
   - We use a constant `MODULE_URL_ALIAS` for consistency.

6. `isFreeModule()`:
   - Indicates whether this is a "free" module (not participating in the licensing system).
   - We return `true` as this is a free module.

## Component Registration

In the `startup` method, we register our Button component:

```java
this.componentRegistry.registerComponent(Button.DESCRIPTOR);
```

This makes the Button component available for use in Perspective.

## Resource Mounting

By implementing `getMountedResourceFolder()` and `getMountPathAlias()`, we ensure that our web resources (JavaScript and CSS files) are properly mounted and accessible to the Perspective frontend.

## Error Handling

We include error logging in case the component registry is not available, which would prevent our components from functioning correctly.

## Shutdown Process

In the `shutdown` method, we properly unregister our components to ensure clean module removal:

```java
this.componentRegistry.removeComponent(Button.COMPONENT_ID);
```

## Best Practices

1. We use a logger (`LoggerEx`) for proper error and info logging.
2. We check for null references before using them to avoid null pointer exceptions.
3. We use constants for component IDs and module aliases to maintain consistency across the module.

This gateway hook ensures that our custom components are properly registered with the Perspective module, making them available for use in Perspective projects. It also handles the proper mounting of resources and cleanup during shutdown.