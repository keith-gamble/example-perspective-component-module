---
title: Development Overview
description: Overview of development workflow and tools
---

# Development Workflow

Our development workflow is designed to provide rapid feedback and a smooth development experience. This section covers:

```mermaid
graph LR
    A[Docker Environment] --> B[Hot Reload: web assets]
    A --> E[Rebuild + Restart: module code]
    B --> C[Development]
    E --> C
    C --> D[Testing]
    D --> B
    D --> E
```

## Key Topics

1. [Docker Setup](docker-setup) - Configure development environment
2. [Development Loop](hot-reload) - Hot reload for web assets and redeploying the module
3. [Debugging](debugging) - Troubleshooting and debugging tools

## Quick Reference

| Tool             | Purpose                          |
| ---------------- | -------------------------------- |
| Docker           | Isolated development environment |
| Hot Reload       | Rapid feedback on web changes    |
| Browser DevTools | Frontend debugging               |
| Java Debug       | Backend debugging                |

:::tip Development Best Practice
Always use the Docker development environment to ensure consistency across team members and match production environments.
:::
