---
title: Component Variants
description: Learn how to add variants to Perspective components for pre-configured examples
---

## Adding Component Variants

:::tip 📝
Variants allow you to create pre-configured versions of your component with different default properties. This is useful for providing concise examples in the component palette, making it easier for users to drag-and-drop components with specific settings.
:::

### Purpose of Variants

Variants are pre-configured instances of a component that appear as separate entries in the Perspective Designer’s component palette. They allow you to:

- Showcase different use cases of your component (e.g., a Button with primary or secondary styling).
- Provide default property values to save users time during design.
- Simplify the user experience by offering ready-to-use configurations.

For example, in the default component palette, under the default "Input" category you will see two entries for a Button component: "Primary" and "Secondary." Each variant has its own set of default properties, like different colors, while still being the same underlying component.

### How to Add Variants

To add a variant, you modify the component’s `ComponentDescriptor` by calling the `addPaletteEntry` method on the `ComponentDescriptorImpl.ComponentBuilder`. This method allows you to define multiple palette entries for the same component, each with its own configuration.

Here’s how to do it, using the Example Button component as a reference:

#### 1. Define Variant Properties

First, decide on the default properties for your variant. For the Button component, let’s create two variants: "Primary" and "Secondary," each with different styling. These properties should match the component’s schema defined in `example-button.props.json`.

For example, assume the Button component has a `style` property that accepts a style object. We can define default styles for each variant:

- **Primary Variant**: A blue background with white text.
- **Secondary Variant**: A gray background with black text.

These default properties are passed as a `JsonObject` to `addPaletteEntry`.

#### 2. Update the Component Descriptor

Modify the `Button.java` class to add palette entries for each variant. The `addPaletteEntry` method is called on the `ComponentDescriptorImpl.ComponentBuilder` to register each variant.

Here’s the updated code:

```java title="common/src/main/java/dev/kgamble/perspective/examples/common/components/input/Button.java"
package dev.kgamble.perspective.examples.common.components.input;

import java.util.List;
import java.awt.image.BufferedImage;

import com.inductiveautomation.perspective.common.api.ComponentDescriptor;
import com.inductiveautomation.perspective.common.api.ComponentDescriptorImpl;
import com.inductiveautomation.perspective.common.api.ComponentEventDescriptor;

import dev.kgamble.perspective.examples.common.Constants;
import dev.kgamble.perspective.examples.common.ExampleComponents;
import dev.kgamble.perspective.examples.common.utilities.ComponentUtilities;
import dev.kgamble.perspective.examples.common.utilities.ImageUtilities;

// Add import for JsonObject
import com.inductiveautomation.ignition.common.gson.JsonObject;

public class Button {

    public static String COMPONENT_ID = "examples.input.button";

    private static final String THUMBNAIL_PATH = "/images/button-thumbnail.png";
    private static final int THUMBNAIL_WIDTH = 70;
    private static final int THUMBNAIL_HEIGHT = 35;

    private static final String PROPS_SCHEMA_PATH = "/props/example-button.props.json";

    private static final String COMPONENT_NAME = "Example Button";
    private static final String COMPONENT_DEFAULT_NAME = "example-button";

    static ComponentEventDescriptor ActionPerformedDescriptor = ComponentUtilities.getEventDescriptor(
            "events/example-button/onActionPerformed.json",
            "onActionPerformed",
            "This event is fired when Better Button is clicked.");

    static BufferedImage thumbnail = ImageUtilities.loadThumbnailFromFilePath(THUMBNAIL_PATH, THUMBNAIL_WIDTH,
            THUMBNAIL_HEIGHT);

    // Define default properties for the Primary variant
    private static JsonObject primaryProps() {
        JsonObject props = new JsonObject();
        JsonObject style = new JsonObject();
        style.addProperty("backgroundColor", "#007bff");
        style.addProperty("color", "#ffffff");
        props.add("style", style);
        return props;
    }

    // Define default properties for the Secondary variant
    private static JsonObject secondaryProps() {
        JsonObject props = new JsonObject();
        JsonObject style = new JsonObject();
        style.addProperty("backgroundColor", "#6c757d");
        style.addProperty("color", "#000000");
        props.add("style", style);
        return props;
    }

    public static ComponentDescriptor DESCRIPTOR = ComponentDescriptorImpl.ComponentBuilder.newBuilder()
            .setPaletteCategory(ExampleComponents.COMPONENT_CATEGORY)
            .setId(COMPONENT_ID)
            .setModuleId(Constants.MODULE_ID)
            .setSchema(ComponentUtilities.getSchemaFromFilePath(PROPS_SCHEMA_PATH))
            .setEvents(List.of(ActionPerformedDescriptor))
            .setName(COMPONENT_NAME)
            // Add the Primary variant
            .addPaletteEntry(
                "", // Variant ID
                "Primary", // Label in the palette
                "A primary styled button", // Tooltip description
                thumbnail, // Thumbnail image
                primaryProps() // Default properties
            )
            // Add the Secondary variant
            .addPaletteEntry(
                "secondary", // Variant ID
                "Secondary", // Label in the palette
                "A secondary styled button", // Tooltip description
                thumbnail, // Thumbnail image
                secondaryProps() // Default properties
            )
            .setDefaultMetaName(COMPONENT_DEFAULT_NAME)
            .setResources(ExampleComponents.BROWSER_RESOURCES)
            .build();
}
```

#### 3. Understand the `addPaletteEntry` Method

The `addPaletteEntry` method has the following signature:

```java
public ComponentDescriptorImpl.ComponentBuilder addPaletteEntry(
    String variantId,
    String label,
    String tooltip,
    BufferedImage thumbnail,
    com.inductiveautomation.ignition.common.gson.JsonObject props
)
```

- **`variantId`**: A unique identifier for this variant (e.g., `"primary"` or `"secondary"`). Use an empty string (`""`) for the default variant.
- **`label`**: The name displayed in the component palette (e.g., `"Primary"` or `"Secondary"`).
- **`tooltip`**: A description shown as a tooltip in the palette.
- **`thumbnail`**: An optional image to display in the palette (can be `null` if not needed).
- **`props`**: A `JsonObject` containing the default properties for this variant, which must match the component’s schema.

#### 4. Verify in the Designer

After rebuilding and deploying the module (`./gradlew build deployModl`), open the Ignition Designer. In the component palette, under "Example UI Library," you should now see two entries for the Button component:

- **Primary**: A button with a blue background and white text.
- **Secondary**: A button with a gray background and black text.

Dragging either variant onto a view will create a Button component pre-configured with the specified default properties.

### Best Practices for Variants

1. **Keep Variants Focused**
   - Use variants to highlight key use cases (e.g., different styles or behaviors).
   - Avoid creating too many variants, as this can clutter the palette.

2. **Align with Schema**
   - Ensure the default properties in your `JsonObject` match the component’s schema (`example-button.props.json`).
   - Invalid properties will be ignored by Perspective.

3. **Use Descriptive Labels**
   - Choose clear, concise labels and tooltips to help users understand the variant’s purpose.

4. **Test Each Variant**
   - Verify that each variant works as expected in the Designer.
   - Test edge cases, such as overriding the default properties.

### Summary

Variants enhance the usability of your components by providing pre-configured examples in the component palette. By using the `addPaletteEntry` method, you can define multiple variants for a single component, each with its own set of default properties. This makes it easier for users to adopt and customize your components in their Perspective projects.

Next, try the [Practice Exercises](practice) to enhance your component with additional features and variants.
