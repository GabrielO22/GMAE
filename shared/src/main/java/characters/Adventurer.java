package characters;

import java.util.UUID;

public class Adventurer implements CharacterType {
    public UUID id() {
        return UUID.randomUUID();
    }

    public String displayName() {
        return "Adventurer";
    }

    public int maxHP() {
        return 85;
    }
    public int attack() {
        return 20;
    }
    public int defence() {
        return 8;
    }
    public double speed() {
        return 1.5;
    }
}
