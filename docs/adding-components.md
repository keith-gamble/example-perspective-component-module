# Detailed Guide: Adding Components to the Example Component Library

This guide provides a step-by-step process for adding new components to the Example Component Library. We'll cover each required file, explain the registration process, and provide details on how the different parts of the module interact.

## Overview

Adding a new component involves creating or modifying files in several parts of the project:

1. Web (React/TypeScript)
2. Common (Java)
3. Gateway (Java)
4. Designer (Java)
5. Resources

Let's go through each of these in detail.

## 1. Web (React/TypeScript)

### 1.1 Create the React Component

Create a new TypeScript file in `web/src/components/`. For example, `MyNewComponent.tsx`:

```typescript
import * as React from 'react';
import {
	Component,
	ComponentMeta,
	ComponentProps,
	PComponent,
	PropertyTree,
	SizeObject
} from '@inductiveautomation/perspective-client';


// This will be the basis of the components properties in the Ignition Designer
export interface MyNewComponentProps {
    text?: string;
}

export class MyNewComponent extends Component<ComponentProps<MyNewComponentProps>, any> {
	// The render function is where the component's UI is defined. It will be re-rendered whenever the component's properties change.
    render() {
		// Pull any necessary properties from the props object so that you can use them in the component
        const { props: { text }, emit } = this.props;
        return <div {...emit()}>{text}</div>;
    }
}

export class MyNewComponentMeta implements ComponentMeta {
    getComponentType(): string {
		// This should match the ID used in the Java component class
        return 'examples.mynewcomponent';
    }

    getViewComponent(): PComponent {
        return MyNewComponent;
    }

    getDefaultSize(): SizeObject {
		return ({
			width: 100,
			height: 50
		});
	}
}
```

To update update your component properties from within the component itself, you can use the following method on the ComponentProps object:

```ts

export interface ExampleComponent {
    text?: string;
	// Make sure to add the disabled property to the interface
	disabled?: boolean;
}

export class ExampleComponent extends Component<ComponentProps<ExampleComponent>, any> {
	// ... existing component code

	// If we want to disable our button when we click it
	disableOnClick = () => {
		// Write to the props store to update the component's properties
		this.props.store.props.write('disabled', true);
	}

	// Add the reference to the function in the render method
	render() {
		// Make sure to pull the new prop from the props object, to update our component when the prop changes
		const { props: { text, disabled }, emit } = this.props;
		return <div 
				{...emit()}
				onClick={this.disableOnClick} 
				disabled={disabled}
			>{text}</div>;
	}

	// ...remaining component code
}
```

### 1.2 Update the Index File

Add your new component to `web/src/index.ts`:

```typescript
import { ComponentMeta, ComponentRegistry } from '@inductiveautomation/perspective-client';
import { MyNewComponent, MyNewComponentMeta } from './components/MyNewComponent'; // Import your component

export { MyNewComponent }; // Add your component to the exports

const components: Array<ComponentMeta> = [
    new MyNewComponentMeta(), // Add your component to the list of components
    // ... other components
];

components.forEach((c: ComponentMeta) => ComponentRegistry.register(c));
```

### 1.3 Add Styles (if necessary)

If your component needs specific styles, add them to `web/src/css/styles.css`.

## 2. Common (Java)

### 2.1 Create the Java Component Class

Create a new Java file in `common/src/main/java/dev/bwdesigngroup/perspective/examples/common/components/`. For example, `MyNewComponent.java`:

```java
package dev.bwdesigngroup.perspective.examples.common.components;

import java.util.List;
import com.inductiveautomation.perspective.common.api.ComponentDescriptor;
import com.inductiveautomation.perspective.common.api.ComponentDescriptorImpl;
import dev.bwdesigngroup.perspective.examples.common.Constants;
import dev.bwdesigngroup.perspective.examples.common.ExampleComponents;

public class MyNewComponent {
	// This should match the ID used in the TypeScript component class
    public static final String COMPONENT_ID = "examples.mynewcomponent";
	public static final String COMPONENT_NAME = "My New Component";
	public static final String COMPONENT_DESCRIPTION = "A new component for the Example Component Library";

    public static ComponentDescriptor DESCRIPTOR = ComponentDescriptorImpl.ComponentBuilder.newBuilder()
        .setPaletteCategory(ExampleComponents.COMPONENT_CATEGORY)
        .setId(COMPONENT_ID)
        .setModuleId(Constants.MODULE_ID)
        .setSchema(ComponentUtilities.getSchemaFromFilePath("/props/mynewcomponent.props.json"))
		.addPaletteEntry("", COMPONENT_NAME, COMPONENT_DESCRIPTION, null, null)
        .setName("My New Component")
        .setDefaultMetaName("myNewComponent")
        .setResources(ExampleComponents.BROWSER_RESOURCES)
        .build();
}
```

### 2.2 Create the Props Schema

Create a JSON schema file for your component's properties in `common/src/main/resources/props/`. For example, `mynewcomponent.props.json`:

```json
{
  "type": "object",
  "properties": {
    "text": {
      "type": "string",
      "default": "Hello, World!"
    }
  }
}
```

## 3. Gateway (Java)

### 3.1 Update the Gateway Hook

Modify `gateway/src/main/java/dev/bwdesigngroup/perspective/examples/gateway/ExampleComponentLibraryGatewayHook.java` to register your new component:

```java
import dev.bwdesigngroup.perspective.examples.common.components.MyNewComponent;

public class ExampleComponentLibraryGatewayHook extends AbstractGatewayModuleHook {
    @Override
    public void startup(LicenseState activationState) {
        // ... existing code ...
        
        if (this.componentRegistry != null) {
            this.componentRegistry.registerComponent(MyNewComponent.DESCRIPTOR);
            // ... other component registrations ...
        }
    }

    @Override
    public void shutdown() {
        // ... existing code ...
        
        if (this.componentRegistry != null) {
            this.componentRegistry.removeComponent(MyNewComponent.COMPONENT_ID);
            // ... other component removals ...
        }
    }
}
```

## 4. Designer (Java)

### 4.1 Update the Designer Hook

Modify `designer/src/main/java/dev/bwdesigngroup/perspective/examples/designer/ExampleComponentLibraryDesignerHook.java` to register your new component:

```java
import dev.bwdesigngroup.perspective.examples.common.components.MyNewComponent;

public class ExampleComponentLibraryDesignerHook extends AbstractDesignerModuleHook {
    private void init() {
        // ... existing code ...
        
        ComponentUtilities.registerComponentWithIcon(registry, MyNewComponent.DESCRIPTOR, "/images/mynewcomponent-icon.svg");
        // ... other component registrations ...
    }

    private void removeComponents() {
        registry.removeComponent(MyNewComponent.COMPONENT_ID);
        // ... other component removals ...
    }
}
```

## 5. Resources

### 5.1 Add Component Icon

Create an SVG icon for your component in `designer/src/main/resources/images/`. For example, `mynewcomponent-icon.svg`.

## Understanding Component Registration

Component registration is the process of making your new component known to the Perspective component system. This happens in two places:

1. **Gateway Registration**: This makes the component available for use in Perspective sessions. It's done in the `ExampleComponentLibraryGatewayHook` class.

2. **Designer Registration**: This makes the component available in the Perspective Component Palette for drag-and-drop use. It's done in the `ExampleComponentLibraryDesignerHook` class.

Registration involves associating your component's unique ID with its descriptor, which includes all the metadata about your component (its name, properties, events, etc.).

## Building and Testing

After adding all the necessary files and registering your component:

1. Build the module:
   ```
   ./gradlew clean build
   ```

2. Deploy the module to your local Ignition gateway:
   ```
   ./gradlew build deployModl
   ```

3. Open the Ignition Designer and check if your new component appears in the Component Palette.

4. Try dragging your component onto a view and configuring its properties.

## Refreshing your Designer with new web changes

If you make changes to the web portion of your component, there is no need to restart the designer to see those changes. To refresh the Designer, do the following:

1. Launch the Dev Tools (AKA, the External Debugger) by selecting `Tools > Launch Perspective... > External Debugger` in the menu bar.

2. Press Cmd+R (Mac) or Ctrl+R (Windows) to refresh the designer, just like you would in a web browser.

## Best Practices

1. Keep your component ID unique and consistent across all files.
2. Using a Perspective Session, looking at an individual view with just your component in it can be a good way to test your component without worrying about designer restarts, etc.
3. Follow the existing naming conventions in the project.
4. Always update both the Gateway and Designer hooks when adding or removing components.
5. Test your component thoroughly in different scenarios.
6. Update any relevant documentation or README files to include information about your new component.

## Troubleshooting

If your component doesn't appear in the Designer:
- Double-check that you've registered it in both the Gateway and Designer hooks.
- Ensure that the component ID is consistent across all files.
- Check the Ignition logs for any error messages.

If your component appears but doesn't function correctly:
- Verify that the props schema matches your TypeScript interface.
- Check the browser console for any JavaScript errors.

Remember, adding a new component is a multi-step process that touches several parts of the module. Take your time, and don't hesitate to ask for help if you run into issues!

## Practice Ideas

1. Recreate the native Checkbox component with custom styling.
2. Create a new component that displays a live clock.
3. Implement a component that fetches data from an external API and displays it.
