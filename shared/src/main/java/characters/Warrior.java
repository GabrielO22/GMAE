package characters;

import java.util.UUID;

public class Warrior implements CharacterType {
    @Override
    public UUID id() {
        return UUID.randomUUID();
    }

    @Override
    public String displayName() {
        return "Warrior";
    }

    @Override
    public int maxHP() {
        return 90;
    }

    @Override
    public int attack() {
        return 30;
    }

    @Override
    public int defence() {
        return 5;
    }

    @Override
    public double speed() {
        return 1;
    }
}
