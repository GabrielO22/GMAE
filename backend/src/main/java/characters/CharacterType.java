package characters;

import java.util.UUID;

public interface CharacterType {
    UUID id();
    String displayName();

    int maxHP();    // standard: 100
    int attack();   // standard: 20
    int defence();  // standard: 10
    double speed(); // standard: 1 (tile/sec)

    default String getDisplayName() {
        return displayName();
    }
    default int getMaxHP() {
        return maxHP();
    }
    default int getAttack() {
        return attack();
    }
    default int getDefence() {
        return defence();
    }
    default double getSpeed() {
        return speed();
    }

}
