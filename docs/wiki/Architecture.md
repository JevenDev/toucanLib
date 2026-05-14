# Architecture

ToucanLib is structured as an Architectury multi-project repository.

```text
ToucanLib/
├─ common/      shared loader-neutral source and resources
├─ fabric/      Fabric entrypoint and Fabric metadata
├─ neoforge/    NeoForge entrypoint, metadata, and NeoForge-only helpers
└─ docs/wiki/   source-controlled project wiki
```

## Module responsibilities

### `common`

Use `common` for code that can compile and run without directly depending on Fabric-only or NeoForge-only APIs.

Good fits:

- constants and shared logging
- pure Java utilities
- Minecraft API helpers that are available to both loaders
- Architectury-backed abstractions
- shared resources used by both platform jars

### `fabric`

Use `fabric` for Fabric-specific bootstrap and integration.

Current responsibility:

- Fabric `ModInitializer` entrypoint that calls `ToucanLib.init()`

Future responsibilities may include Fabric-specific adapters for shared abstractions.

### `neoforge`

Use `neoforge` for NeoForge-specific bootstrap and APIs.

Current responsibility:

- NeoForge `@Mod(ToucanLib.MOD_ID)` entrypoint that calls `ToucanLib.init()`

The README notes that NeoForge-specific networking, GUI layer, and config screen helpers currently remain under `com.jvn.toucanlib.neoforge.*` until matching cross-loader abstractions are added.

## Initialization flow

Both platform entrypoints delegate into the common initializer:

```text
Fabric ModInitializer ─┐
                       ├─ ToucanLib.init()
NeoForge @Mod class ───┘
```

Keep `ToucanLib.init()` safe to call from both loaders. It should only perform shared initialization or dispatch into loader-specific code through explicit platform adapters.

## Package conventions

Use the root group:

```text
com.jvn.toucanlib
```

Suggested package layout:

```text
com.jvn.toucanlib
com.jvn.toucanlib.api
com.jvn.toucanlib.client
com.jvn.toucanlib.config
com.jvn.toucanlib.network
com.jvn.toucanlib.platform
com.jvn.toucanlib.fabric
com.jvn.toucanlib.neoforge
```

Public reusable helpers should live in clearly named `api` or feature packages. Internal implementation details should avoid looking like stable public API.

## Cross-loader rule of thumb

When adding a new helper:

1. Start in `common` if it can be loader-neutral.
2. Add a tiny platform adapter only when loader APIs differ.
3. Keep mod-specific behavior out of ToucanLib.
4. Document the helper before using it across multiple mods.
