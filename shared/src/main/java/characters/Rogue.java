package characters;

import java.util.UUID;

public class Rogue implements CharacterType {
    @Override
    public UUID id() { return UUID.randomUUID(); }
    @Override
    public String displayName() { return "Rogue"; }
    @Override
    public int maxHP() { return 75; }
    @Override
    public int attack() { return 25; }
    @Override
    public int defence() { return 5; }
    @Override
    public int speed() { return 7; } // Fastest character!
}