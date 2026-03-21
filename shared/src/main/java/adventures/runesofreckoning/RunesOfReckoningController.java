package adventures.runesofreckoning;

import characters.Character;
import engine.input.Command;
import engine.state.GameStatus;
import engine.state.PlayerNumber;
import items.Inventory;
import items.Item;
import items.ItemType;

import java.util.List;
import java.util.Random;

public class RunesOfReckoningController {

    private static final double CRIT_MULTIPLIER = 1.75;
    // Defend = heals 25% of max HP — costs your turn, guaranteed value regardless of opponent action
    private final Random rng = new Random();

    public RunesOfReckoningController() {}

    public RunesOfReckoningGameState tick(RunesOfReckoningGameState state) {
        return state; // turn-based — nothing to do passively
    }

    public RunesOfReckoningGameState handleCommand(
            PlayerNumber playerNumber,
            Command command,
            RunesOfReckoningGameState state) {

        if (state.playerTurn() != playerNumber) return state;
        if (state.status() != GameStatus.RUNNING)  return state;

        // If we're in item-selection mode, a USE_ITEM command means "open picker"
        // — actual item use is triggered via handleItemUsed() called directly by the UI
        return switch (command) {
            case ACTION    -> handleAttack(playerNumber, state);
            case MOVE_DOWN -> handleDefend(playerNumber, state);
            case MOVE_UP   -> handleSwap(playerNumber, state);
            case USE_ITEM  -> openItemPicker(state);
            case CONFIRM   -> handleForfeit(playerNumber, state);
            default        -> state;
        };
    }

    /**
     * Called by BattleScreen when the player selects a specific item from the picker.
     *
     * Routing by ItemType:
     *   CONSUMABLE → apply to self,     fully consumed
     *   BUFF       → apply to self,     lose 1 durability
     *   WEAPON     → apply to self,     lose 1 durability
     *   DEBUFF     → apply to OPPONENT, lose 1 durability
     */
    public RunesOfReckoningGameState handleItemUsed(
            PlayerNumber user,
            Item item,
            RunesOfReckoningGameState state) {

        if (state.playerTurn() != user) return state;
        if (state.status() != GameStatus.RUNNING) return state;

        Character self     = user == PlayerNumber.PLAYER_ONE ? state.activeOne() : state.activeTwo();
        Character opponent = user == PlayerNumber.PLAYER_ONE ? state.activeTwo() : state.activeOne();
        Inventory inv      = self.getInventory();

        if (!inv.getItems().containsKey(item) || inv.getItems().get(item) <= 0) {
            return state.withLog("That item is out of stock!", false);
        }

        int remaining = inv.getItems().get(item);
        String log;

        switch (item.getType()) {
            case CONSUMABLE -> {
                item.applyEffect(self);
                inv.useItem(item, 1); // 1 durability per use, same as all other types
                log = self.getName() + " used " + item.getName() + "!"
                        + (remaining - 1 > 0 ? " (" + (remaining - 1) + " left)" : " (last one!)");
            }
            case WEAPON -> {
                item.applyEffect(self);
                boolean broken = item.useDurability(); // decrement Item's own durability counter
                if (broken) {
                    inv.useItem(item, remaining); // remove fully from inventory
                    log = self.getName() + " used " + item.getName()
                            + "! Weapon broke — removed from inventory.";
                } else {
                    log = self.getName() + " used " + item.getName()
                            + "! (" + item.getCurrentDurability() + "/" + item.getMaxDurability() + " durability)";
                }
            }
            case BUFF -> {
                item.applyEffect(self);
                inv.useItem(item, 1);
                log = self.getName() + " used " + item.getName()
                        + "! (" + (remaining - 1 > 0 ? (remaining - 1) + " uses left" : "last use!") + ")";
            }
            case DEBUFF -> {
                // Applied to OPPONENT
                item.applyEffect(opponent);
                inv.useItem(item, 1);
                log = self.getName() + " used " + item.getName()
                        + " on " + opponent.getName() + "!"
                        + " (" + (remaining - 1 > 0 ? (remaining - 1) + " left" : "last one!") + ")";
            }
            default -> {
                log = "Unknown item type — nothing happened.";
            }
        }

        return advanceTurn(user, state.withLog(log, false), log, false);
    }

    // -----------------------------------------------------------------------
    // ATTACK
    // -----------------------------------------------------------------------
    private RunesOfReckoningGameState handleAttack(PlayerNumber attacker, RunesOfReckoningGameState state) {
        Character atk = attacker == PlayerNumber.PLAYER_ONE ? state.activeOne() : state.activeTwo();
        Character def = attacker == PlayerNumber.PLAYER_ONE ? state.activeTwo() : state.activeOne();

        int rawDamage = atk.getAttack();

        // Crit check
        boolean isCrit = rng.nextDouble() < atk.getCritRate();
        if (isCrit) rawDamage = (int)(rawDamage * CRIT_MULTIPLIER);

        // Item flags on attacker
        boolean ignoresDefence = Boolean.TRUE.equals(atk.getAttribute("EFFECT_IGNORES_DEFENSE"));

        // Item flags on defender
        boolean reduceDmg      = Boolean.TRUE.equals(def.getAttribute("EFFECT_REDUCE_DMG"));
        boolean ignoresAttack  = Boolean.TRUE.equals(def.getAttribute("EFFECT_IGNORES_ATTK"));

        // Shield — fully block the attack
        if (ignoresAttack) {
            def.removeAttribute("EFFECT_IGNORES_ATTK"); // consumed on use
            String blockLog = def.getName() + "'s shield blocked " + atk.getName() + "'s attack!";
            return advanceTurn(attacker, state, blockLog, false);
        }

        int defence = ignoresDefence ? 0 : def.getDefence();
        int damage  = Math.max(1, rawDamage - defence);
        if (reduceDmg) damage = (int)(damage * 0.75);

        def.modifyCurrentHP(-damage);

        String critText = isCrit ? " CRITICAL HIT!" : "";
        String log = atk.getName() + " attacks " + def.getName()
                + " for " + damage + " damage!" + critText;

        if (def.getCurrentHP() <= 0) {
            def.modifyCurrentHP(-def.getCurrentHP()); // clamp to 0
            return handleKO(attacker, state, log);
        }

        return advanceTurn(attacker, state, log, false);
    }

    // -----------------------------------------------------------------------
    // DEFEND — heals 25% of max HP
    // -----------------------------------------------------------------------
    private RunesOfReckoningGameState handleDefend(PlayerNumber defender, RunesOfReckoningGameState state) {
        Character defChar = defender == PlayerNumber.PLAYER_ONE ? state.activeOne() : state.activeTwo();

        int healAmount = (int)(defChar.getMaxHP() * 0.25);
        int actualHeal = Math.min(healAmount, defChar.getMaxHP() - defChar.getCurrentHP());
        defChar.modifyCurrentHP(actualHeal);

        String log = defChar.getName() + " rests and recovers " + actualHeal + " HP!";

        // No defending flag needed anymore — heal is instant, no next-turn effect
        return advanceTurn(defender, state, log, false);
    }

    // -----------------------------------------------------------------------
    // OPEN ITEM PICKER — sets pendingItemSelection flag, doesn't advance turn
    // -----------------------------------------------------------------------
    private RunesOfReckoningGameState openItemPicker(RunesOfReckoningGameState state) {
        Character active = state.playerTurn() == PlayerNumber.PLAYER_ONE
                ? state.activeOne() : state.activeTwo();
        boolean hasItems = !active.getInventory().getItems().isEmpty();
        String log = hasItems
                ? "Choose an item to use:"
                : active.getName() + " has no items!";
        return state.withLog(log, hasItems);
    }

    // -----------------------------------------------------------------------
    // SWAP TO SPECIFIC INDEX — called directly by BattleScreen picker
    // -----------------------------------------------------------------------
    public RunesOfReckoningGameState swapToIndex(
            PlayerNumber swapper, int targetIndex, RunesOfReckoningGameState state) {

        List<Character> team = swapper == PlayerNumber.PLAYER_ONE
                ? state.playerOne().getCharacters()
                : state.playerTwo().getCharacters();

        int currentIndex = swapper == PlayerNumber.PLAYER_ONE
                ? state.playerOneActiveIndex()
                : state.playerTwoActiveIndex();

        if (targetIndex == currentIndex) {
            return state.withLog("That character is already active!", false);
        }
        if (targetIndex < 0 || targetIndex >= team.size()) {
            return state.withLog("Invalid character index.", false);
        }
        if (team.get(targetIndex).getCurrentHP() <= 0) {
            return state.withLog(team.get(targetIndex).getName() + " is KO'd!", false);
        }

        int p1Idx = swapper == PlayerNumber.PLAYER_ONE ? targetIndex : state.playerOneActiveIndex();
        int p2Idx = swapper == PlayerNumber.PLAYER_TWO ? targetIndex : state.playerTwoActiveIndex();
        String log = team.get(targetIndex).getName() + " enters the battle!";

        RunesOfReckoningGameState withSwap = new RunesOfReckoningGameState(
                state.status(), state.playerTurn(),
                state.playerOne(), state.playerTwo(),
                p1Idx, p2Idx,
                state.playerOneDefending(), state.playerTwoDefending(),
                state.duelPhase(), state.winner(), log, false
        );
        return advanceTurn(swapper, withSwap, log, false);
    }

    // -----------------------------------------------------------------------
    // SWAP CHARACTER (cycle to next alive — used by MOVE_UP command)
    // -----------------------------------------------------------------------
    private RunesOfReckoningGameState handleSwap(PlayerNumber swapper, RunesOfReckoningGameState state) {
        List<Character> team = swapper == PlayerNumber.PLAYER_ONE
                ? state.playerOne().getCharacters()
                : state.playerTwo().getCharacters();

        int currentIndex = swapper == PlayerNumber.PLAYER_ONE
                ? state.playerOneActiveIndex()
                : state.playerTwoActiveIndex();

        int nextIndex = -1;
        for (int i = 1; i <= team.size(); i++) {
            int candidate = (currentIndex + i) % team.size();
            if (team.get(candidate).getCurrentHP() > 0) {
                nextIndex = candidate;
                break;
            }
        }

        if (nextIndex == -1 || nextIndex == currentIndex) {
            return state.withLog("No other characters available to swap!", false);
        }

        int p1Idx = swapper == PlayerNumber.PLAYER_ONE ? nextIndex : state.playerOneActiveIndex();
        int p2Idx = swapper == PlayerNumber.PLAYER_TWO ? nextIndex : state.playerTwoActiveIndex();
        Character swappedIn = team.get(nextIndex);
        String log = swappedIn.getName() + " enters the battle!";

        RunesOfReckoningGameState withSwap = new RunesOfReckoningGameState(
                state.status(), state.playerTurn(),
                state.playerOne(), state.playerTwo(),
                p1Idx, p2Idx,
                state.playerOneDefending(), state.playerTwoDefending(),
                state.duelPhase(), state.winner(), log, false
        );
        return advanceTurn(swapper, withSwap, log, false);
    }

    // -----------------------------------------------------------------------
    // FORFEIT
    // -----------------------------------------------------------------------
    private RunesOfReckoningGameState handleForfeit(PlayerNumber forfeiter, RunesOfReckoningGameState state) {
        PlayerNumber winner = forfeiter == PlayerNumber.PLAYER_ONE
                ? PlayerNumber.PLAYER_TWO : PlayerNumber.PLAYER_ONE;

        String loserName  = forfeiter == PlayerNumber.PLAYER_ONE
                ? state.playerOne().getPlayerName() : state.playerTwo().getPlayerName();
        String winnerName = winner == PlayerNumber.PLAYER_ONE
                ? state.playerOne().getPlayerName() : state.playerTwo().getPlayerName();

        String log = loserName + " forfeits! " + winnerName + " wins!";
        updateProfiles(state, winner);

        return new RunesOfReckoningGameState(
                GameStatus.ENDED, state.playerTurn(),
                state.playerOne(), state.playerTwo(),
                state.playerOneActiveIndex(), state.playerTwoActiveIndex(),
                false, false,
                state.duelPhase(), winner, log, false
        );
    }

    // -----------------------------------------------------------------------
    // KO HANDLING
    // -----------------------------------------------------------------------
    private RunesOfReckoningGameState handleKO(PlayerNumber attacker, RunesOfReckoningGameState state, String attackLog) {
        PlayerNumber defender = attacker == PlayerNumber.PLAYER_ONE
                ? PlayerNumber.PLAYER_TWO : PlayerNumber.PLAYER_ONE;

        if (isTeamWiped(defender, state)) {
            String winnerName = attacker == PlayerNumber.PLAYER_ONE
                    ? state.playerOne().getPlayerName() : state.playerTwo().getPlayerName();
            String log = attackLog + "\n" + winnerName + " wins the duel!";
            updateProfiles(state, attacker);
            return new RunesOfReckoningGameState(
                    GameStatus.ENDED, state.playerTurn(),
                    state.playerOne(), state.playerTwo(),
                    state.playerOneActiveIndex(), state.playerTwoActiveIndex(),
                    false, false, state.duelPhase(), attacker, log, false
            );
        }

        // Auto-swap to next alive character
        List<Character> defTeam = defender == PlayerNumber.PLAYER_ONE
                ? state.playerOne().getCharacters() : state.playerTwo().getCharacters();
        int newIndex = 0;
        for (int i = 0; i < defTeam.size(); i++) {
            if (defTeam.get(i).getCurrentHP() > 0) { newIndex = i; break; }
        }

        int p1Idx = defender == PlayerNumber.PLAYER_ONE ? newIndex : state.playerOneActiveIndex();
        int p2Idx = defender == PlayerNumber.PLAYER_TWO ? newIndex : state.playerTwoActiveIndex();
        String log = attackLog + "\n" + defTeam.get(newIndex).getName() + " steps in!";

        return advanceTurn(attacker, new RunesOfReckoningGameState(
                state.status(), state.playerTurn(),
                state.playerOne(), state.playerTwo(),
                p1Idx, p2Idx,
                state.playerOneDefending(), state.playerTwoDefending(),
                state.duelPhase(), null, log, false
        ), log, false);
    }

    // -----------------------------------------------------------------------
    // HELPERS
    // -----------------------------------------------------------------------
    private boolean isTeamWiped(PlayerNumber player, RunesOfReckoningGameState state) {
        List<Character> team = player == PlayerNumber.PLAYER_ONE
                ? state.playerOne().getCharacters() : state.playerTwo().getCharacters();
        return team.stream().allMatch(c -> c.getCurrentHP() <= 0);
    }

    private RunesOfReckoningGameState advanceTurn(
            PlayerNumber justActed,
            RunesOfReckoningGameState state,
            String log,
            boolean keepDefendFlag) {

        PlayerNumber next = justActed == PlayerNumber.PLAYER_ONE
                ? PlayerNumber.PLAYER_TWO : PlayerNumber.PLAYER_ONE;

        boolean p1Def = justActed == PlayerNumber.PLAYER_ONE
                ? (keepDefendFlag ? state.playerOneDefending() : false)
                : state.playerOneDefending();
        boolean p2Def = justActed == PlayerNumber.PLAYER_TWO
                ? (keepDefendFlag ? state.playerTwoDefending() : false)
                : state.playerTwoDefending();

        // Tick buffs/debuffs on the player whose turn is ENDING (they just acted)
        // This means poison applied this turn won't damage until next turn
        Character justActedChar = justActed == PlayerNumber.PLAYER_ONE
                ? state.activeOne() : state.activeTwo();
        Character nextChar = justActed == PlayerNumber.PLAYER_ONE
                ? state.activeTwo() : state.activeOne();

        // Tick the incoming player's debuffs (poison damages the poisoned player at start of their turn)
        String poisonLog = "";
        Object poisonDmg = nextChar.getAttribute("POISONED_DAMAGE");
        if (poisonDmg instanceof Integer dmg) {
            nextChar.modifyCurrentHP(-dmg);
            poisonLog = "\n" + nextChar.getName() + " takes " + dmg + " poison damage!";

            // Check if poison killed them
            if (nextChar.getCurrentHP() <= 0) {
                nextChar.modifyCurrentHP(-nextChar.getCurrentHP()); // clamp to 0
                RunesOfReckoningGameState withPoison = new RunesOfReckoningGameState(
                        state.status(), next,
                        state.playerOne(), state.playerTwo(),
                        state.playerOneActiveIndex(), state.playerTwoActiveIndex(),
                        p1Def, p2Def,
                        state.duelPhase(), state.winner(), log + poisonLog, false
                );
                return handleKO(justActed, withPoison, log + poisonLog);
            }
        }

        // Tick buff durations on the character who just acted
        justActedChar.updateBuffs();

        String turnMsg = log + poisonLog + "\n"
                + (next == PlayerNumber.PLAYER_ONE ? "Player 1" : "Player 2") + "'s turn.";

        return new RunesOfReckoningGameState(
                state.status(), next,
                state.playerOne(), state.playerTwo(),
                state.playerOneActiveIndex(), state.playerTwoActiveIndex(),
                p1Def, p2Def,
                state.duelPhase(), state.winner(), turnMsg, false
        );
    }

    private void updateProfiles(RunesOfReckoningGameState state, PlayerNumber winner) {
        if (winner == PlayerNumber.PLAYER_ONE) {
            state.playerOne().addDuelWon();
            state.playerTwo().addDuelLost();
        } else {
            state.playerTwo().addDuelWon();
            state.playerOne().addDuelLost();
        }
    }
}