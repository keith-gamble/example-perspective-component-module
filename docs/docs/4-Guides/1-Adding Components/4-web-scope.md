---
title: Web Scope
description: Create the web scope for a Perspective component
---

## Web Scope Implementation

:::tip 📝
The web scope contains the actual React component implementation, TypeScript definitions, and styling. This is where we define how our component looks and behaves in the browser.
:::

### 1. Create Component Implementation

First, create your React component implementation:

```typescript title="web/src/components/Checkbox.tsx"
import * as React from "react";
import {
  Component, // Base Component class from Perspective
  ComponentMeta, // Interface for component metadata
  ComponentProps, // Props wrapper that includes Perspective-specific properties
  PComponent, // Type for Perspective components
  PropertyTree, // Utility for reading properties from Perspective's property tree
  SizeObject, // Interface defining component size
  StyleObject, // Interface for Perspective's style object
} from "@inductiveautomation/perspective-client";

// Component type must match the ID defined in the Java component
// This ensures the frontend and backend components are properly linked
// See dev.kgamble.perspective.examples.common.components.input.Checkbox#COMPONENT_ID
export const COMPONENT_ID = "examples.input.checkbox";

// Define the props interface for type safety
// These properties match the schema defined in example-checkbox.props.json
export interface CheckboxProps {
  value?: boolean; // Current checked state of the checkbox
  disabled?: boolean; // Whether the checkbox can be interacted with
  label?: string; // Text label displayed next to the checkbox
  style?: StyleObject; // Perspective style object for custom styling
}

/**
 * Checkbox component for Perspective
 * Extends the base Component class with our props interface
 * ComponentProps<CheckboxProps> adds Perspective-specific props to our interface
 */
export class Checkbox extends Component<ComponentProps<CheckboxProps>> {
  /**
   * Handler for checkbox state changes
   * @param event - The change event from the checkbox input
   */
  handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    // Check to make sure events are enabled, in the designer scope they shouldnt be.
    // However in the preview scope and client scope they should be.
    if (!this.props.eventsEnabled) {
      return;
    }

    // Get the new value from the checkbox input
    const newValue = event.target.checked;

    // Update the component's value in Perspective's prop store
    // This ensures the component state stays in sync with Perspective
    this.props.store.props.write("value", newValue);

    // Fire a custom event that can be handled in Perspective
    // This matches the event schema defined in onChange.json
    this.props.componentEvents.fireComponentEvent("onChange", {
      value: newValue,
    });
  };

  /**
   * Render the checkbox component
   * Uses a label wrapper for accessibility and UX
   */
  render() {
    // Destructure props and emit function from Perspective's props
    const {
      props: { value, disabled, label },
      // emit helps manage Perspective's styling system
      emit,
    } = this.props;

    return (
      // Wrapper label makes entire component clickable
      <label
        {...emit({
          // Apply our base classes and allow for custom classes
          classes: ["kg-example-checkbox-wrapper"],
        })}
      >
        {/* The actual checkbox input */}
        <input
          type="checkbox"
          className="kg-example-checkbox"
          checked={value}
          disabled={disabled}
          onChange={this.handleChange}
        />
        {/* Label text next to checkbox */}
        <span className="kg-example-checkbox-label">{label}</span>
      </label>
    );
  }
}

/**
 * Metadata class for the Checkbox component
 * This tells Perspective how to use and render our component
 */
export class CheckboxMeta implements ComponentMeta {
  /**
   * Return the component's unique identifier
   * Must match COMPONENT_ID and Java component ID
   */
  getComponentType(): string {
    return COMPONENT_ID;
  }

  /**
   * Return the React component class
   * This is what Perspective will render
   */
  getViewComponent(): PComponent {
    return Checkbox;
  }

  /**
   * Define default size in the designer
   * This helps with initial placement and sizing
   */
  getDefaultSize(): SizeObject {
    return {
      width: 150,
      height: 24,
    };
  }

  /**
   * Convert Perspective's property tree into component props
   * This is where we read and provide default values for our properties
   */
  getPropsReducer(tree: PropertyTree): CheckboxProps {
    return {
      value: tree.readBoolean("value", false),
      disabled: tree.readBoolean("disabled", false),
      label: tree.readString("label", ""),
      style: tree.readObject("style", {}),
    };
  }
}
```

Key points about the implementation:

- Uses controlled component pattern for the checkbox input
- Handles value changes through props store and events
- Provides accessible label wrapping
- Follows Perspective component patterns

### 2. Add Component Styles

Create styles for your checkbox:

```css title="web/src/css/checkbox.css"
.kg-example-checkbox-wrapper {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  font-family: var(--textFont);
  font-size: 14px;
  color: var(--neutral-100);
}

.kg-example-checkbox-wrapper:has(input:disabled) {
  cursor: not-allowed;
  opacity: 0.7;
}

.kg-example-checkbox {
  appearance: none;
  -webkit-appearance: none;
  width: 16px;
  height: 16px;
  border: 2px solid var(--neutral-50);
  border-radius: 4px;
  margin: 0;
  cursor: pointer;
  position: relative;
  background: var(--surface-light);
  transition: all 0.2s ease;
}

.kg-example-checkbox:disabled {
  cursor: not-allowed;
  border-color: var(--neutral-30);
  background: var(--surface-disabled);
}

.kg-example-checkbox:checked {
  background: var(--primary);
  border-color: var(--primary);
}

.kg-example-checkbox:checked::after {
  content: "";
  position: absolute;
  left: 4px;
  top: 1px;
  width: 4px;
  height: 8px;
  border: solid white;
  border-width: 0 2px 2px 0;
  transform: rotate(45deg);
}

.kg-example-checkbox-label {
  user-select: none;
}
```

### 3. Register Component

Update your index file to include the new component:

```typescript title="web/src/index.ts"
import {
  ComponentMeta,
  ComponentRegistry,
} from "@inductiveautomation/perspective-client";
import { Checkbox, CheckboxMeta } from "./components/Checkbox";

// Export components
export { Checkbox };

// Import styles
import "./css/checkbox.css";

// Register components
const components: Array<ComponentMeta> = [
  new CheckboxMeta(),
  // ... other components ...
];

components.forEach((c: ComponentMeta) => ComponentRegistry.register(c));
```

## Web Scope Summary

The web scope implementation provides:

1. React component implementation
2. TypeScript type safety
3. Styled presentation
4. Event handling
5. Props management

Our checkbox component features:

- Clean, modern styling using CSS variables
- Accessible markup structure
- Proper state management
- Type-safe implementation

:::tip Testing Your Component
After implementing all scopes:

1. Build the module: `./gradlew build deployModl`
2. Open the Designer
3. Create a new view
4. Find your checkbox in the component palette
5. Test all functionality:
   - Value changes
   - Event handling
   - Style customization
   - Disabled state
     :::
