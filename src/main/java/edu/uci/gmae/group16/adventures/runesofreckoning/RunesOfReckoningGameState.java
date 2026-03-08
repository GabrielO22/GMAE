package edu.uci.gmae.group16.adventures.runesofreckoning;

import edu.uci.gmae.group16.engine.state.DuelPhase;
import edu.uci.gmae.group16.engine.state.GameStatus;
import edu.uci.gmae.group16.engine.state.PlayerNumber;
import edu.uci.gmae.group16.engine.state.TurnBasedGameState;
import edu.uci.gmae.group16.profiles.PlayerProfile;
import edu.uci.gmae.group16.reuse.Character;

public record RunesOfReckoningGameState(
        GameStatus status,
        PlayerNumber playerTurn,
        PlayerProfile playerOne,
        PlayerProfile playerTwo,
        Character playerOneActiveCharacter,
        Character playerTwoActiveCharacter,
        DuelPhase duelPhase

) implements TurnBasedGameState {
    public static RunesOfReckoningGameState init(PlayerProfile p1, PlayerProfile p2) {
        return new RunesOfReckoningGameState(
                GameStatus.RUNNING,
                PlayerNumber.PLAYER_ONE,
                p1,
                p2,
                p1.getCharacters().get(0),
                p2.getCharacters().get(0),
                DuelPhase.CHOOSE_NEW_CHARACTER
        );
    }
}
