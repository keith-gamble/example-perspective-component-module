---
title: Component Schema Reference
description: Understanding component property and event schemas in Perspective
---

# Component Schema Reference

This guide explains how to define component properties and events using Perspective's schema system.

## Property Schemas

### Basic Property Types

```json
{
  "type": "object",
  "properties": {
    "value": {
      "default": "Radio button",
      "description": "The value of the selected radio"
    },
    "enabled": {
      "type": "boolean",
      "default": true,
      "description": "If user should be allowed to select a radio"
    },
    "index": {
      "type": "number",
      "description": "The index of the selected radio",
      "default": 0
    }
  }
}
```

### Enumerated vs Suggested Values

Perspective supports both strict enums and flexible suggestions:

```json
{
  "properties": {
    "textPosition": {
      "type": "string",
      "enum": ["top", "right", "bottom", "left"],
      "default": "right",
      "description": "Where to place label text in relation to radio button"
    },
    "align": {
      "type": "string",
      "description": "Align radios along the cross axis",
      "suggestions": ["start", "center", "end"],
      "default": "center"
    }
  }
}
```

:::tip Enum vs Suggestions

- Use `enum` when values must be from a specific set
- Use `suggestions` and a `string` type when offering recommended values but allowing custom input
  :::

### Array Properties

Arrays with complex item definitions:

```json
{
  "radios": {
    "type": "array",
    "description": "List of radios that make up this group",
    "items": {
      "type": "object",
      "additionalProperties": false,
      "required": ["value", "text", "selected"],
      "properties": {
        "text": {
          "type": ["string", "number"],
          "default": "",
          "description": "Text to pair with this radio"
        },
        "selected": {
          "type": "boolean",
          "default": false,
          "description": "If true, this radio is selected"
        },
        "value": {
          "default": "",
          "description": "The value of the radio to be evaluated when selected"
        }
      },
      "default": {
        "text": "Radio button",
        "selected": false,
        "value": ""
      }
    },
    "default": [
      {
        "text": "Radio button",
        "selected": true,
        "value": "Radio button"
      }
    ]
  }
}
```

### Complex Object References

Using schema definitions and references:

```json
{
  "properties": {
    "selectedIcon": {
      "$ref": "urn:ignition-schema:schemas/icon-schema.json",
      "default": {
        "path": "material/radio_button_checked",
        "color": "red"
      },
      "description": "Icon to show when selected"
    }
  },
  "definitions": {
    "icon": {
      "type": "object",
      "properties": {
        "path": {
          "type": "string",
          "default": "",
          "description": "Shorthand path to icon source, in format: library/iconName"
        },
        "color": {
          "type": "object",
          "properties": {
            "enabled": {
              "type": "string",
              "format": "color",
              "default": "",
              "description": "Color of the icon when enabled"
            },
            "disabled": {
              "type": "string",
              "format": "color",
              "default": "",
              "description": "Color of the icon when disabled"
            }
          }
        }
      },
      "oneOf": [
        {
          "required": ["path"]
        },
        {
          "required": ["library", "name"]
        }
      ]
    }
  }
}
```

### Style Properties

Perspective provides standard style schema references:

```json
{
  "radioStyle": {
    "$ref": "urn:ignition-schema:schemas/style-properties.schema.json",
    "description": "Style properties for individual radio buttons"
  },
  "style": {
    "$ref": "urn:ignition-schema:schemas/style-properties.schema.json",
    "description": "Style properties for the component container"
  }
}
```

## Event Schemas

Events define the data structure that will be emitted when the event occurs.

```json
{
  "events": [
    {
      "name": "onActionPerformed",
      "description": "This event is fired when the 'action' of the component occurs.",
      "documentationUrl": "https://links.inductiveautomation.com/81-action-performed-event",
      "schema": {
        "type": "object"
      }
    }
  ]
}
```

## Best Practices

1. **Property Definitions**

   - Use clear descriptions
   - Provide sensible defaults
   - Consider using suggestions for flexible inputs
   - Use enums for strict value sets

2. **Schema Structure**

   - Use references for reusable definitions
   - Group related properties logically
   - Consider using `additionalProperties: false` for strict typing

3. **Documentation**

   - Include descriptive comments
   - Add documentation URLs for complex features
   - Explain relationships between properties

4. **Validation**
   - Use required arrays for mandatory properties
   - Provide clear validation rules
   - Consider using oneOf for complex validation

## Schema References

| Feature                 | Description                                                                                                   | Example                                                                       |
| ----------------------- | ------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------- |
| `type`                  | Defines the data type of a property, such as `object`, `array`, `number`, `string`, or `boolean`.             | `"type": "object"`                                                            |
| `default`               | Specifies a default value for a property when it’s not explicitly set.                                        | `"default": 640"`                                                             |
| `description`           | Provides a description of a property’s function or purpose.                                                   | `"description": "Width below which container displays small child"`           |
| `enum`                  | Lists allowed values for a property, restricting input to predefined options.                                 | `"enum": ["width", "height"]`                                                 |
| `visibleWhen`           | Conditions when a property is visible based on another property’s value, enhancing conditional rendering.     | `"visibleWhen": {"property": "mode", "equals": "percent"}`                    |
| `$ref`                  | References external schema definitions, allowing reusability and modularity.                                  | `"$ref": "urn:ignition-schema:schemas/style-properties.schema.json"`          |
| `oneOf`                 | Allows for multiple possible schemas for a single property, with conditions to match one of the listed types. | `"oneOf": [{"type": "string"}, {"type": "object", "required": ["viewPath"]}]` |
| `required`              | Specifies which properties must be present in an object, ensuring mandatory attributes.                       | `"required": ["breakpoint", "style"]`                                         |
| `additionalProperties`  | Defines whether properties not explicitly listed in the schema are allowed.                                   | `"additionalProperties": false`                                               |
| `properties`            | Lists child properties within an object, defining each sub-property’s structure.                              | `"properties": {"width": {"type": "number", "default": 100}}`                 |
| `items`                 | Specifies the schema for elements in an array, supporting uniformity within arrays.                           | `"items": {"type": "object", "properties": {"minWidth": {"type": "number"}}}` |
| `events`                | Lists event handlers supported by the component, each with a name, description, and optional schema.          | `"events": [{"name": "onClick", "description": "Triggered when clicked"}]`    |
| `documentationUrl`      | URL providing additional documentation for an event or component.                                             | `"documentationUrl": "https://example.com/event-docs"`                        |
| `extensionFunctions`    | Custom functions allowing extension, often with arguments and a default implementation.                       | `"extensionFunctions": [{"name": "filterAlarm", "arguments": [...] }]`        |
| `format`                | Specifies format restrictions on string values, such as `color` or `view-path`.                               | `"format": "color"`                                                           |
| `defaultImplementation` | Defines a default code block for function-based features, guiding initial behavior.                           | `"defaultImplementation": "return True"`                                      |
| `additionalProperties`  | Allows additional undefined properties to be included in the object.                                          | `"additionalProperties": true`                                                |
| `pattern`               | Regular expression to enforce valid formats for a property, especially strings.                               | `"pattern": "^[+-]?[0-9]+(px\|em\|%)$"`                                       |
| `suggestions`           | Provides predefined values or suggestions, often used for formatting.                                         | `"suggestions": {"none": "none", "date [MM/DD/YYYY]": "MM/DD/YYYY"}`          |
