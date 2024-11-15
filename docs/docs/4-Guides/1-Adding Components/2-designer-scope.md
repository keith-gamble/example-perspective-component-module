---
title: Designer Scope
description: Create the designer scope for a Perspective component
---

## Designer Scope Implementation

:::tip 📝
The designer scope handles how your component appears and behaves within the Ignition Designer environment. This includes component icons, registration, and any designer-specific behaviors.

In a simple component, this is likely just showing the component in the palette and associating it with an icon. More complex components may require additional designer-specific behavior.
:::

### 1. Create Component Icon

First, create an SVG icon for your checkbox component:

```svg title="designer/src/main/resources/images/checkbox-icon.svg"
<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
    stroke="#3D5D6F" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
    class="lucide lucide-copy-check">
    <path d="m12 15 2 2 4-4" />
    <rect stroke="#B9C4CB" width="14" height="14" x="8" y="8" rx="2" ry="2" />
    <path stroke="#B9C4CB" d="M4 16c-1.1 0-2-.9-2-2V4c0-1.1.9-2 2-2h10c1.1 0 2 .9 2 2" />
</svg>
```

This icon will be displayed in the component palette and represents your checkbox visually in the designer.

### 2. Update Designer Hook

Modify the designer hook `init` and `shutdown` methods to register your new checkbox component:

```java title="designer/src/main/java/dev/kgamble/perspective/examples/designer/ExampleComponentLibraryDesignerHook.java"
// Add the checkbox import after the other imports
import dev.kgamble.perspective.examples.common.components.input.Checkbox;

public class ExampleComponentLibraryDesignerHook extends AbstractDesignerModuleHook {
    // ... other DesignerHook methods ...

    private void init() {
        // ... other init code ...
        // Register the Checkbox component with its icon
        ComponentUtilities.registerComponentWithIcon(
            registry,
            Checkbox.DESCRIPTOR,
            "/images/checkbox-icon.svg"
        );
    }

    @Override
    public void shutdown() {
        // Remove the component during shutdown
        registry.removeComponent(Checkbox.COMPONENT_ID);
    }
}
```

This hook handles:

- Component registration with the designer
- Association of the component with its icon
- Proper cleanup during shutdown

### 3. Component Registration Utilities

The designer scope uses utility classes to handle component registration and icon loading. These are already provided in the example project, but here's how they work with our checkbox:

- `ComponentUtilities.registerComponentWithIcon` handles:
  1. Loading the SVG icon
  2. Creating a delegating descriptor that provides the icon
  3. Registering the component with the designer
- `IconUtilities.getSvgIcon` handles:
  1. Loading the SVG file from resources
  2. Converting it to a Swing Icon
  3. Sizing it appropriately for the designer

## Designer Scope Summary

The designer scope implementation:

1. Provides a visual representation (icon) for the component
2. Registers the component with the designer
3. Ensures proper cleanup on shutdown

These elements work together to make your component available and usable within the Ignition Designer interface.

Next, we'll implement the Gateway scope to handle server-side registration and resource management.
