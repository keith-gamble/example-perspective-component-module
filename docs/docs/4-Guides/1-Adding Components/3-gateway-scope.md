---
title: Gateway Scope
description: Create the gateway scope for a Perspective component
---

## Gateway Scope Implementation

:::tip 📝
The gateway scope handles server-side component registration and resource management. This is where your component becomes available to the Perspective module and where its resources are made accessible to clients.
:::

### 1. Update Gateway Hook

Register your checkbox component in the gateway hook:

```java title="gateway/src/main/java/dev/kgamble/perspective/examples/gateway/ExampleComponentLibraryGatewayHook.java"
import dev.kgamble.perspective.examples.common.components.input.Checkbox;

public class ExampleComponentLibraryGatewayHook extends AbstractGatewayModuleHook {
    // ... other DesignerHook methods ...
    @Override
    public void startup(LicenseState activationState) {
        // ... other startup code ...

        if (this.componentRegistry != null) {
            // ... other startup code ...
            // Register the Checkbox component
            this.componentRegistry.registerComponent(Checkbox.DESCRIPTOR);
        } else {
            log.error("Component registry not found, Checkbox component will not function!");
        }
    }

    @Override
    public void shutdown() {
        if (this.componentRegistry != null) {
            // ... other shutdown code ...
            // Remove the Checkbox component during shutdown
            this.componentRegistry.removeComponent(Checkbox.COMPONENT_ID);
        }
    }

    // ... remaining GatewayHook methods ...
}
```

The gateway hook handles:

- Component registration with the Perspective system
- Resource mounting for web assets
- Proper cleanup during shutdown

### 2. Understanding Resource Mounting

The gateway hook includes two important methods for resource handling:

1. `getMountedResourceFolder()`:

   - Returns "mounted" to indicate where web resources are located
   - Resources in this folder will be available at `/res/${module-id}/`

2. `getMountPathAlias()`:
   - Provides an alias for the module's resource path
   - Uses the constant defined in `Constants.java`

:::info Resource Path
Your component's web resources (JS/CSS) will be available at:

```
http://perspective-component.localtest.me/res/example-components/ExampleComponents.js
http://perspective-component.localtest.me/res/example-components/ExampleComponents.css
```

These paths are defined in `ExampleComponents.java` in the common scope.
:::

## Gateway Scope Summary

The gateway scope implementation:

1. Registers the component with the Perspective system
2. Manages web resource accessibility
3. Handles proper component lifecycle

These elements ensure your component is:

- Available to Perspective sessions
- Properly resourced with necessary files
- Cleanly managed during system changes

Next, we'll implement the Web scope to create the actual React component and its associated TypeScript definitions.
