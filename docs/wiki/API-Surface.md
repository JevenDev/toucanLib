# API Surface

This page tracks the intended public surface of ToucanLib.

ToucanLib is still in `0.x`, so public helpers are usable but provisional until they are used by multiple released mods and ToucanLib reaches `1.0.0`.

## Package boundaries

Current package intent:

```text
com.jvn.toucanlib              Shared core constants and initialization
com.jvn.toucanlib.client       Public client-side helper APIs
com.jvn.toucanlib.input        Public keybind helper APIs
com.jvn.toucanlib.network      Public/provisional network helper APIs
com.jvn.toucanlib.util         Public utility APIs
com.jvn.toucanlib.neoforge     NeoForge entrypoint and NeoForge-only helpers
com.jvn.toucanlib.fabric       Fabric entrypoint; not a stable API surface
```

Anything documented in the root, `client`, `input`, `network`, or `util` packages should be treated as public but early API. NeoForge helper classes under `com.jvn.toucanlib.neoforge.*` are public only for NeoForge consumers and may change if cross-loader equivalents are added later.

Platform entrypoint classes such as `ToucanLibFabric` and `ToucanLibNeoForge` are implementation details. Do not depend on them from consuming mods.

Future packages named `impl`, `internal`, or `experimental` should be treated as unstable and may change without notice.

## Helper classification

ToucanLib keeps helper classes in their existing packages for `0.x` compatibility. The table below is the source of truth for whether a public type is intended for consumers or only public because a loader, Java, or generated artifact needs to access it.

| Classification | Helpers | Notes |
| --- | --- | --- |
| Public, early API | `ToucanLib`, `ToucanIds`, `ToucanResourceLocations`, `ToucanAnimationClock`, `ToucanClientOnly`, `ToucanClientSession`, `ToucanColors`, `ToucanEasing`, `ToucanHudAnchor`, `ToucanHudAnchors`, `ToucanMotion`, `ToucanScreenRect`, `ToucanScreenRects`, `ToucanWorldTextIndicators`, `ToucanWorldTextStyle`, `ToucanKeybind`, `ToucanKeybinds`, `ToucanInstallMode`, `ToucanSafeClientHandler`, `ToucanServerCapabilityState` | Documented for consuming mods. Behavior should be changed carefully and deprecated first when possible. |
| Public, NeoForge-only early API | `ToucanConfigScreens`, `ToucanEventBuses`, `ToucanEventRegistration`, `ToucanGuiLayers`, `ToucanNetwork` | Usable from NeoForge mods only. These may gain common equivalents later. |
| Experimental public helper | `ToucanHudText` | Useful for HUD overlays, but the exact text rendering helper shape may change before `1.0.0`. Prefer keeping call sites small. |
| Internal entrypoint | `ToucanLibFabric`, `ToucanLibNeoForge` | Public for loader construction only. Consuming mods should not import, construct, subclass, or call these classes. |

## Core API

### `ToucanLib.MOD_ID`

The shared mod id constant:

```java
public static final String MOD_ID = "toucanlib";
```

Use this inside ToucanLib resources, platform entrypoints, metadata, and future network channels.

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

## Common helpers

### Namespaced IDs

`ToucanIds` is a reusable factory for mods that create many resource locations:

```java
ToucanIds ids = ToucanIds.create("examplemod");
ResourceLocation id = ids.id("thing");
ResourceLocation texture = ids.texture("gui/icon.png");
```

Use this when a consuming mod would otherwise grow its own repeated `id(String path)` helper.

### Client helpers

The `client` package contains small helpers for:

- ARGB color operations with `ToucanColors`
- easing and animation progress with `ToucanEasing`
- screen rectangles and hit testing with `ToucanScreenRect` / `ToucanScreenRects`
- HUD anchoring with `ToucanHudAnchor` / `ToucanHudAnchors`
- experimental outlined HUD text helpers with `ToucanHudText`
- animated in-world text indicators with `ToucanWorldTextIndicators` / `ToucanWorldTextStyle`
- motion smoothing with `ToucanMotion`
- client session tracking with `ToucanClientSession`
- client-only guards with `ToucanClientOnly`

These helpers may reference Minecraft client classes. Keep usage on physical-client code paths unless a helper explicitly says it is safe elsewhere.

`ToucanHudText` is intentionally marked experimental because text drawing conventions are more likely to change with Minecraft and loader UI APIs than the math/layout helpers.

#### In-world text indicators

`ToucanWorldTextIndicators` is a small client-side manager/renderer for animated world-space text indicators such as damage numbers, combo popups, warning glyphs, or status callouts.

It provides:

- floating indicators with randomized scatter and drift
- entity-anchored indicators that follow a living target while it remains valid
- directed/arc movement for indicators that orbit or peel away from an entity
- billboarded full-bright text rendering
- Minecraft's eight-way outlined text stroke
- shared pop, hover, sway, tilt, fade, drift, and expiry behavior

The consuming mod owns gameplay decisions and presets. ToucanLib only owns the reusable animation and rendering behavior.

Basic NeoForge usage:

```java
private static final ToucanWorldTextIndicators INDICATORS = new ToucanWorldTextIndicators();

public static void spawnDamage(Vec3 hitPosition, float damage, long nowMillis) {
    INDICATORS.addFloating(
            hitPosition,
            ToucanWorldTextStyle.simple(Integer.toString(Math.round(damage)), 0xFFFFF7E0),
            nowMillis);
}

@SubscribeEvent
public static void renderWorld(RenderLevelStageEvent event) {
    if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
        return;
    }

    Minecraft minecraft = Minecraft.getInstance();
    if (minecraft.level == null || minecraft.player == null) {
        INDICATORS.clear();
        return;
    }

    INDICATORS.render(
            minecraft,
            event.getPoseStack(),
            event.getCamera().getPosition(),
            event.getPartialTick().getGameTimeDeltaPartialTick(true),
            System.currentTimeMillis());
}
```

For a mod with pause-aware animation timing, pass that mod's own client animation clock value instead of `System.currentTimeMillis()`.

Create explicit `ToucanWorldTextStyle` presets when a mod needs a specific feel:

```java
ToucanWorldTextStyle crit = new ToucanWorldTextStyle(
        "crit!",
        0xFFFFD166,
        false,
        1100,
        0.023F,
        0.34D,
        0.14D,
        0.28D,
        1.45F,
        0.03F,
        0.045F,
        6.0F);
```

Keep style names, colors, particles, combat rules, and payload handling in the consuming mod. Good ToucanLib usage is to share the renderer, not to move a mod's combat feedback system into the library.

### Input helpers

`ToucanKeybinds` wraps Architectury key mapping registration:

```java
ToucanKeybinds keybinds = ToucanKeybinds.create("examplemod");
KeyMapping openScreen = keybinds.key("open_screen", InputConstants.KEY_O);
keybinds.register();
```

### Network helpers

`ToucanServerCapabilityState` and `ToucanInstallMode` help a consuming mod track whether server-side state is authoritative or the client is using fallback behavior.

`ToucanSafeClientHandler` supports reflective dispatch to client-only handler classes without loading those classes on a dedicated server.

## NeoForge helpers

`ToucanEventBuses` provides small batch helpers for NeoForge listener registration:

```java
ToucanEventBuses.on(modEventBus)
        .listener(ModEvents::registerThings);
ToucanEventBuses.game()
        .listener(GameEvents::onTick);
```

`ToucanNetwork.safePlayToClientThreaded(...)` registers clientbound payloads that dispatch to static client handlers through the client task queue without loading the handler class on a dedicated server.

`ToucanGuiLayers` and `ToucanConfigScreens` are NeoForge-only convenience wrappers for GUI layer and config screen registration.

## Stability guidelines

When promoting a helper into public API:

1. Put it in an obvious package such as `client`, `input`, `network`, `util`, or a loader-specific package.
2. Add Javadocs for behavior, loader assumptions, and lifecycle timing.
3. Avoid exposing loader-specific implementation types from common APIs unless unavoidable.
4. Prefer small focused helpers over large framework-style classes.
5. Document the helper here once another mod starts depending on it.

## Deprecation policy

For anything used by released mods:

- mark old methods/classes as `@Deprecated`
- provide the replacement in Javadocs
- keep the deprecated API through at least one minor version when possible
- remove only on a clearly documented breaking release
