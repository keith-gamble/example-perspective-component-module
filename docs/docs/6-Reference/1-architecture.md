---
title: Architecture Overview
description: Understanding the technical architecture of Perspective components
---

# Module Architecture

This guide explains how Perspective components work internally, how they communicate between scopes, and how the pieces fit together.

## High-Level Overview

```mermaid
graph TB
    subgraph "Client Scope"
        A[React Components]
        B[Component Meta]
        C[Props Store]
    end

    subgraph "Gateway Scope"
        D[Component Descriptors]
        E[Gateway Hooks]
        F[Resource Management]
    end

    subgraph "Designer Scope"
        G[Designer Hooks]
        H[Property Editor]
        I[Component Palette]
    end

    A <-->|Props/Events| C
    B -->|Registers| A
    D -->|Defines| C
    E -->|Manages| F
    F -->|Serves| A
    G -->|Configures| H
    H -->|Edits| C
    I -->|Creates| A
```

## Component Lifecycle

### Initialization Flow

```mermaid
sequenceDiagram
    participant G as Gateway
    participant M as Module
    participant C as Component
    participant R as React

    G->>M: Load Module
    M->>G: Register Components
    G->>C: Initialize Component
    C->>R: Mount Component
    R->>C: Render UI
    C->>G: Ready for Events
```

## Scope Communication

### Props Flow

```mermaid
graph LR
    subgraph "Designer"
        A[Property Editor]
        B[Preview]
    end

    subgraph "Gateway"
        C[Props Store]
        D[Validation]
    end

    subgraph "Client"
        E[Component]
        F[UI]
    end

    A -->|Edit| C
    C -->|Update| D
    D -->|Valid| E
    E -->|Render| F
    F -->|User Input| C
```

## Resource Architecture

### Static Resources

```
module/
├── web/
│   └── build/
│       └── resources/
│           ├── js/
│           └── css/
├── common/
│   └── resources/
│       ├── props/
│       └── events/
└── designer/
    └── resources/
        └── images/
```

### Dynamic Resource Loading

```mermaid
sequenceDiagram
    participant C as Client
    participant G as Gateway
    participant M as Module

    C->>G: Request Resource
    G->>M: Lookup Resource
    M->>G: Return Resource Path
    G->>C: Serve Resource
```

## Data Flow

### Property Updates

```mermaid
graph TD
    A[User Action] -->|Trigger| B[Component]
    B -->|Request| C[Props Store]
    C -->|Validate| D[Schema]
    D -->|Update| E[State]
    E -->|Notify| B
```

### Event Handling

```mermaid
graph TD
    A[Component] -->|Emit| B[Event System]
    B -->|Route| C[Event Handler]
    C -->|Process| D[Gateway]
    D -->|Response| E[Client]
```

## Security Model

### Scope Boundaries

```mermaid
graph TB
    subgraph "Client Scope - Restricted"
        A[Browser]
        B[React]
    end

    subgraph "Gateway Scope - Privileged"
        C[Data Access]
        D[Business Logic]
    end

    A <-->|API| C
    B <-->|Props| D
```

## Performance Considerations

### Resource Loading

- Bundling strategy
- Lazy loading
- Caching mechanisms

```mermaid
graph LR
    A[Initial Load] --> B{Cached?}
    B -->|Yes| C[Use Cache]
    B -->|No| D[Load Resource]
    D --> E[Cache Resource]
    E --> C
```

### Component Optimization

- Props memoization
- Render optimization
- Event debouncing

## Error Handling

### Error Boundaries

```mermaid
graph TD
    A[Component Error] -->|Catch| B[Error Boundary]
    B -->|Log| C[Console]
    B -->|Display| D[Fallback UI]
    B -->|Report| E[Gateway]
```

## Best Practices

### Component Design

1. **Separation of Concerns**

   - Keep view logic in React
   - Business logic in Gateway
   - Validation in schemas

2. **Resource Management**

   - Optimize bundle size
   - Use lazy loading
   - Implement caching

3. **Error Handling**
   - Implement boundaries
   - Provide fallbacks
   - Log effectively

### Performance

1. **Component Lifecycle**

   - Minimize renders
   - Clean up resources
   - Cache computations

2. **Data Flow**
   - Batch updates
   - Debounce events
   - Validate early

## Common Patterns

### State Management

```typescript
// Using Props Store
class MyComponent extends Component<MyComponentProps> {
  handleUpdate = () => {
    this.props.store.props.write("value", newValue);
  };
}
```

### Resource Loading

```java
public static final Set<BrowserResource> BROWSER_RESOURCES = Set.of(
    new BrowserResource(
        "example-components-js",
        "/res/example-components/bundle.js",
        BrowserResource.ResourceType.JS
    )
);
```

## Troubleshooting Guide

### Common Issues

1. **Resource Loading**

   - Check paths
   - Verify mounting
   - Check permissions

2. **Props Updates**

   - Validate schema
   - Check store connection
   - Verify scope access

3. **Event Handling**
   - Confirm registration
   - Check event routing
   - Verify handlers

## Next Steps

- Review [Component Schema](component-schema)
- Study [Naming Conventions](../Guides/naming-conventions)
- Explore [Development Setup](../Development/docker-setup)
