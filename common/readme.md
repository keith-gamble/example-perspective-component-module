# Common Scope - Example Component Library

This directory contains the shared code used by both the Gateway and Designer scopes of the Example Component Library. The common scope is crucial for maintaining consistency between the two environments and defining the core structures of our components. This also allows us to avoid code duplication and ensure that changes are reflected uniformly across the module.

## Key Files

- `Constants.java`: Holds constant values used across the module.
- `DelegatingComponentDescriptor.java`: A utility class for creating flexible component descriptors.
- `ExampleComponents.java`: Defines common properties for all example components.
- `component/input/Button.java`: Defines the Button component's structure and properties.

## Constants

This class contains module-wide constant values:

- `MODULE_ID`: The unique identifier for our module.
- `MODULE_URL_ALIAS`: The alias used for URL mounting of resources.

These constants ensure consistency across different parts of the module.

## DelegatingComponentDescriptor

This abstract class implements `ComponentDescriptor` and provides a way to override specific methods of a component descriptor while delegating others to the original implementation. This is particularly useful in the Designer scope where we might want to customize certain aspects of a component (like its icon) without redefining everything.

## ExampleComponents

This class defines common properties for all example components:

- `COMPONENT_CATEGORY`: The category under which our components will appear in the Perspective component palette.
- `BROWSER_RESOURCES`: A set of `BrowserResource` objects defining the JavaScript and CSS files needed by our components on the client side.

## Button Component

The `Button` class in the `component/input` package defines our example Button component:

### Key Elements

1. `COMPONENT_ID`: A unique identifier for the Button component.
2. `ButtonEventDescriptor`: Defines the `onActionPerformed` event for the Button.
3. `getSchema()`: Loads the JSON schema for the component's properties and events.
4. `DESCRIPTOR`: A `ComponentDescriptor` that defines how the Button appears and behaves in both the Designer and Gateway environments.

### Component Descriptor

The `DESCRIPTOR` includes:

- Palette category and entry
- Component ID and name
- Module ID
- JSON schema for properties
- Event descriptors
- Default meta name
- Browser resources

## JSON Schemas

The common scope includes JSON schema files that define the structure of the component's properties and events. These schemas are used by both the Designer (for property editing) and the Gateway (for validation).

## Best Practices

1. Use of constants for identifiers and categories ensures consistency across the module.
2. Separating the component definition (Button.java) from its implementation allows for easier maintenance and extension.
3. JSON schemas provide a clear contract for component properties and events.
4. The `DelegatingComponentDescriptor` allows for flexible customization in different scopes without code duplication.

## Importance of the Common Scope

The common scope serves as the shared foundation for both the Gateway and Designer scopes. By defining core structures, constants, and component descriptors here, we ensure that our components behave consistently across both environments. This separation also makes it easier to maintain and extend the module, as changes to core component definitions only need to be made in one place.