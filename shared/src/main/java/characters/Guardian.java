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
        return 130;
    }

    @Override
    public int attack() {
        return 16;
    }

    @Override
    public int defence() {
        return 16;
    }

    @Override
    public int speed() { return 3;}

    @Override
    public String getSpritePath() {
        return "/player/guardian/boss_still.png";
    }
}