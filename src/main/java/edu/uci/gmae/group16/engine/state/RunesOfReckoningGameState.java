package edu.uci.gmae.group16.engine.state;

import edu.uci.gmae.group16.profiles.PlayerProfile;

public record RunesOfReckoningGameState(
        GameStatus status,
        PlayerNumber playerTurn,
        PlayerProfile playerOne,
        PlayerProfile playerTwo,
        Character playerOneActiveCharacter,
        Character playerTwoActiveCharacter,
        DuelPhase duelPhase

) implements TurnBasedGameState {
}
