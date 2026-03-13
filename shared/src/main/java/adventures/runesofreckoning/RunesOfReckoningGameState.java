package adventures.runesofreckoning;

import engine.state.DuelPhase;
import engine.state.GameStatus;
import engine.state.PlayerNumber;
import engine.state.TurnBasedGameState;
import profiles.PlayerProfile;
import reuse.Character;

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
