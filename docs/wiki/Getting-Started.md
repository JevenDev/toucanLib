# Getting Started

toucanLib is intended to be consumed by JVN mods as a shared library. It is not a gameplay mod by itself; it provides common helpers and platform entrypoints.

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

## Adding toucanLib to another mod workspace

Until toucanLib is published to a remote Maven, the simplest workflow is to build it locally and depend on the generated jar in the consuming mod's development environment.

Recommended local workflow:

1. Build toucanLib with `./gradlew build`.
2. Copy the matching platform jar into the consuming mod's local `libs/` or run-mods folder.
3. Add the dependency as `modImplementation` / `runtimeOnly` according to the consuming project setup.
4. Keep the Fabric and NeoForge jar variants matched to the loader you are testing.

## Versioning expectations

toucanLib should use semantic-style versions:

- patch bump for internal fixes that do not change public helper behavior
- minor bump for new helpers or additive API changes
- major bump for breaking package, method, or behavior changes

Because other mods may depend on it, prefer additive changes and deprecation windows over sudden removals.
