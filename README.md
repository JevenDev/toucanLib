# toucanLib

Small shared multi-loader utility helpers for JVN mods.

## Wiki

Project documentation lives in [`docs/wiki/Home.md`](docs/wiki/Home.md).

Start there for setup notes, architecture, current API surface, development standards, and the release checklist.

## Loaders

toucanLib is structured as an Architectury multi-project build:

- `common` contains loader-neutral code and shared resources.
- `fabric` contains the Fabric entrypoint and Fabric metadata.
- `neoforge` contains the NeoForge entrypoint, NeoForge metadata, and NeoForge-only helper APIs.

## Building

```sh
./gradlew build
```

The platform jars are written to:

- `fabric/build/libs/toucanlib-fabric-<minecraft-version>-<version>.jar`
- `neoforge/build/libs/toucanlib-neoforge-<minecraft-version>-<version>.jar`

## Notes

The common module depends on Architectury API. Key mapping registration and physical-side checks already use Architectury wrappers, while NeoForge-specific networking, GUI layer, and config screen helpers remain under `com.jvn.toucanlib.neoforge.*` until matching cross-loader abstractions are added.
