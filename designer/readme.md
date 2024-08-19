# Designer Scope - Example Component Library

This directory contains the designer-specific code for the Example Component Library. The designer scope is responsible for integrating our custom components into the Ignition Designer, providing necessary metadata, icons, and any designer-specific functionality.

## Key Files

- `ExampleComponentLibraryDesignerHook.java`: The main entry point for the designer module.
- `IconUtilities.java`: A utility class for handling SVG icons.

## ExampleComponentLibraryDesignerHook

This class extends `AbstractDesignerModuleHook`, the hook provided by the Ignition SDK that acts as the entrypoint for our Java code in the designer scope. It's responsible for registering our components with the designer and handling any designer-specific logic.

### Key Methods

1. `startup(DesignerContext context, LicenseState activationState)`:
   - Called when the module is being started in the designer.
   - We store the `DesignerContext` for later use.
   - We call our `init()` method to set up the components.

2. `shutdown()`:
   - Called when the module is being shut down in the designer.
   - We remove our registered components.

### Component Registration

In the `init()` method, we register our Button component with the designer:

```java
registry.registerComponent(new DelegatingComponentDescriptor(Button.DESCRIPTOR) {
    @Override
    @Nonnull
    public Optional<Icon> getIcon() {
        return Optional.of(componentIcon);
    }
});
```

Note that we're using a `DelegatingComponentDescriptor` here. This allows us to override specific methods (in this case, `getIcon()`) while delegating all other methods to the original descriptor. Without this it requires us to essentially duplicate all of our descriptor methods, and that is a pain.

## IconUtilities

This utility class provides methods for loading SVG icons, which are used in the Ignition Designer's component palette.

### Key Method

- `getSvgIcon(String filePath)`: Loads an SVG file and returns it as a Swing Icon.

## Resource Bundling

We use `BundleUtil` to add a resource bundle for internationalization:

```java
BundleUtil.get().addBundle("example-components", ExampleComponentLibraryDesignerHook.class.getClassLoader(),
        "example-components");
```

This allows us to provide localized strings for our components in the designer.

## Best Practices

1. We use a logger (`LoggerEx`) for proper error and info logging.
2. We separate icon loading logic into a utility class for better organization.
3. We use a delegating descriptor to override only the necessary methods when registering components.
4. We properly clean up resources in the `shutdown()` method.

## Designer Integration

By registering our components with the designer, we make them available in the component palette. The custom icon we provide helps users easily identify our components in the designer interface.

The designer hook ensures that our custom components are properly integrated into the Ignition Designer, allowing users to drag and drop them into their Perspective projects, configure their properties, and see accurate representations of the components in the design view.