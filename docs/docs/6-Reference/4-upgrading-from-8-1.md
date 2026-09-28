---
title: Upgrading from 8.1
description: What changed for this module between Ignition 8.1 and 8.3
---

# Upgrading from Ignition 8.1

This project targets Ignition 8.3. Earlier versions targeted Ignition 8.1.44. This page summarizes what changed between the two for a Perspective component module like this one. For the full list of platform changes, see the [Ignition 8.1 to 8.3 SDK Upgrade Guide](https://www.sdk-docs.inductiveautomation.com/docs/8.3/to-83-upgrade-guide/).

## Version Pins

| Item | Ignition 8.1 | Ignition 8.3 |
| --- | --- | --- |
| SDK (`gradle/libs.versions.toml`) | `8.1.44` | `8.3.9` |
| `requiredIgnitionVersion` | `8.1.44` | `8.3.0` |
| `io.ia.sdk.modl` Gradle plugin | `0.3.0` | `0.5.0` |
| Gradle wrapper | `7.6.4` | `8.7` |
| `com.github.node-gradle.node` plugin | `3.2.1` | `7.1.0` |
| `@inductiveautomation/perspective-client` | `2.1.44` | `2.3.9` |
| `@inductiveautomation/perspective-common` | not declared | `2.3.9` |
| Docker image | `inductiveautomation/ignition:8.1.44` | `inductiveautomation/ignition:8.3.9` |
| Java | 17 | 17 |
| React | 18.2 | 18.2 |

## Module Installs Need a Gateway Restart

Ignition 8.1 could install, upgrade and restart a module while the gateway kept running. The SDK Gradle plugin's `deployModl` task used this through a developer upload servlet, enabled with `-Dia.developer.moduleupload=true`.

Ignition 8.3 removes module hot-swapping and the upload servlet. Module installs and upgrades are staged while the gateway runs and applied at the next gateway restart. In this project:

- `deployModl` fails immediately with a pointer to the supported options.
- The Docker loop is `./gradlew build` followed by `docker compose -f docker/docker-compose.yml restart gateway`.
- The `deployModule` task installs the module on any 8.3 gateway through the REST API, using an API key, and can restart the gateway with `-PrestartGateway=true`.

Web asset hot reload through `-Dres.path.<module id>` works the same way on 8.3. See [Development Loop](../Development/hot-reload).

## Java APIs

The Perspective and platform APIs this module uses are the same in 8.3:

- `ComponentDescriptor`, `ComponentDescriptorImpl.ComponentBuilder` and `BrowserResource`
- `ComponentRegistry` in the gateway and `DesignerComponentRegistry` in the Designer
- The `AbstractGatewayModuleHook` and `AbstractDesignerModuleHook` lifecycle methods
- `getMountedResourceFolder()` and `getMountPathAlias()`, with resources served at `/res/<alias>/`

The breaking 8.3 changes are in areas this module doesn't use, such as `javax.servlet` becoming `jakarta.servlet`, the removal of Wicket gateway pages, the RPC API, and access control on gateway routes. The [SDK Upgrade Guide](https://www.sdk-docs.inductiveautomation.com/docs/8.3/to-83-upgrade-guide/) covers them.

The SDK and Perspective libraries are `compileOnly` dependencies in every subproject. The gateway provides them at runtime, so the module doesn't bundle its own copies.

## Web Package

`SizeObject`, the return type of `ComponentMeta.getDefaultSize()`, is exported from `@inductiveautomation/perspective-common` in 8.3 instead of `@inductiveautomation/perspective-client`. Import it as a type:

```typescript
import type { SizeObject } from "@inductiveautomation/perspective-common";
```

A type-only import is erased at compile time, so it needs no webpack external. The other `perspective-client` imports in this project are unchanged.

## Docker Environment

- **Gateway state.** The 8.1 environment restored a committed gateway backup. The 8.3 environment starts clean and configures itself with environment variables.
- **Module acceptance.** `ACCEPT_MODULE_LICENSES` and `ACCEPT_MODULE_CERTS` accept the module on first boot, so it loads without any clicks in the gateway web UI. The `inductiveautomation/ignition` image honors these for mixed-case module ids like this one from 8.3.8.
- **`GATEWAY_MODULES_ENABLED`.** In 8.3 this is an allowlist of fully-qualified module ids covering both built-in and third-party modules. It lists `com.inductiveautomation.perspective` and this module's id.
- **Removed JVM argument.** `-Dia.developer.moduleupload=true` is gone.

See [Docker Setup](../Development/docker-setup).

## Gateway Web UI

The 8.3 gateway web UI is a new interface served at the gateway root. The 8.1 admin pages under `/system/admin` are gone. Installed modules are listed under Platform > System > Modules.
