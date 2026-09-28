---
sidebar_position: 0
title: Introduction
description: A comprehensive guide and example for creating custom Ignition Perspective components
---

# Example Component Library for Ignition Perspective

Welcome to the Example Component Library documentation! This guide demonstrates how to create custom components for Ignition Perspective, Inductive Automation's web-based application platform.

## What's Inside

This project provides:

- ✨ Working example components with full source code
- 📚 Comprehensive development guides
- 🏗️ Production-ready project structure
- 🔧 Development workflow best practices
- 🚀 CI/CD pipeline setup
- 🐳 Docker-based development environment

## Project Structure

The repository follows standard Ignition module conventions:

```
example-perspective-component-module/
├── common/          # Shared code for Gateway and Designer
├── designer/        # Designer-specific code
├── gateway/         # Gateway-specific code
├── web/             # Frontend React components
└── docker/          # Development environment
```

:::info Why This Structure?
This structure separates concerns while maintaining Ignition's module conventions. Each directory serves a specific purpose in the module's lifecycle, from development to deployment.
:::

## Key Features

- **Type-Safe Development**: Full TypeScript support for component development
- **Modern Build System**: Gradle with Kotlin DSL for robust build configuration
- **Ignition 8.3**: Built against the Ignition 8.3 SDK
- **Hot Reload**: Web assets reload without rebuilding the module
- **Docker Integration**: Consistent development environment
- **Comprehensive Testing**: Example test setup and best practices
- **CI/CD Ready**: GitHub Actions workflows included

## Getting Started

Ready to create your first component? Start with our [Getting Started Guide](Getting%20Started).

:::tip Quick Setup
If you're familiar with Ignition development, you can jump straight to the [Quick Start Guide](Getting%20Started/quick-start).
:::

## Tools & Technologies

This project leverages modern development tools:

- Ignition 8.3 SDK
- Java 17
- Gradle (Kotlin DSL)
- TypeScript
- React
- Node.js & npm
- Docker & Docker Compose

## Next Steps

- [Set up your development environment](Getting%20Started/environment-setup)
- [Understand the build system](Guides/build-system)
- [Learn our naming conventions](Guides/naming-conventions)
- [Add your first component](Guides/Adding%20Components)

## Support

If you need help or want to report an issue:

1. Search [existing issues](https://github.com/keith-gamble/example-perspective-component-module/issues)
2. Create a new issue if needed

## Contributing

We welcome contributions! See our [Contributing Guide](Contributing) to get started.
