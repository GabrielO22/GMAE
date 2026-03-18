package characters;

import java.util.UUID;

public class Adventurer implements CharacterType {
    @Override
    public UUID id() {
        return UUID.randomUUID();
    }

    @Override
    public String displayName() {
        return "Adventurer";
    }

    @Override
    public int maxHP() {
        return 85;
    }

    @Override
    public int attack() {
        return 20;
    }

    @Override
    public int defence() {
        return 8;
    }

    @Override
    public int speed() {
        return 6;
    }

    @Override
    public String getSpritePath() {
        return "/player/adventurer/hamtaro_still.png";
    }
}
