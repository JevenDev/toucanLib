# ToucanLib Wiki

ToucanLib is a small shared utility library for JVN Minecraft mods. It is currently organized as an Architectury multi-project build with shared common code plus Fabric and NeoForge platform modules.

## Current targets

| Target | Value |
| --- | --- |
| Minecraft | `1.21.1` |
| Java | `21` |
| Platforms | Fabric, NeoForge |
| Mod ID | `toucanlib` |
| Package group | `com.jvn.toucanlib` |
| Current version | `0.1.0` |

## Wiki pages

- [Getting Started](Getting-Started.md)
- [Architecture](Architecture.md)
- [API Surface](API-Surface.md)
- [Development](Development.md)
- [Release Checklist](Release-Checklist.md)

## What belongs in ToucanLib?

ToucanLib should hold reusable systems that are useful across multiple JVN mods, especially when they reduce duplicated loader-specific code. Good candidates include:

- shared registration helpers
- client/server side checks
- key mapping utilities
- config screen helpers
- networking abstractions
- GUI layer helpers
- small compatibility-safe utilities used by multiple mods

Avoid moving mod-specific gameplay logic into ToucanLib unless at least two mods actually need it.
