package adventures.realmrelicrun;

import engine.input.Command;
import engine.state.PlayerNumber;

/**
 * Controller for Realm Relic Run.
 *
 * Architectural note: Realm Relic Run is a real-time 2D game rendered via
 * Java2D/Swing (GamePanel). Player input, movement, collision, item pickup,
 * and the game timer are all handled directly by GamePanel and Player on the
 * Swing thread at 60fps. This controller exists to satisfy the MiniAdventure
 * interface contract — handleCommand() and tick() are intentionally no-ops
 * because the real-time engine drives the game loop independently.
 *
 * To extend Relic Run game logic (e.g. power-ups, traps, NPC behaviour),
 * implement it in GamePanel.update() or Player.update() rather than here.
 */
public class RealmRelicRunController {
    public RealmRelicRunController() {}

    public RealmRelicRunGameState handleCommand(PlayerNumber playerNumber, Command command, RealmRelicRunGameState state) {
        // Input is handled by KeyHandler → Player.update() in the real-time engine.
        return state;
    }

    public RealmRelicRunGameState tick(RealmRelicRunGameState state) {
        // Game loop is driven by GamePanel.run() at 60fps on the Swing thread.
        return state;
    }
}