# VS2 Ship Systems

**VS2 Ship Systems** is a Minecraft Forge 1.20.1 mod that adds advanced ship systems and survival mechanics on top of [Valkyrien Skies 2](https://modrinth.com/mod/valkyrien-skies).

## Planned Features

- **Hull Integrity**: Track and simulate structural damage to ships. Breaches, leaks, repair mechanics.
- **Flood Detection**: Realistic flooding of ship compartments when hull is breached. Water levels, pumps, etc.
- **Environmental Sealing**: Pressurized rooms, airlocks, oxygen / life support systems.
- **Ship Systems UI / HUD**: Displays for integrity, flood status, seal status.
- **Integration with VS2 ships**: Uses ship attachments, shipyard events, chunk claims, etc.

More features will be added as the project develops.

## Project Setup (Development)

### Prerequisites

- JDK 17 (Temurin recommended)
- IntelliJ IDEA (recommended) or Eclipse
- Git (optional)

### Getting the Gradle Wrapper

This project is based on the official Forge MDK for 1.20.1.

The wrapper scripts (`gradlew` + `gradlew.bat`) and `gradle/wrapper/gradle-wrapper.properties` are included.

**One file is still needed:** `gradle/wrapper/gradle-wrapper.jar` (binary, ~60KB).

**Fastest way on Windows:**

1. Download the Forge MDK zip for **1.20.1-47.3.7** (or any close 47.x) from https://files.minecraftforge.net/net/minecraftforge/forge/index_1.20.1.html (click Mdk → Skip ad).
2. Open the zip and copy ONLY the file:
   `gradle/wrapper/gradle-wrapper.jar`
   into your project's `gradle/wrapper/` folder.
3. Done. The first time you run `./gradlew` it will download the full Gradle distribution (~150MB) automatically.

(You can also copy the entire `gradle/` folder + scripts from the MDK if you prefer a fresh copy.)

### Importing into IDE

**IntelliJ IDEA**:

1. `File` > `Open` > select this folder (the one containing `build.gradle`).
2. When prompted, accept to import as Gradle project.
3. After import finishes, run the Gradle task: `genIntellijRuns` (or right-click in Gradle panel).
4. Set your project's SDK to Java 17 if not auto-detected.

**Other IDEs**:

- Eclipse: Run `gradlew genEclipseRuns`
- VS Code: Use the Gradle for Java extension + `genVSCodeRuns`

### Building

```powershell
./gradlew build
```

The built jar will be in `build/libs/vs2_ship_systems-*.jar`.

### Running the game (dev environment)

```powershell
# Client
./gradlew runClient

# Server (you will need to accept the EULA the first time in the `run/` folder)
./gradlew runServer
```

## Adding VS2 as a Dependency

VS2 is already declared in `gradle.properties` (`vs2_version`) and `build.gradle`.

- Update the version numbers in `gradle.properties` when a new VS2 release drops.
- Re-run Gradle (refresh) after changing versions.
- The mod declares `valkyrienskies` as a **mandatory** dependency in `mods.toml`.

## Contributing / Roadmap

This is currently a skeleton. Issues and PRs are welcome once the project has more structure.

## License

All Rights Reserved (change in `gradle.properties` + `mods.toml` if you open-source it).

---

Built for Minecraft 1.20.1 + Forge + Valkyrien Skies 2.
