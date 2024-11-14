---
title: Debugging
description: Tools and techniques for debugging components
---

# Debugging Components

## Designer Debugging Limitations

:::caution Important Designer Limitations
The Designer runs in a Chromium-based browser embedded within Java. This means:

- Traditional browser breakpoint debugging is **not possible**
- Cannot attach Chrome DevTools directly to Designer
- No source maps for stepping through code

However, you can still use:

- `console.log`, `console.error`, etc.
- The Designer's External Debugger console
- Network request monitoring
- React component state inspection
  :::

## Using the External Debugger

1. Open External Debugger (Tools > Launch Perspective... > External Debugger)
2. Keep the debugger window open during development
3. Use console logging for debugging:

```typescript
export class MyComponent extends Component<MyComponentProps> {
  componentDidMount() {
    console.log("Component mounted with props:", this.props);
  }

  handleEvent = () => {
    console.log("Event triggered:", {
      props: this.props,
      state: this.state,
      timestamp: new Date(),
    });
  };
}
```

## Effective Debugging Strategies

Since traditional debugging isn't available, use structured logging:

```typescript
// Group related logs
console.group("MyComponent Update");
console.log("Previous props:", prevProps);
console.log("New props:", this.props);
console.log("DOM state:", this.element);
console.groupEnd();

// Track errors
try {
  // Your code
} catch (error) {
  console.error("Operation failed:", error);
  console.log("Component state:", this.state);
}
```

## Best Practices

1. Use descriptive console messages
2. Group related logs for clarity
3. Log component lifecycle events
4. Include relevant state/props in error logs
5. Keep External Debugger open during development
