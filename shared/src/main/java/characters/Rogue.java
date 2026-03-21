package characters;

import java.util.UUID;

public class Rogue implements CharacterType {
    @Override
    public UUID id() { return UUID.randomUUID(); }
    @Override
    public String displayName() { return "Rogue"; }
    @Override
    public int maxHP() { return 80; }
    @Override
    public int attack() { return 24; }
    @Override
    public int defence() { return 6; }
    @Override
    public int speed() { return 8; }

    @Override
    public String getSpritePath() {
        return "/player/rogue/jingle_still.png";
    }
}