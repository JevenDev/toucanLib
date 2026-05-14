# Getting Started

ToucanLib is intended to be consumed by JVN mods as a shared library. It is not a gameplay mod by itself; it provides common helpers and platform entrypoints.

## Requirements

- Minecraft `1.21.1`
- Java `21`
- Fabric Loader `0.19.2`
- Fabric API `0.116.12+1.21.1`
- NeoForge `21.1.200`
- Architectury API `13.0.8`

## Building locally

From the repository root:

```sh
./gradlew build
```

On Windows PowerShell:

```powershell
.\gradlew build
```

Built jars are generated under each platform module:

```text
fabric/build/libs/toucanlib-fabric-<minecraft-version>-<version>.jar
neoforge/build/libs/toucanlib-neoforge-<minecraft-version>-<version>.jar
```

## Adding ToucanLib to another mod workspace

ToucanLib is publicly available on CurseForge, and the supported development workflows right now are:

- download the published Fabric or NeoForge jar from CurseForge when you want the public release artifact
- publish the artifacts to this repository's local `repo/` Maven with `./gradlew publish`
- use a composite build when ToucanLib and the consuming mod live in the same workspace neighborhood

See the root [`README.md`](../../README.md) for the current public-usage notes and dependency snippets.

## Versioning expectations

ToucanLib should use semantic-style versions:

- patch bump for internal fixes that do not change public helper behavior
- minor bump for new helpers or additive API changes
- major bump for breaking package, method, or behavior changes

Because other mods may depend on it, prefer additive changes and deprecation windows over sudden removals.
