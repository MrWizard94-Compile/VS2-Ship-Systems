# VS2 Ship Systems

**VS2 Ship Systems** is a Minecraft Forge 1.20.1 mod that adds advanced ship systems and survival mechanics on top of [Valkyrien Skies 2](https://modrinth.com/mod/valkyrien-skies).

**Repository:** https://github.com/MrWizard94-Compile/VS2-Ship-Systems.git

> **Note:** This repo was initialized with the complete Forge 1.20.1 mod skeleton on 2026-06-14.
>
> **Current Status (as of latest commit):** Basic project structure, registration system, and foundational VS2 `ShipSystemsData` attachment + integration layer are in place. Ready for system-specific logic (hull, flood, sealing).

## Current Progress

- ✅ Full Forge 1.20.1 + VS2 dependency skeleton
- ✅ VS Code optimized (extensions, tasks, launch configs)
- ✅ Deferred registration for Blocks, Items, Creative Tab + custom HullAnalyzerItem
- ✅ `ShipSystemsData` attachment (points-based hull integrity, flood level, environmental seal) with NBT persistence
- ✅ `VS2ShipIntegration`:
  - Server tick hook ensuring attachment on all ships
  - Flood simulation (increases when integrity low / not sealed; rain boosts flood on unsealed ships)
  - Block place/break events that adjust hull points for Hull Plating & Reinforced Hull
  - `shouldBlockPrecipitation(...)` used to decide if a ship position blocks rain/water
- ✅ Functional Hull Analyzer item: right-click ship block to read live integrity/flood/seal data
- ✅ **Main priority achieved**: Ships now block rain/water when environmentally sealed (or high hull integrity)
  - Mixin to `Level.isRainingAt` → rain doesn't register inside protected ships (affects cauldrons, weather checks)
  - `BlockEvent.FluidPlaceBlockEvent` → water placement is prevented inside sealed/protected ships
  - Rain actively contributes to flood level only on unsealed/low-integrity ships (ties directly to flood detection)
- ✅ Basic Forge config (hull damage multiplier, flood/seal rates)
- ✅ Example blocks wired into the system (placing hull blocks increases integrity; breaking damages it)

## Planned Features (High Level)

- **Hull Integrity**: (Core implemented) Points-based tracking from hull blocks. Damage from breaks/collisions. Repair via placement/sealant.
- **Flood Detection**: (Basic simulation active) Flood level rises/falls based on integrity + seal status. Future: compartment simulation, water placement inside ship volumes, pumps.
- **Environmental Sealing**: (Flag implemented) Prevents/reduces flooding. Future: airlocks, oxygen, pressure per section using VS2 ship chunks.
- **Ship Systems UI / HUD**: Future - displays, perhaps using VS2 ship overlays or simple item tooltips/GUI.
- **Deeper VS2 Integration**: Ship assembly events, better physics interaction (e.g. flood adding mass/drag), redstone ship systems.

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
