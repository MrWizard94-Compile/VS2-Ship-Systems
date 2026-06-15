# VS2 Ship Systems

**VS2 Ship Systems** is a Minecraft Forge 1.20.1 mod that adds advanced ship systems and survival mechanics on top of [Valkyrien Skies 2](https://modrinth.com/mod/valkyrien-skies).

**Repository:** https://github.com/MrWizard94-Compile/VS2-Ship-Systems.git

> **Note:** This repo was initialized with the complete Forge 1.20.1 mod skeleton on 2026-06-14.

## Planned Features

- **Hull Integrity**: Track and simulate structural damage to ships. Breaches, leaks, repair mechanics.
- **Flood Detection**: Realistic flooding of ship compartments when hull is breached. Water levels, pumps, etc.
- **Environmental Sealing**: Pressurized rooms, airlocks, oxygen / life support systems.
- **Ship Systems UI / HUD**: Displays for integrity, flood status, seal status.
- **Integration with VS2 ships**: Uses ship attachments, shipyard events, chunk claims, etc.

More features will be added as the project develops.

## Project Setup (Development)

### Prerequisites

- **JDK 17** (Temurin / Eclipse Adoptium recommended)
- Git
- **Visual Studio Code** (this project is optimized for VS Code)

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

### VS Code Setup (Primary Development Environment)

This project is set up with first-class VS Code support.

1. **Open the folder** in VS Code (`File > Open Folder` → select the project root).

2. **Install recommended extensions**  
   When you open the project, VS Code should prompt you to install the recommended extensions.  
   Or manually install:
   - "Extension Pack for Java" (by Microsoft)
   - "Gradle for Java"
   - "Language Support for Java™ by Red Hat"

3. **Generate VS Code run configurations** (important first step)  
   Open the integrated terminal (`` Ctrl + ` ``) and run:
   ```powershell
   .\gradlew genVSCodeRuns
   ```
   This creates proper debug launch configurations for Client, Server, and Data Generation.

4. **Run the mod**
   - Use the **Run and Debug** panel (left sidebar) → choose "Minecraft Client"
   - Or use the provided tasks (`Ctrl+Shift+P` → "Tasks: Run Task" → "Run Client")

**Pro tip for supervisors:** Use the built-in Tasks (`Ctrl+Shift+P` → Tasks) for `Build Mod`, `Run Client`, `Run Server`, and `Clean + Build`.

### Other IDEs (if needed)

- Eclipse: `./gradlew genEclipseRuns`
- IntelliJ: `./gradlew genIntellijRuns` (still works, just not the primary target)

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

## Adding / Updating VS2 as a Dependency

VS2 is already declared in `gradle.properties` (`vs2_version`) and `build.gradle`.

**To update VS2:**

1. Edit `gradle.properties` → change `vs2_version` and `vscore_version`.
2. In VS Code, run the Gradle task **"Refresh Gradle Projects"** (or run `./gradlew --refresh-dependencies`).
3. The mod already declares `valkyrienskies` as a **mandatory** dependency in `mods.toml`.

## Working Style (Director + AI)

This project is developed in a **supervisory director + AI pair-programming** style:

- The director (you) gives high-level direction and priorities.
- The AI (Grok) does the detailed implementation, refactoring, debugging, and structure work.
- We keep the codebase clean, well-commented, and easy to understand at a glance.

When giving direction, high-level goals work best, for example:
- "Start building the hull integrity system. Focus on attaching data to ships first."
- "Create the basic registration classes and a simple flood detector block."
- "Add a config for hull integrity thresholds."

## Contributing / Roadmap

This is currently a skeleton. The AI will expand it based on direction.

Current high-level roadmap lives in the issues and this README. Feel free to open discussions.

## License

All Rights Reserved (change in `gradle.properties` + `mods.toml` if you open-source it).

---

Built for Minecraft 1.20.1 + Forge + Valkyrien Skies 2.
