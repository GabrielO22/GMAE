package characters;

import java.util.UUID;

public class Cleric implements CharacterType {
    @Override
    public UUID id() { return UUID.randomUUID(); }
    @Override
    public String displayName() { return "Cleric"; }
    @Override
    public int maxHP() { return 115; }
    @Override
    public int attack() { return 18; }
    @Override
    public int defence() { return 12; }
    @Override
    public int speed() { return 4; }

    @Override
    public String getSpritePath() {
        return "/player/cleric/dexter_still.png";
    }
}