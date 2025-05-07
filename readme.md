# Example Component Library for Ignition Perspective

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A comprehensive example and guide for creating custom components in Ignition Perspective. This repository demonstrates best practices, development workflows, and proper module structure through working examples.

## 📚 Documentation

Visit our [documentation site](https://keith-gamble.github.io/example-perspective-component-module) to learn:

- How to set up your development environment
- Step-by-step guides for creating components
- Best practices and naming conventions
- Build system configuration
- CI/CD setup guides

## 🚀 Quick Start

1. Clone the repository:

   ```bash
   git clone https://github.com/keith-gamble/example-perspective-component-module.git
   cd example-perspective-component-module
   ```

2. Set up your development environment:

   ```bash
   # Copy gradle properties template
   cp gradle.properties.template gradle.properties

   # Install web dependencies
   cd web && npm install
   ```

3. Start the development environment:

   ```bash
   # Start Ignition in Docker
   cd docker && docker-compose up -d

   # Build and deploy the module
   ./gradlew build deployModl
   ```

For detailed setup instructions, see our [Getting Started Guide](https://keith-gamble.github.io/example-perspective-component-module/Getting%20Started/).

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guide](https://keith-gamble.github.io/example-perspective-component-module/5-Contributing) for details.

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
