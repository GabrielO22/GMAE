package adventures.runesofreckoning;

import engine.state.DuelPhase;
import engine.state.GameStatus;
import engine.state.PlayerNumber;
import engine.state.TurnBasedGameState;
import profiles.PlayerProfile;
import characters.Character;

public record RunesOfReckoningGameState(
        GameStatus status,
        PlayerNumber playerTurn,
        PlayerProfile playerOne,
        PlayerProfile playerTwo,
        int playerOneActiveIndex,
        int playerTwoActiveIndex,
        boolean playerOneDefending,
        boolean playerTwoDefending,
        DuelPhase duelPhase,
        PlayerNumber winner,
        String lastActionLog,
        boolean pendingItemSelection
) implements TurnBasedGameState {
    public Character activeOne() {
        return playerOne().getCharacters().get(playerOneActiveIndex());
    }

    public Character activeTwo() {
        return playerTwo().getCharacters().get(playerTwoActiveIndex());
    }

    public items.Inventory activeInventory() {
        Character active = playerTurn() == PlayerNumber.PLAYER_ONE ? activeOne() : activeTwo();
        return active.getInventory();
    }

    public static RunesOfReckoningGameState init(PlayerProfile p1, PlayerProfile p2) {
        return new RunesOfReckoningGameState(
                GameStatus.RUNNING,
                PlayerNumber.PLAYER_ONE,
                p1,
                p2,
                0,
                0,
                false,
                false,
                DuelPhase.CHOOSE_ACTION,
                null,
                "Battle begins! Player 1's turn.",
                false
        );
    }

    public RunesOfReckoningGameState withLog(String log, boolean itemPending) {
        return new RunesOfReckoningGameState(
                status, playerTurn, playerOne, playerTwo,
                playerOneActiveIndex, playerTwoActiveIndex,
                playerOneDefending, playerTwoDefending,
                duelPhase, winner, log, itemPending
        );
    }
}