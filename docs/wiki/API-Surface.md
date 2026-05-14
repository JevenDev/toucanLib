# API Surface

This page tracks the intended public surface of ToucanLib.

ToucanLib is still early, so treat APIs as provisional until they are used by multiple released mods.

## Current core API

### `ToucanLib.MOD_ID`

The shared mod id constant:

```java
public static final String MOD_ID = "toucanlib";
```

Use this instead of repeating string literals in registry names, logging, platform entrypoints, metadata, and future network channels.

### `ToucanLib.LOGGER`

Shared SLF4J logger backed by Mojang `LogUtils`.

Use this for ToucanLib's own logging. Consuming mods should normally keep their own logger.

### `ToucanLib.init()`

Shared initialization entrypoint called by both platform modules.

Current behavior:

- logs ToucanLib initialization at debug level

Expected future behavior:

- shared registry/bootstrap calls
- cross-loader helper setup
- safe dispatch to platform-specific adapters where needed

## Planned helper areas

The README currently calls out these areas as existing or expected helper territory:

- Architectury-backed key mapping registration
- physical-side checks
- NeoForge networking helpers
- NeoForge GUI layer helpers
- NeoForge config screen helpers

## API stability guidelines

When promoting a helper into public API:

1. Put it in an obvious package such as `api`, `client`, `network`, or `config`.
2. Add JavaDoc for behavior, loader assumptions, and lifecycle timing.
3. Avoid exposing loader-specific implementation types from common APIs unless unavoidable.
4. Prefer small focused helpers over large framework-style classes.
5. Document the helper here once another mod starts depending on it.

## Deprecation policy

For anything used by released mods:

- mark old methods/classes as `@Deprecated`
- provide the replacement in JavaDoc
- keep the deprecated API through at least one minor version when possible
- remove only on a clearly documented breaking release

## Public vs internal packages

Suggested convention:

```text
com.jvn.toucanlib.api.*       stable reusable API
com.jvn.toucanlib.*           shared core/internal utilities
com.jvn.toucanlib.fabric.*    Fabric implementation details
com.jvn.toucanlib.neoforge.*  NeoForge implementation details
```

Do not treat platform implementation packages as stable unless the class is explicitly documented as public API.
