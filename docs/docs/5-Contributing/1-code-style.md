---
title: Code Style Guide
description: Code style guidelines for the Example Component Library
---

# Code Style Guide

This guide outlines the coding standards for the Example Component Library project.

## General Principles

- Write clean, readable, and maintainable code
- Follow existing patterns in the codebase
- Keep functions and classes focused and single-purpose
- Comment complex logic, but prefer self-documenting code
- Use meaningful variable and function names

## Java Code Style

### Naming Conventions

- Classes: `PascalCase`
- Methods: `camelCase`
- Variables: `camelCase`
- Constants: `UPPER_SNAKE_CASE`
- Packages: `lowercase`

```java
public class MyComponent {
    private static final String COMPONENT_ID = "examples.mycomponent";

    private String componentName;

    public void initializeComponent() {
        // Implementation
    }
}
```

### Class Organization

1. Static fields
2. Instance fields
3. Constructors
4. Public methods
5. Protected methods
6. Private methods
7. Inner classes

## TypeScript/React Code Style

### Component Structure

```typescript
import * as React from "react";
import { ComponentProps } from "@inductiveautomation/perspective-client";

export interface MyComponentProps {
  text?: string;
  onClick?: () => void;
}

export class MyComponent extends React.Component<
  ComponentProps<MyComponentProps>
> {
  render() {
    const { text, onClick } = this.props;
    return <div onClick={onClick}>{text}</div>;
  }
}
```

### File Organization

1. Imports
2. Interface definitions
3. Component class
4. Helper functions
5. Exports

### Naming Conventions

- Components: `PascalCase`
- Props interfaces: `PascalCase` with `Props` suffix
- Event handlers: `handleEventName`
- Files: Match component name

## Resource Files

### Props Schema

```json title="mycomponent.props.json"
{
  "type": "object",
  "properties": {
    "text": {
      "type": "string",
      "description": "Display text"
    }
  }
}
```

### Formatting

- Use 2 spaces for indentation in JSON files
- Include descriptions for all properties
- Order properties logically

## Documentation

### JSDoc Comments

```typescript
/**
 * A component that displays customizable text.
 * @param props - The component props
 * @param props.text - The text to display
 * @param props.onClick - Click event handler
 */
```

### Markdown Files

- Use descriptive headers
- Include code examples
- Link to related documentation
- Use admonitions for important notes

## Git Commit Messages

Follow conventional commits:

```bash
type(scope): description

# Examples:
feat(button): add hover state animation
fix(build): correct webpack configuration
docs(readme): update setup instructions
```

:::tip Commit Types

- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation
- `style`: Code style changes
- `refactor`: Code refactoring
- `test`: Testing updates
- `chore`: Maintenance
  :::

## Additional Guidelines

- Keep line length under 100 characters
- Use meaningful variable names
- Avoid unnecessary comments
- Write self-documenting code
- Include tests for new features

For more details about our development workflow, see our [Pull Request Guide](pull-requests).
