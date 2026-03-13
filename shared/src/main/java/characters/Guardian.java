package characters;

import java.util.UUID;

public class Guardian implements CharacterType {
    @Override
    public UUID id() {
        return UUID.randomUUID();
    }

    @Override
    public String displayName() {
        return "Guardian";
    }

    @Override
    public int maxHP() {
        return 150;
    }

    @Override
    public int attack() {
        return 15;
    }

    @Override
    public int defence() {
        return 20;
    }

    @Override
    public double speed() {
        return 0.75;
    }
}
