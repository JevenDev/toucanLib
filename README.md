# ToucanLib

Small shared multi-loader utility helpers for JVN mods.

## Wiki

Project documentation lives in [`docs/wiki/Home.md`](docs/wiki/Home.md).

Start there for setup notes, architecture, current API surface, development standards, and the release checklist.

## Loaders

ToucanLib is structured as an Architectury multi-project build:

- `common` contains loader-neutral code and shared resources.
- `fabric` contains the Fabric entrypoint and Fabric metadata.
- `neoforge` contains the NeoForge entrypoint, NeoForge metadata, and NeoForge-only helper APIs.

## Adding ToucanLib to Your Project

ToucanLib currently supports both Fabric and NeoForge, but this repository does not publish to Modrinth Maven or another remote Maven yet.

Today there are two supported consumption workflows:

1. Use a composite build while developing ToucanLib and your mod side by side.
2. Publish ToucanLib to this repo's local file Maven and depend on the loader-specific artifact from there.

### Local Maven repository

From the ToucanLib repository root:

```sh
./gradlew publish
```

That publishes artifacts to:

```text
./repo
```

Then add that repository in the consuming mod:

```gradle
repositories {
    maven {
        name = "ToucanLib Local"
        url = uri("../toucanLib/repo")
    }
}
```

### Loader-specific dependencies

Most consuming mods should depend on the matching loader jar, not the `common` module directly.

Fabric / Loom:

```gradle
dependencies {
    modImplementation "com.jvn.toucanlib:toucanlib-fabric-1.21.1:<version>"
}
```

NeoForge / Architectury Loom:

```gradle
dependencies {
    modImplementation "com.jvn.toucanlib:toucanlib-neoforge-1.21.1:<version>"
}
```

NeoForge / ModDevGradle:

```gradle
dependencies {
    implementation "com.jvn.toucanlib:toucanlib-neoforge-1.21.1:<version>"
    localRuntime "com.jvn.toucanlib:toucanlib-neoforge-1.21.1:<version>"
}
```

Replace `<version>` with the ToucanLib version you want to target. The current project version is `0.1.1`.

### Composite-build development

If your consuming mod lives beside this repository, you can wire ToucanLib in as an included build instead of publishing jars by hand:

```gradle
includeBuild("../toucanLib") {
    dependencySubstitution {
        substitute module("com.jvn.toucanlib:toucanlib") using project(":common")
        substitute module("com.jvn.toucanlib:toucanlib-neoforge-1.21.1") using project(":neoforge")
    }
}
```

Add a Fabric substitution too if your consuming workspace targets Fabric.

### Example Java usage

```java
import com.jvn.toucanlib.ToucanLib;
import com.jvn.toucanlib.client.ToucanColors;
import com.jvn.toucanlib.client.ToucanEasing;
import com.jvn.toucanlib.client.ToucanHudAnchor;
import com.jvn.toucanlib.client.ToucanScreenRect;
import com.jvn.toucanlib.util.ToucanResourceLocations;
import net.minecraft.resources.ResourceLocation;

ResourceLocation icon = ToucanResourceLocations.id(ToucanLib.MOD_ID, "textures/gui/example.png");
int accent = ToucanColors.withAlpha(0x00FFC850, 255);
float eased = ToucanEasing.smoothstep(0.6F);
ToucanScreenRect rect = new ToucanScreenRect(12, 12, 24, 24);
ToucanHudAnchor anchor = ToucanHudAnchor.TOP_RIGHT;
```

## Install Mode Notes

ToucanLib is a loader-specific mod dependency. Install the matching Fabric or NeoForge jar on every side where your consuming mod actually loads ToucanLib classes.

- Pure client helpers such as colors, easing, HUD layout, and client-only reflection helpers are safe for client-side use.
- Shared or network-oriented integrations should be present on both sides when your mod expects server-authoritative behavior.
- `ToucanInstallMode.CLIENT_LOCAL_ONLY` means the client is using fallback behavior without a server handshake.
- `ToucanInstallMode.SERVER_AUTHORITATIVE` and `ToucanInstallMode.INTEGRATED_SERVER_AUTHORITATIVE` mean the remote or integrated server confirmed support and now owns the authoritative state.

`ToucanServerCapabilityState` and `ToucanSafeClientHandler` are helper APIs for optional capability negotiation and safe client dispatch. They do not automatically install or synchronize ToucanLib for you.

## Building

```sh
./gradlew build
```

The platform jars are written to:

- `fabric/build/libs/toucanlib-fabric-<minecraft-version>-<version>.jar`
- `neoforge/build/libs/toucanlib-neoforge-<minecraft-version>-<version>.jar`

## Notes

The common module depends on Architectury API. Key mapping registration and physical-side checks already use Architectury wrappers, while NeoForge-specific networking, GUI layer, and config screen helpers remain under `com.jvn.toucanlib.neoforge.*` until matching cross-loader abstractions are added.
