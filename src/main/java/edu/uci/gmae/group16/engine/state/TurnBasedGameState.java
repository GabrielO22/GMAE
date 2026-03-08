package edu.uci.gmae.group16.engine.state;

public interface TurnBasedGameState extends GameState {
    PlayerNumber playerTurn();
}
