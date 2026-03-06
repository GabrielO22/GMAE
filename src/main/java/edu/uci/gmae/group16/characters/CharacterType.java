package edu.uci.gmae.group16.profiles;

import java.util.*;

public interface CharacterClass {
    UUID id();
    String className();

    int maxHP();
    int attack();
    int defence();
    double speed();

    String getClassName();
    int getMaxHP();
    int getAttack();
    int getDefence();
    double getSpeed();

}
