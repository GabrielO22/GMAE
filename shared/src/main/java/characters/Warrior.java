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
        return 95;
    }

    @Override
    public int attack() {
        return 28;
    }

    @Override
    public int defence() {
        return 8;
    }

    @Override
    public int speed() {
        return 5;
    }

    @Override
    public String getSpritePath() {
        return "/player/warrior/panda_still.png";
    }
}