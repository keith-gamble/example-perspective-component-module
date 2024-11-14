---
sidebar_position: 2
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

# Start development environment
cd docker && docker-compose up -d
cd ..

# Build and deploy
./gradlew build deployModl
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

### 3. Start Development Environment

```bash
cd docker
docker-compose up -d
```

### 4. Build and Deploy

```bash
./gradlew build deployModl
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

- Learn about [adding new components](../guides/adding-components)
- Understand our [build system](../guides/build-system)
- Set up [hot reload](../development/hot-reload)
