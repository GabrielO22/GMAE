package edu.uci.gmae.group16.engine;

import java.util.*;
public class MiniAdventureRegistry {
    private final Map<String, MiniAdventure> adventures = new LinkedHashMap<>();
    public void register(MiniAdventure adventure) {
        Objects.requireNonNull(adventure, "adventure");
        adventures.put(adventure.id(), adventure);
    }
    public MiniAdventure get(String id) {
        MiniAdventure adv = adventures.get(id);
        if (adv == null)
            throw new IllegalArgumentException("Unknown adventure ID: " + id);
        return adv;
    }
    public List<MiniAdventure> list() {
        return List.copyOf(adventures.values());
    }
}
