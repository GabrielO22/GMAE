package engine.state;

public interface TurnBasedGameState extends GameState {
    PlayerNumber playerTurn();
}
