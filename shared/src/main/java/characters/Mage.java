package characters;

import java.util.UUID;

public class Mage implements CharacterType {
    @Override
    public UUID id() { return UUID.randomUUID(); }
    @Override
    public String displayName() { return "Mage"; }
    @Override
    public int maxHP() { return 70; }
    @Override
    public int attack() { return 35; }
    @Override
    public int defence() { return 4; }
    @Override
    public int speed() { return 4; }
}