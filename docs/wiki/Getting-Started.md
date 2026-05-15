# Getting Started

ToucanLib is intended to be consumed by JVN mods as a shared library. It is not a gameplay mod by itself; it provides common helpers and platform entrypoints.

## Requirements

- Minecraft `1.21.1`
- Java `21`
- Fabric Loader `0.19.2`
- Fabric API `0.116.12+1.21.1`
- NeoForge `21.1.200`
- Architectury API `13.0.8`

## Adding ToucanLib to another mod workspace

Use the released public artifact for CI and releases. Local ToucanLib resolution should be an explicit developer-only override.

### CurseMaven

CurseMaven is the working public Gradle path while Modrinth approval is pending. The current public CurseMaven coordinate for the NeoForge 1.21.1 file is:

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

Use `implementation` instead of `modImplementation` if your consuming build expects plain Java dependencies.

### Future Modrinth Maven

After Modrinth approval, prefer Modrinth Maven for normal Gradle consumption:

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
    modImplementation "maven.modrinth:toucanlib:<version>"
}
```

Replace `<version>` once the Modrinth artifact exists.

### Optional local override

When actively developing ToucanLib and a consuming mod side by side, gate local resolution behind a Gradle property:

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
        name = "CurseMaven"
        url = "https://cursemaven.com"
        content {
            includeGroup "curse.maven"
        }
    }
}

dependencies {
    if (useLocalToucanLib) {
        modImplementation "com.jvn.toucanlib:toucanlib-neoforge-1.21.1:0.1.3"
    } else {
        modImplementation "curse.maven:toucanlib-1542666:8089151"
    }
}
```

Do not enable this in CI or release builds.

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

Sources and Javadoc jars are also produced for publication.

## CI guidance

Consuming mods should fail CI if release builds depend on:

- local ToucanLib paths
- `flatDir`
- `mavenLocal()` without an explicit local development property
- relative local jars

CI should resolve ToucanLib from CurseMaven now, or Modrinth Maven once available.

## Versioning expectations

ToucanLib uses early semantic-style versions:

- `0.x` versions are still API development
- patch bump for internal fixes that do not change public helper behavior
- minor bump for new helpers or additive API changes
- major bump for breaking package, method, or behavior changes
- `1.0.0` means documented public API is expected to remain stable

Because other mods may depend on it, prefer additive changes and deprecation windows over sudden removals.
