# Naming Conventions Guide

This guide explains the naming conventions used throughout the Example Component Library module, ensuring consistency across different parts of the codebase.

## Table of Contents

1. [Module Identification](#module-identification)
2. [Component Naming](#component-naming)
3. [Resource Naming](#resource-naming)
4. [Package Structure](#package-structure)
5. [Best Practices](#best-practices)

## Module Identification

### Module ID

The module ID follows a reverse domain name pattern and is defined in multiple locations:

1. **Build Configuration** (`build.gradle.kts`):

```kotlin
ignitionModule {
    id.set("dev.kgamble.perspective.examples.ExampleComponentLibrary")
}
```

2. **Constants** (`Constants.java`):

```java
public static final String MODULE_ID = "dev.kgamble.perspective.examples.ExampleComponentLibrary";
```

### Module URL Alias

Defined in `Constants.java`:

```java
public static final String MODULE_URL_ALIAS = "example-components";
```

This alias is used for:

- Resource mounting
- Web asset access
- Browser resource paths

## Component Naming

Components must maintain consistent naming across different layers:

### 1. TypeScript/React Layer

```typescript
// web/src/components/display/MyWidget.tsx
export class MyWidgetMeta implements ComponentMeta {
  getComponentType(): string {
    return "examples.display.mywidget";
  }
}
```

### 2. Java Layer

```java
// common/src/main/java/.../components/display/MyWidget.java
public class MyWidget {
    public static final String COMPONENT_ID = "examples.display.mywidget";
    public static final String COMPONENT_NAME = "My Widget";
}
```

### 3. Resource Files

Props schema: `common/src/main/resources/props/mywidget.props.json`
Icons: `designer/src/main/resources/images/mywidget-icon.svg`

## Resource Naming

### Browser Resources

```java
public static final Set<BrowserResource> BROWSER_RESOURCES = Set.of(
    new BrowserResource(
        "example-components-js",
        String.format("/res/%s/ExampleComponents.js", Constants.MODULE_URL_ALIAS),
        BrowserResource.ResourceType.JS
    )
);
```

### File Naming Conventions

1. **Props Schema Files**:

   - Location: `common/src/main/resources/props/`
   - Pattern: `{component-name}.props.json`

2. **Icon Files**:

   - Location: `designer/src/main/resources/images/`
   - Pattern: `{component-name}-icon.svg`

3. **Event Schema Files**:
   - Location: `common/src/main/resources/events/`
   - Pattern: `{component-name}/{event-name}.json`

## Package Structure

```
dev.kgamble.perspective.examples
├── common
│   ├── Constants.java
│   ├── components
│   │   ├── display
│   │   └── input
│   └── utilities
├── designer
│   └── hooks
└── gateway
    └── hooks
```

### Naming Patterns

1. **Base Package**: `dev.kgamble.perspective.examples`
2. **Component Categories**: `components.{category}`
3. **Utility Classes**: `utilities`
4. **Hook Classes**: `{scope}.hooks`

## Best Practices

### 1. Component ID Construction

```
examples.{category}.{component}
```

Example: `examples.input.button`

### 2. Resource Path Construction

```java
String.format("/res/%s/%s", Constants.MODULE_URL_ALIAS, resourceName)
```

### 3. File Organization

- Group components by category in both TypeScript and Java
- Keep resource files close to their component definitions
- Use consistent suffixes (e.g., `-icon.svg`, `.props.json`)

### 4. Naming Consistency Checklist

- [ ] Component ID matches between TypeScript and Java
- [ ] Resource files follow naming conventions
- [ ] Package structure reflects component categories
- [ ] Constants used for shared identifiers
- [ ] Event names follow consistent patterns

## Common Mistakes to Avoid

1. **Inconsistent IDs**:

   ```java
   // Wrong
   public static final String COMPONENT_ID = "button";

   // Right
   public static final String COMPONENT_ID = "examples.input.button";
   ```

2. **Resource Path Inconsistency**:

   ```java
   // Wrong
   String path = "/resources/button-icon.svg";

   // Right
   String path = String.format("/res/%s/button-icon.svg", Constants.MODULE_URL_ALIAS);
   ```

3. **Mixed Category Usage**:

   ```typescript
   // Wrong: Mixing categories
   "examples.button";

   // Right: Consistent category usage
   "examples.input.button";
   ```

Remember to maintain consistency across all these naming conventions to ensure your module works correctly and is maintainable.
