package engine;

import java.util.*;
public class MiniAdventureRegistry {
    private final Map<UUID, MiniAdventure> adventures = new LinkedHashMap<>();
    public void register(MiniAdventure adventure) {
        Objects.requireNonNull(adventure, "adventure");
        adventures.put(adventure.id(), adventure);
    }
    public MiniAdventure get(UUID id) {
        MiniAdventure adv = adventures.get(id);
        if (adv == null)
            throw new IllegalArgumentException("Unknown adventure ID: " + id);
        return adv;
    }
    public List<MiniAdventure> list() {
        return List.copyOf(adventures.values());
    }
}
