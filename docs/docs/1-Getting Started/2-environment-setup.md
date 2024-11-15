---
title: Environment Setup
description: Set up your development environment for Example Component Library
---

# Development Environment Setup

This guide will help you set up a complete development environment for building Ignition Perspective components.

## Required Tools

- Java Development Kit (JDK) 17
- Node.js (LTS version)
- Docker & Docker Compose
- Git
- VS Code (recommended)

## Installation Steps

### 1. Install SDKMAN! and Java

```bash
# Install SDKMAN!
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"

# Install Java
sdk install java 17.0.11-zulu
```

### 2. Install Node.js

```bash
# Using nvm (recommended)
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.3/install.sh | bash
source ~/.bashrc
nvm install --lts
```

### 3. Install Docker

Follow the [official Docker installation guide](https://docs.docker.com/get-docker/) for your operating system.

### 4. IDE Setup

1. Install VS Code
2. Install recommended extensions:
   - Java Extension Pack
   - ESLint
   - Prettier
   - Docker

## Verification

Run these commands to verify your setup:

```bash
java -version    # Should show Java 17
node -version   # Should show LTS version
npm -version    # Should match Node.js LTS
docker --version
docker-compose --version
```

## Next Steps

- Follow our [Quick Start Guide](quick-start)
- Check [Troubleshooting](troubleshooting) if needed
- Learn about our [development workflow](../Development/hot-reload)
