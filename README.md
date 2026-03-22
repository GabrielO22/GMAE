# GuildQuest Mini Adventure Environment
### INF 122 — Group 16 | UC Irvine

---

## How to Run

### Prerequisites
- JDK 17 installed (Recommended: Temurin 17 / OpenJDK 17)
- Internet connection on first run (Gradle downloads dependencies automatically)

### Run in IntelliJ (Recommended)
1. Open the project folder in IntelliJ
2. If prompted, choose "Import as Gradle project" or click "Load Gradle Changes"
3. Wait for Gradle sync to finish (bottom-right progress bar)
4. Open the Gradle tool window (right side panel)
5. Navigate to: **Tasks → frontend → application → run**
6. The GUI window will open

### Run from the Command Line
```
./gradlew :frontend:run
```
If you get "Permission denied" on Mac/Linux:
```
chmod +x gradlew
./gradlew :frontend:run
```

### Running the JAR file
The jar file 'GMAE-Game.jar' can be found in the root folder of the project. 
```
 java -jar GMAE-Game.jar 
```
---

## Controls

### Realm Relic Run (Real-Time)
| Player | Move Up | Move Down | Move Left | Move Right |
|--------|---------|-----------|-----------|------------|
| Player 1 | W | S | A | D |
| Player 2 | ↑ | ↓ | ← | → |

### Runes of Reckoning (Turn-Based)
Both players share the terminal input field. Type a command and press **Enter**.

| Command | Shortcut | Action |
|---------|----------|--------|
| `attack` | `a` | Attack the opponent's active character |
| `defend` | `d` | Heal 25% of active character's max HP |
| `swap` | `s` | Switch to a different character |
| `item` | `i` | Use an item from your inventory |
| `forfeit` | `f` | Concede the battle |

When prompted after `swap` or `item`, type the number shown and press Enter.

All menu navigation uses **mouse clicks**.

---

## Project Structure

```
shared/         Core GMAE framework — MiniAdventure interface, game state,
                characters, items, realms, player profiles

frontend/       JavaFX GUI and real-time game engine — menus, battle screen,
                tile renderer, input handling

backend/        Setup and initialization — realm registration, adventure registration

shared/src/main/java/adventures/
    realmrelicrun/      Realm Relic Run implementation
    runesofreckoning/   Runes of Reckoning implementation
```

---

## How to Play

1. Launch the game and press **PRESS START**
2. Both players draft their team of 3 characters
3. From the Main Menu, choose a mini-adventure:
  - **Realm Relic Run** — race to collect as many items as possible before the timer runs out. Items collected carry over into Runes of Reckoning.
  - **Runes of Reckoning** — turn-based 3v3 battle. Use items collected in Relic Run to gain an advantage.
4. Visit **Player Profiles** to view your character roster, inventory, and duel record.

---

## Troubleshooting

**JavaFX imports show red in IntelliJ:**
- Refresh Gradle, or
- File → Invalidate Caches / Restart

**Wrong Java version:**
```
java -version
```
This project targets Java 17. IntelliJ can use a configured Project SDK (17) even if your system Java differs.

**First run is slow:**
Normal — Gradle is downloading JavaFX and other dependencies. Subsequent runs are fast.

**Build fails after changing build.gradle:**
Click "Load Gradle Changes" in IntelliJ or run `./gradlew --refresh-dependencies`.
