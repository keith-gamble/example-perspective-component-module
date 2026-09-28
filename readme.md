# Example Component Library for Ignition Perspective

[![Ignition 8.3](https://img.shields.io/badge/Ignition-8.3-blue.svg)](https://www.docs.inductiveautomation.com/docs/8.3/intro)
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
   cd web && npm install && cd ..
   ```

3. Build the module, then start an Ignition 8.3 gateway in Docker:

   ```bash
   # From the repository root
   ./gradlew build

   # Start Ignition with the module mounted
   docker compose -f docker/docker-compose.yml up -d
   ```

4. After changing Java code, descriptors or schemas, rebuild and restart the gateway. Ignition 8.3 loads modules only at startup:

   ```bash
   ./gradlew build
   docker compose -f docker/docker-compose.yml restart gateway
   ```

   Web changes (TypeScript and CSS) don't need a restart. Run `npm run watch` in `web/` and refresh the Designer or browser.

For detailed setup instructions, see our [Getting Started Guide](https://keith-gamble.github.io/example-perspective-component-module/Getting%20Started/).

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guide](https://keith-gamble.github.io/example-perspective-component-module/5-Contributing) for details.

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE.txt) file for details.

---
