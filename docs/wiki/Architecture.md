# Architecture

ToucanLib is structured as an Architectury multi-project repository.

```text
ToucanLib/
|-- common/      shared loader-neutral source and resources
|-- fabric/      Fabric entrypoint and Fabric metadata
|-- neoforge/    NeoForge entrypoint, metadata, and NeoForge-only helpers
`-- docs/wiki/   source-controlled project wiki
```

## Module responsibilities

### `common`

Use `common` for code that can compile and run without directly depending on Fabric-only or NeoForge-only APIs.

Good fits:

- constants and shared logging
- pure Java utilities
- Minecraft API helpers that are available to both loaders
- client-side rendering helpers that use shared Minecraft client APIs and stay free of loader event classes
- Architectury-backed abstractions
- shared resources used by both platform jars

### `fabric`

Use `fabric` for Fabric-specific bootstrap and integration.

Current responsibility:

- Fabric `ModInitializer` entrypoint that calls `ToucanLib.init()`

The Fabric entrypoint package is implementation detail unless a future helper is explicitly documented as public API.

### `neoforge`

Use `neoforge` for NeoForge-specific bootstrap and APIs.

Current responsibilities:

- NeoForge `@Mod(ToucanLib.MOD_ID)` entrypoint that calls `ToucanLib.init()`
- NeoForge-only networking, event bus, GUI layer, and config screen helper APIs

Keep NeoForge helper shapes narrow so cross-loader equivalents can be added later without breaking consumers.

## Initialization flow

Both platform entrypoints delegate into the common initializer:

```text
Fabric ModInitializer
        |
        +--> ToucanLib.init()
        |
NeoForge @Mod class
```

Keep `ToucanLib.init()` safe to call from both loaders. It should only perform shared initialization or dispatch into loader-specific code through explicit platform adapters.

## Package conventions

Use the root group:

```text
com.jvn.toucanlib
```

Current package layout:

```text
com.jvn.toucanlib
com.jvn.toucanlib.client
com.jvn.toucanlib.input
com.jvn.toucanlib.network
com.jvn.toucanlib.util
com.jvn.toucanlib.fabric
com.jvn.toucanlib.neoforge
```

Future internal implementation should use explicit package names such as `internal`, `impl`, or `experimental` so consuming mods do not mistake it for stable API.

## Cross-loader rule of thumb

When adding a new helper:

1. Start in `common` if it can be loader-neutral.
2. Add a tiny platform adapter only when loader APIs differ.
3. Keep mod-specific behavior out of ToucanLib.
4. Document the helper before using it across multiple mods.
