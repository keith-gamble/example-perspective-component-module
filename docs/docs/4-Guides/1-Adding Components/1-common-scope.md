---
title: Common Scope
description: Create the common scope for a Perspective component
---

## Common Scope Implementation

<!-- Explain that file contents underneath the `examples/common` directory can be changed and modified, and that we just picked a structure that works well for us. -->

:::tip 📝
The common scope establishes the core structure of our Checkbox component, defining its properties, events, and component descriptor. Because we are building the package `dev.kgamble.perspective.examples`, we'll place our files in the `common` directory under `src/main/java` and `src/main/resources`.

Anything below the `common` directory can be changed and modified to suit your needs. We've chosen a structure that works well for us, but you can adjust it as needed.
:::

### 1. Create Component Class

First, create your component class `Checkbox.java`

```java title="common/src/main/java/dev/kgamble/perspective/examples/common/components/input/Checkbox.java"
package dev.kgamble.perspective.examples.common.components.input;

import java.util.List;
import com.inductiveautomation.perspective.common.api.ComponentDescriptor;
import com.inductiveautomation.perspective.common.api.ComponentDescriptorImpl;
import dev.kgamble.perspective.examples.common.Constants;
import dev.kgamble.perspective.examples.common.ExampleComponents;
import dev.kgamble.perspective.examples.common.utilities.ComponentUtilities;

public class Checkbox {
    // Unique identifier for the component
    public static final String COMPONENT_ID = "examples.input.checkbox";

    // Component name displayed in the palette
    private static final String COMPONENT_NAME = "Example Checkbox";
    // Description of the component that is displayed in the palette as a tooltip
    private static final String COMPONENT_DESCRIPTION =
        "A customizable checkbox input component";
    // Default name for the component instance in a view
    private static final String COMPONENT_DEFAULT_NAME = "checkbox";

    // Create the component descriptor
    public static final ComponentDescriptor DESCRIPTOR = ComponentDescriptorImpl
        .ComponentBuilder.newBuilder()
        .setPaletteCategory(ExampleComponents.COMPONENT_CATEGORY)
        .setId(COMPONENT_ID)
        .setModuleId(Constants.MODULE_ID)
        .setSchema(ComponentUtilities.getSchemaFromFilePath(
            "/props/example-checkbox.props.json"))
        .addPaletteEntry(
            "", // Variant used for the palette entry, empty string is the default variant
            COMPONENT_NAME,
            COMPONENT_DESCRIPTION,
            null, // Thumbnail image path, located in the resources directory
            null)
        .setName(COMPONENT_NAME)
        .setDefaultMetaName(COMPONENT_DEFAULT_NAME)
        .setResources(ExampleComponents.BROWSER_RESOURCES) // Connect the component to the javascript and css resources defined in ExampleComponents.java
        .build();
}
```

This class defines our Checkbox component's core structure. Let's break down the key elements:

- `COMPONENT_ID`: Unique identifier using our naming convention (`examples.input.checkbox`)
- `COMPONENT_NAME`: Display name in the component palette
- `ComponentDescriptor`: Registers the component with Perspective, defining its:
  - Category in the palette
  - Component ID
  - Module association
  - Property schema
  - Display information
  - Resource requirements

### 2. Create Property Schema

Create the property schema file that will represent your components Perspective properties:

```json title="common/src/main/resources/props/example-checkbox.props.json"
{
  "type": "object",
  "properties": {
    "value": {
      "type": "boolean",
      "description": "Current checked state of the checkbox",
      "default": false
    },
    "disabled": {
      "type": "boolean",
      "description": "Whether the checkbox is interactive",
      "default": false
    },
    "label": {
      "type": ["string", "number"],
      "description": "Text label displayed next to checkbox",
      "default": ""
    },
    "style": {
      "$ref": "urn:ignition-schema:schemas/style-properties.schema.json",
      "description": "Custom styling properties"
    }
  }
}
```

This schema defines our component's configurable properties:

- `value`: Boolean state of the checkbox
- `disabled`: Whether the checkbox can be interacted with
- `label`: Text displayed beside the checkbox
- `style`: Standard Perspective styling properties

### 3. Create Event Schema

Create the event schema file that will represent your components Perspective custom events:

```json title="common/src/main/resources/events/example-checkbox/onChange.json"
{
  "name": "onChange",
  "description": "Triggered when the checkbox state changes",
  "schema": {
    "type": "object",
    "properties": {
      "value": {
        "type": "boolean",
        "description": "The new checked state of the checkbox"
      }
    }
  }
}
```

This defines our checkbox's change event structure, specifying:

- Event name and description
- Parameters passed with the event (the new checked state)

### 4. Register Event Descriptor

Update the `Checkbox.java` class to include the event descriptor:

```java title="common/src/main/java/dev/kgamble/perspective/examples/common/components/input/Checkbox.java"
    // Add this near the top of the Checkbox class
    static ComponentEventDescriptor OnChangeDescriptor = ComponentUtilities
        .getEventDescriptor(
            "events/example-checkbox/onChange.json",
            "onChange",
            "Triggered when the checkbox state changes"
        );

    // Modify the DESCRIPTOR builder to include events
    public static final ComponentDescriptor DESCRIPTOR = ComponentDescriptorImpl
        .ComponentBuilder.newBuilder()
        // ... other builder methods ...
        .setEvents(List.of(OnChangeDescriptor))
        // ... rest of builder chain ...
        .build();
```

This registers our onChange event with the component descriptor, making it available for use as a Perspective component event.

## Common Scope Summary

The common scope establishes the foundation of our Checkbox component by:

1. Defining its basic structure and properties
2. Creating property and event schemas
3. Registering it with the component system

These files work together to:

- Provide type safety and validation
- Enable property editing in Designer
- Define the component's API
- Set up event handling

Check out these other files in the common scope for additional functionality:

- Defining constants and utilities: `Constants.java`
- Exposing file resources via the Ignition web server: `ExampleComponents.java`

Next, we'll implement the Designer scope to add visual assets and customize how the component appears in the Perspective Designer.
