# toucanLib Wiki

toucanLib is a small shared utility library for jvn's (that's me :D) Minecraft mods. It is currently organized as an Architectury multi-project build with shared common code plus Fabric and NeoForge platform modules.

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

- [Getting Started](Getting-Started)
- [Architecture](Architecture)
- [API Surface](API-Surface)
- [Development](Development)
- [Release Checklist](Release-Checklist)

## What belongs in toucanLib?

toucanLib holds reusable systems that are useful across multiple of my mods, especially when they reduce duplicated loader-specific code.

Some good candidates include:
- shared registration helpers
- client/server side checks
- key mapping utilities
- config screen helpers
- networking abstractions
- GUI layer helpers
- small compatibility-safe utilities used by multiple mods

I avoided moving mod-specific gameplay logic into toucanLib unless at least two mods actually need it.
