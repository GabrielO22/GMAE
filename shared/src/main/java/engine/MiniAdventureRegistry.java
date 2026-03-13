package engine;

import java.util.*;
public class MiniAdventureRegistry {
    private static final Map<UUID, MiniAdventure> adventures = new LinkedHashMap<>();
    public static void register(MiniAdventure adventure) {
        Objects.requireNonNull(adventure, "adventure cannot be null");
        adventures.put(adventure.getID(), adventure);
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
