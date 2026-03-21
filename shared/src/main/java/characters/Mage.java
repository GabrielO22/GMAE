package characters;

import java.util.UUID;

public class Mage implements CharacterType {
    @Override
    public UUID id() { return UUID.randomUUID(); }
    @Override
    public String displayName() { return "Mage"; }
    @Override
    public int maxHP() { return 75; }
    @Override
    public int attack() { return 30; }
    @Override
    public int defence() { return 5; }
    @Override
    public int speed() { return 5; }

    @Override
    public String getSpritePath() {
        return "/player/mage/bijou_still.png";
    }
}