---
title: Troubleshooting Guide
description: Common issues and solutions when working with Example Component Library
---

# Troubleshooting Guide

Common issues and their solutions when working with Example Component Library.

## Build Issues

### Gradle Build Fails

#### Symptoms

- `./gradlew build` fails
- Missing dependency errors
- Compilation errors

#### Solutions

1. Check Java version:

   ```bash
   java -version  # Should be Java 17
   ```

2. Update Gradle wrapper:

   ```bash
   ./gradlew wrapper --gradle-version=7.6
   ```

3. Clean and rebuild:
   ```bash
   ./gradlew clean build
   ```

### NPM Build Issues

#### Symptoms

- `npm install` fails
- Webpack errors
- Missing dependencies

#### Solutions

1. Clear npm cache:

   ```bash
   npm cache clean --force
   ```

2. Delete node_modules:
   ```bash
   rm -rf node_modules
   npm install
   ```

## IDE Issues

### VS Code Java Problems

#### Symptoms

- Missing Java imports
- Red squiggles under valid code
- Intellisense not working

#### Solutions

1. Regenerate project files:

   ```bash
   ./gradlew clean eclipse
   ```

2. In VS Code:
   - `Cmd/Ctrl + Shift + P`
   - Type "Java: Clean Language Server Workspace"
   - Select "Restart and delete"

### TypeScript Errors

#### Symptoms

- TypeScript compilation errors
- Missing type definitions

#### Solutions

1. Check TypeScript version:

   ```bash
   npm list typescript
   ```

2. Update TypeScript:
   ```bash
   npm install --save-dev typescript@latest
   ```

## Docker Issues

### Container Won't Start

#### Symptoms

- `docker-compose up` fails
- Port conflicts
- Permission issues

#### Solutions

1. Check for port conflicts:

   ```bash
   lsof -i :8088
   ```

2. Stop conflicting services:
   ```bash
   docker-compose down
   docker system prune
   ```

## Still Need Help?

- Check our [GitHub Issues](https://github.com/keith-gamble/example-perspective-component-module/issues)
- [Create a new issue](https://github.com/keith-gamble/example-perspective-component-module/issues/new)
