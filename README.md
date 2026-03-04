# GuildQuest Mini Adventure Environment project for INF 122 at UC Irvine

## How to Run (Gradle + Java 17)
###  Prerequisites

- JDK 17 installed
    - Recommended: Temurin 17 / OpenJDK 17
- Internet connection the first time you run (Gradle downloads dependencies)

### Steps to Run in IntelliJ (Recommended)
- Open the project folder in IntelliJ.
    - If prompted, choose “Import as Gradle project” (or “Load Gradle Changes”).
- Wait for Gradle sync to finish (bottom-right progress bar).
- Open the Gradle tool window (right side).
- Navigate to: Tasks → application → run
- The GUI window should open.

### Run from the Command Line (Mac/Linux)
- From the project root: `./gradlew run`
- If you get “Permission denied”:
    - `chmod +x gradlew`
    - `./gradlew run`

### Run from the Command Line (Windows PowerShell)
- From the project root: `.\gradlew.bat run`


### Notes (If Something Goes Wrong)

- The first run may take longer because Gradle downloads dependencies (including JavaFX).
- If you changed build.gradle, click “Load Gradle Changes” in IntelliJ or refresh Gradle.
- If JavaFX imports are red in IntelliJ:
    - Refresh Gradle, or
    - Restart IntelliJ, or
    - File → Invalidate Caches / Restart

## Controls (Two Local Players)

(Replace once keybindings are finalized)

Player 1: [W/A/S/D]

Player 2: [I/J/K/L]

Menu selection: mouse click buttons in the GUI

## Project Structure (High-Level)

`engine/` — core GMAE framework (no JavaFX imports)

`ui/` — JavaFX GUI

`adventures/` — mini-adventure implementations:
- Realm Relic Run
- Runes of Reckoning

`profiles/` — player profiles + inventory persistence

## Troubleshooting: Java Version

This project targets Java 17.

To check your Java version in terminal: `java -version`

If your system Java is not 17, IntelliJ can still run the project using its configured Project SDK (17).
