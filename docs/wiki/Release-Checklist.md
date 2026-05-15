# Release Checklist

Use this checklist before publishing or tagging a toucanLib version.

## 1. Confirm version metadata

Check `gradle.properties`:

```properties
mod_version=0.1.3
minecraft_version=1.21.1
enabled_platforms=fabric,neoforge
```

Update `mod_version` before cutting a release.

Recommended version bump rules:

- patch: bug fixes and internal cleanup
- minor: new helpers or additive public API
- major: breaking API or package changes

## 2. Build everything

```sh
./gradlew clean build
```

Confirm the expected jars exist:

```text
fabric/build/libs/toucanlib-fabric-<minecraft-version>-<version>.jar
neoforge/build/libs/toucanlib-neoforge-<minecraft-version>-<version>.jar
```

Also confirm sources and Javadoc jars are present for the platform artifacts. Published releases should include source jars so consuming projects and IDEs can inspect ToucanLib APIs directly.

## 3. Smoke test consuming mods

At minimum, test toucanLib with one mod that depends on it for each supported loader.

Check:

- Minecraft launches
- the loader detects toucanLib
- no missing dependency warnings appear
- client-only helpers do not load on dedicated servers
- common initialization runs once per platform

## 4. Review API changes

For any changed helper:

- confirm package names are stable
- avoid breaking released mods where possible
- add `@Deprecated` instead of immediate removal when practical
- update [API Surface](API-Surface.md)

## 5. Review docs

Update these pages when relevant:

- [Home](Home.md)
- [Getting Started](Getting-Started.md)
- [Architecture](Architecture.md)
- [API Surface](API-Surface.md)
- [Development](Development.md)

## 6. Tag or publish

Suggested release order:

1. Commit version/docs changes.
2. Build clean jars.
3. Create a GitHub release or tag.
4. Upload/publish Fabric and NeoForge artifacts.
5. Update consuming mods to the new toucanLib version.

For CurseForge releases:

```powershell
$env:CURSEFORGE_API_TOKEN="your-token-here"
.\gradlew printCurseForgeReleasePlan
.\gradlew publishCurseForge
```

Optional overrides:

- `-PcurseforgeReleaseType=alpha|beta|release`
- `-PcurseforgeChangelog="..."` for a one-off changelog body

## 7. Verify consumer dependency safety

Before updating consuming mods, confirm release/CI builds do not use:

- local ToucanLib paths
- `flatDir`
- `mavenLocal()` unless guarded behind an explicit local development property
- relative local jars

CI should resolve ToucanLib from CurseMaven now, or Modrinth Maven once available.

## 8. Post-release notes

Record notable changes in the GitHub release body:

- supported Minecraft version
- supported loaders
- new helpers
- fixes
- breaking changes
- migration notes for consuming mods
