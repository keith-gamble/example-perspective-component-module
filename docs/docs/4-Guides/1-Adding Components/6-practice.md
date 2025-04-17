---
title: Practice Exercises
description: Exercises to reinforce component development concepts
---

# Practice Exercises

Now that you've created a checkbox component, try these exercises to reinforce your understanding and expand your component development skills.

## 1. Enhance the Checkbox

Start with some simple enhancements to the existing checkbox:

### Exercise 1A: Add Indeterminate State

**Goal**: Add support for an indeterminate checkbox state.

**Hints**:

- Add an `indeterminate` property to the component schema
- Update the TypeScript interface
- Use the `indeterminate` HTML attribute on the input element
- Consider how this affects the `onChange` event
- Modify the `value` property to handle the new state as a nullable boolean

### Exercise 1B: Custom Colors

**Goal**: Allow custom colors for the checkbox through properties.

**Hints**:

- Add properties for `checkColor` and `borderColor`
- Use CSS custom properties (variables) in your styles
- Update the component to apply these colors
- Consider default values that work with Perspective themes

### Exercise 1C: Label Styling

**Goal**: Add support for custom label styling.

**Hints**:

- Add a `labelStyle` property to the schema
- Update the TypeScript interface
- Apply the style to the label element

### Exercise 1D: Label Position

**Goal**: Allow the label to be positioned before or after the checkbox.

**Hints**:

- Add a `labelPosition` property to the schema
- Update the TypeScript interface
- Use CSS flexbox to position the label
- Consider how this affects the label styling

## 2. Create Related Components

Build on your understanding by creating related components:

### Exercise 2A: Radio Button

**Goal**: Create a radio button component using the same patterns as the checkbox.

**Hints**:

- Much of the structure will be similar to the checkbox
- Consider how to handle the `name` attribute for grouping
- Think about how radio buttons relate to each other
- Look at how native HTML radio buttons work

### Exercise 2B: Toggle Switch

**Goal**: Create a toggle switch component that provides an alternative to the checkbox.

**Hints**:

- Use the checkbox component as a template
- Focus on different styling for the switch appearance
- Consider animation for state changes
- Think about accessibility concerns

## 3. Advanced Challenges

Ready for more complex challenges? Try these:

### Exercise 3A: Checkbox Group

**Goal**: Create a component that manages multiple checkboxes.

**Requirements**:

- Support for multiple checkboxes
- Select all/none functionality
- `InstanceStyle` support for each checkbox

**Hints**:

- Define a schema for an array of options
- Consider how to handle selected values
- Think about bulk operations (select all/none)
- Look at how to nest components

### Exercise 3B: Text Input

**Goal**: Create a text input component that includes validation.

**Requirements**:

- Support for different native text based input types
- Placeholder text
- Validation feedback via a style-able label, and outline

**Hints**:

- Define properties for value, placeholder, and validation
- Use custom events for validation feedback
- Consider how to handle different input types
- Look at how to manage focus and selection

## Best Practices to Follow

When working on these exercises, remember to:

1. **Document Everything**

   - Add JSDoc comments to methods
   - Explain complex logic
   - Document props and events

2. **Test Thoroughly**

   - Test all component states
   - Verify event handling
   - Check edge cases
   - Test with different themes

3. **Consider Accessibility**

   - Use semantic HTML
   - Add ARIA attributes when needed
   - Test keyboard navigation
   - Verify screen reader compatibility

4. **Maintain Consistency**
   - Follow existing naming conventions
   - Use consistent event patterns
   - Match existing style patterns
   - Keep prop naming consistent

## Need Help?

If you get stuck:

1. Review the checkbox implementation
2. Look at similar components in the example library
3. Check the Perspective documentation
4. Examine browser dev tools

:::tip Component Development Tips
Remember to:

- Start small and build up
- Test frequently
- Use hot reload for quick iteration
- Keep components focused and simple
  :::

## Share Your Work

Consider sharing your solutions:

- Publish your changes to a fork of the repository
- Document interesting approaches
- Share lessons learned
- Help others with their implementations
- Comment on updates needed in the example library

These exercises will help you become more comfortable with component development and familiar with Perspective's component system.
