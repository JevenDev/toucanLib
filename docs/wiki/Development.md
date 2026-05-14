# Development

Use this page as the working standard for toucanLib contributions.

## Local setup

1. Clone the repository.
2. Open it in IntelliJ IDEA or another Java 21-capable IDE.
3. Let Gradle import the Architectury multi-project build.
4. Run a full build:

```sh
./gradlew build
```

## Project standards

- Java version: `21`
- Encoding: `UTF-8`
- Mappings: official Mojang mappings through Loom
- Build system: Gradle
- Multi-loader structure: Architectury

## Adding shared helpers

Before adding a helper, ask:

- Will at least two mods use this?
- Can it live in `common`?
- Does it need a Fabric or NeoForge adapter?
- Is it small enough to be understood without pulling in a whole framework?

Preferred flow:

1. Add the smallest common abstraction first.
2. Add loader-specific code only where necessary.
3. Keep names boring and obvious.
4. Add a short usage note to the wiki once it becomes reusable.
5. Build both platform jars before release.

## Loader-specific code

Avoid mixing loader APIs into common source unless the dependency is already available cross-loader.

Use platform packages for loader-only behavior:

```text
com.jvn.toucanlib.fabric.*
com.jvn.toucanlib.neoforge.*
```

If a helper starts as NeoForge-only but may become cross-loader later, keep the public shape narrow so a Fabric implementation can be added without breaking consumers.

## Commit style

Use short, human commit messages:

```text
docs: add wiki page for releases
fix: guard client helper on dedicated servers
feat: add key mapping helper
refactor: split platform networking adapter
```

## Testing checklist

Before shipping a toucanLib change:

- `./gradlew build` passes
- Fabric jar builds
- NeoForge jar builds
- no mod-specific logic has leaked into the library
- any new helper has a clear package and lifecycle expectation
- consuming mods still launch with the updated jar

## Documentation checklist

Update the wiki when:

- a new reusable helper is added
- a helper changes behavior
- an API becomes stable enough for released mods
- setup, dependency, or version requirements change
