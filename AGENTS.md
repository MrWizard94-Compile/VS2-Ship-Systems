# AGENTS — VS2 Ship Systems

**Status:** Level 4 project pointer.
**Canonical source:** https://github.com/MrWizard94-Compile/Agents-Constitution
**Skill:** `wpai-constitution`

Accepted GitHub `main` is the single source of constitutional law. Freeze one
verified accepted commit per invocation and load its `SKILL.md`,
`references/always-load.md`, and required law from that same revision.
Generated skill installations are distributions, not independent authority.

## Project context

- Target Minecraft Forge 1.20.1 and Java 17. Read `gradle.properties`,
  `build.gradle`, and the Gradle wrapper for the exact current pins; do not
  substitute NeoForge or newer Minecraft APIs.
- This mod integrates with Valkyrien Skies 2. Verify the declared VS2/core
  versions, attachments, and client/server lifecycle before changing behavior.
- Preserve gameplay semantics unless the director explicitly requests a change.
  Treat client rendering, server ticks, registration, networking, saved state,
  and world lifecycle as distinct integration boundaries.
- The declared Windows build entry is `.\gradlew.bat build`; the development
  client entry is `.\gradlew.bat runClient`. A build is not proof of in-world
  behavior, compatibility, warning cleanliness, or release acceptance.
- Preserve existing dirty work and keep changes scoped to this independent
  repository. Inspect local project docs and source before implementation.

## Governance verification and recovery

The `Constitution adoption` workflow validates an accepted, full-SHA-pinned
canonical source and audits this checkout. Its green status covers governance
only; it neither builds the mod nor certifies runtime or product quality.

Previous local instruction variants are retained in the private migration
inventory, not published as shared law. Shared quality requirements belong in
the canonical Constitution; project mission and stack-specific constraints
stay here. Historical text is not active law.
