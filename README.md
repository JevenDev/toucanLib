<img src="https://raw.githubusercontent.com/JevenDev/toucanLib/refs/heads/1.21.1/common/src/main/resources/logo.png">

# ToucanLib

ToucanLib is a lightweight shared helper/API library for jvn's Minecraft mods. It contains small reusable helpers for repeated setup, UI, networking, and utility code across projects.

## Supported Versions

| Minecraft | Loader | Status |
| --- | --- | --- |
| 1.21.1 | NeoForge | Supported |
| 1.21.1 | Fabric | Supported |

ToucanLib is an Architectury multi-project build:

- `common` contains loader-neutral helpers and shared resources.
- `fabric` contains the Fabric entrypoint and metadata.
- `neoforge` contains the NeoForge entrypoint, metadata, and NeoForge-only helper APIs.

## For Players

ToucanLib is a required dependency for some of my (jvn) mods. It usually does not add gameplay on its own; install the matching Fabric or NeoForge jar when another mod asks for ToucanLib.

## For Developers

Use a released ToucanLib artifact for CI and releases. Local builds are useful while actively developing ToucanLib, but they should be opt-in and never be the default dependency path for a consuming mod.

### Using Modrinth Maven

Modrinth Maven is the preferred public dependency path. ToucanLib's Modrinth project slug is `toucan`, so use loader-filtered versions when a Fabric and NeoForge artifact share the same ToucanLib version number.

```gradle
repositories {
    maven {
        name = "Modrinth"
        url = "https://api.modrinth.com/maven"
        content {
            includeGroup "maven.modrinth"
        }
    }
}

dependencies {
    modImplementation "maven.modrinth:toucan:0.1.2-neoforge"
}
```

For Fabric consumers, use:

```gradle
modImplementation "maven.modrinth:toucan:0.1.2-fabric"
```

Use `implementation` instead of `modImplementation` if your Gradle setup expects normal Java dependencies, such as some ModDevGradle configurations. The unfiltered `maven.modrinth:toucan:0.1.2` coordinate currently resolves, but the loader-filtered coordinates are safer for multi-loader projects.

### Using CurseMaven

CurseMaven remains available as a fallback. The current public CurseMaven coordinate for the NeoForge 1.21.1 file is:

```gradle
repositories {
    maven {
        name = "CurseMaven"
        url = "https://cursemaven.com"
        content {
            includeGroup "curse.maven"
        }
    }
}

dependencies {
    modImplementation "curse.maven:toucanlib-1542666:8089151"
}
```

CurseMaven coordinates are file-id based, so update the trailing file id when targeting a newer uploaded ToucanLib file.

### Optional Local Development Override

Only enable local ToucanLib resolution when you are actively developing ToucanLib and a consuming mod at the same time.

```gradle
def useLocalToucanLib = providers
        .gradleProperty("useLocalToucanLib")
        .map { it.toBoolean() }
        .orElse(false)
        .get()

repositories {
    if (useLocalToucanLib) {
        mavenLocal()
    }

    maven {
        name = "Modrinth"
        url = "https://api.modrinth.com/maven"
        content {
            includeGroup "maven.modrinth"
        }
    }
}

dependencies {
    if (useLocalToucanLib) {
        modImplementation "com.jvn.toucanlib:toucanlib-neoforge-1.21.1:0.1.3"
    } else {
        modImplementation "maven.modrinth:toucan:0.1.2-neoforge"
    }
}
```

Do not enable `useLocalToucanLib` in CI or release builds. Release builds should resolve ToucanLib from Modrinth Maven by default, or CurseMaven when you intentionally need the fallback.

### Recommended CI Rule

Consuming mods should fail CI if they accidentally depend on:

- local ToucanLib paths
- `flatDir`
- `mavenLocal()` without an explicit local development property
- relative local jars

Release and publish workflows should resolve ToucanLib from Modrinth Maven or CurseMaven only. This prevents local workspace state from leaking into builds that are meant to be reproducible.

## API Surface

ToucanLib is still in `0.x`, so public helpers are usable but provisional. Breaking changes may happen before `1.0.0`; changes should still be documented and made carefully.

Current package boundaries:

```text
com.jvn.toucanlib              Shared core constants and initialization
com.jvn.toucanlib.client       Public client-side helper APIs
com.jvn.toucanlib.input        Public keybind helper APIs
com.jvn.toucanlib.network      Public/provisional network helper APIs
com.jvn.toucanlib.util         Public utility APIs
com.jvn.toucanlib.neoforge     NeoForge entrypoint and NeoForge-only helpers
com.jvn.toucanlib.fabric       Fabric entrypoint; not a stable API surface
```

Anything documented in the root, `client`, `input`, `network`, or `util` packages should be treated as public but early API. NeoForge helper classes under `com.jvn.toucanlib.neoforge.*` are public only for NeoForge consumers and may change if cross-loader equivalents are added later. Platform entrypoint classes are implementation details.

Once ToucanLib reaches `1.0.0`, documented public packages should be treated as stable. Future `impl`, `internal`, or `experimental` packages should be treated as unstable and may change without notice.

For the current per-class classification, including helpers that are public only for loader access, see `docs/wiki/API-Surface.md`.

## Example Usage

### Resource IDs

```java
import com.jvn.toucanlib.util.ToucanIds;
import net.minecraft.resources.ResourceLocation;

ToucanIds ids = ToucanIds.create("examplemod");
ResourceLocation blockId = ids.id("example_block");
ResourceLocation icon = ids.texture("gui/example_icon.png");
```

### Client Colors And Animation

```java
import com.jvn.toucanlib.client.ToucanColors;
import com.jvn.toucanlib.client.ToucanEasing;
import com.jvn.toucanlib.client.ToucanMotion;

int translucentWhite = ToucanColors.withAlpha(0x00FFFFFF, 128);
float eased = ToucanEasing.smoothstep(0.6F);
float x = ToucanMotion.approach(currentX, targetX, 0.5F);
```

### HUD Layout

```java
import com.jvn.toucanlib.client.ToucanHudAnchor;
import com.jvn.toucanlib.client.ToucanHudAnchors;
import com.jvn.toucanlib.client.ToucanScreenRect;

ToucanScreenRect rect = ToucanHudAnchors.rect(
        ToucanHudAnchor.TOP_RIGHT,
        screenWidth,
        screenHeight,
        96,
        18,
        8,
        8
);
```

### NeoForge Event Wiring

```java
import com.jvn.toucanlib.neoforge.event.ToucanEventBuses;

ToucanEventBuses.on(modEventBus)
        .listener(ModEvents::registerThings);

ToucanEventBuses.game()
        .listener(GameEvents::onTick);
```

## Versioning

- `0.x` versions are early API development.
- Breaking changes may happen before `1.0.0`.
- Public API should still be documented and changed carefully.
- `1.0.0` means documented public API is expected to remain stable.

## Building

```sh
./gradlew build
```

On Windows PowerShell:

```powershell
.\gradlew build
```

The platform jars are written to:

```text
fabric/build/libs/toucanlib-fabric-<minecraft-version>-<version>.jar
neoforge/build/libs/toucanlib-neoforge-<minecraft-version>-<version>.jar
```

The build also produces sources and Javadoc jars for published artifacts.

## CurseForge Releases

ToucanLib includes root Gradle tasks for CurseForge uploads.

Set your token in the environment:

```powershell
$env:CURSEFORGE_API_TOKEN="your-token-here"
```

Then preview the resolved release plan:

```sh
./gradlew printCurseForgeReleasePlan
```

Then publish both loader jars:

```sh
./gradlew publishCurseForge
```

Useful overrides:

- `-PcurseforgeReleaseType=alpha|beta|release`
- `-PcurseforgeChangelog="..."` to replace the default changelog text

The project id is stored in `gradle.properties` as `curseforge_project_id`.

## Wiki

Additional project documentation lives in [`docs/wiki/Home.md`](docs/wiki/Home.md), including architecture notes, development standards, API notes, and the release checklist.
