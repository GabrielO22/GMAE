package edu.uci.gmae.group16.engine.state;

public interface RealTimeGameState extends GameState {
    int currentTick();
    int FPS();
}
