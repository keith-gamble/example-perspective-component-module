---
title: Quick Start Guide
description: Get up and running quickly with Example Component Library
---

# Quick Start Guide

Get started with Example Component Library in minutes! This guide assumes you have already [set up your environment](./environment-setup).

## TL;DR

```bash
# Clone repository
git clone https://github.com/keith-gamble/example-perspective-component-module.git
cd example-perspective-component-module

# Configure environment
cp gradle.properties.template gradle.properties

# Build the module
./gradlew build

# Start an Ignition 8.3 gateway with the module installed
docker compose -f docker/docker-compose.yml up -d
```

## Step-by-Step Guide

### 1. Clone the Repository

```bash
git clone https://github.com/keith-gamble/example-perspective-component-module.git
cd example-perspective-component-module
```

### 2. Configure Build Properties

Copy the `gradle.properties.template` file to `gradle.properties`, and update the properties as needed.

```bash
cp gradle.properties.template gradle.properties
```

### 3. Build the Module

:::warning Build Needed
You must build the module at least once before starting the development environment. This is because the `.modl` file is mapped into the gateway, and if you start the container first, then Docker will implicitly create a directory in place of where the `.modl` file will go.

To correct this, bring down the Docker containers with `docker compose -f docker/docker-compose.yml down`, run `./gradlew build` again, and then start the containers again.
:::

```bash
./gradlew build
```

### 4. Start the Development Environment

```bash
docker compose -f docker/docker-compose.yml up -d
```

The gateway accepts the module's license and certificate on first boot through environment variables in the compose file, so the module loads without any prompts. See [Docker Setup](../Development/docker-setup) for details.

### 5. Verify Installation

1. Open the gateway at `https://perspective-component.localtest.me` and check Platform > System > Modules for Example Component Library
2. Open Ignition Designer
3. Create a new Perspective view
4. Find "Example UI Library" in the component palette

### 6. Load Your Changes

Ignition 8.3 loads modules only when the gateway starts. After changing Java code, descriptors or schemas, rebuild and restart the gateway:

```bash
./gradlew build
docker compose -f docker/docker-compose.yml restart gateway
```

Changes to TypeScript and CSS reload without a restart. See [Development Loop](../Development/hot-reload).

## Next Steps

- Learn about [adding new components](../Guides/Adding%20Components)
- Understand our [build system](../Guides/build-system)
- Set up the [development loop](../Development/hot-reload)
