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

# Build and deploy
./gradlew build

# Start development environment
cd docker && docker-compose up -d
cd ..

# Deploy module
./gradlew deployModl
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

### 4. Build the Module

:::warning Build Needed
You must build the module at least once before starting the development environment. This is because the end `.modl` file is mapped into the gateway, and if you start the container first, then docker will implicitly createa a directory in place of where the `modl` file will go.

To correct this, run a `./gradlew deepClean` to remove the `build` directory, then bring down the docker containers with `docker-compose down`, run `./gradlew build` again, and then start the docker containers with `docker-compose up -d`.
:::

```bash
./gradlew build
```

### 3. Start Development Environment

```bash
cd docker
docker-compose up -d
```

### 4. Build and Deploy

```bash
./gradlew deployModl
```

### 5. Accept License Agreement

1. Open Ignition Gateway
2. Go to `Config` > `Modules`
3. Find the Example Component Library module
4. Accept the license agreement, if prompted.
5. Download a new gateway backup and place it in `docker/backups/gateway.gwbk`

### 5. Verify Installation

1. Open Ignition Designer
2. Create a new Perspective view
3. Find "Example UI Library" in the component palette

## Next Steps

- Learn about [adding new components](../Guides/Adding%20Components)
- Understand our [build system](../Guides/build-system)
- Set up [hot reload](../Development/hot-reload)
