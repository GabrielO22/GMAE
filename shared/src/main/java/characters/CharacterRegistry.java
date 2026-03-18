package characters;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CharacterRegistry {
    // Stores our base archetypes (Blueprints)
    private static final Map<String, CharacterType> registry = new HashMap<>();

    // Called once when the Engine starts (inside Setup.init())
    public static void register() {
        registerType(new Adventurer());
        registerType(new Warrior());
        registerType(new Guardian());

        registerType(new Mage());
        registerType(new Cleric());
        registerType(new Rogue());

        System.out.println("Character Registry initialized with " + registry.size() + " classes.");
    }

    private static void registerType(CharacterType type) {
        // Use the display name as the lookup key
        registry.put(type.getDisplayName().toUpperCase(), type);
    }

    // UI HELPER METHODS

    // Returns all blueprints so the JavaFX UI can display stat cards
    public static Collection<CharacterType> getAllClasses() {
        return registry.values();
    }

    // Returns a specific blueprint if the UI wants to look up a single class
    public static CharacterType getClassInfo(String className) {
        return registry.get(className.toUpperCase());
    }

    // FACTORY METHOD

    // Creates a brand new, battle-ready Character instance
    public static Character createCharacter(String customName, String className) {
        CharacterType blueprint = registry.get(className.toUpperCase());

        if (blueprint == null) {
            System.err.println("Error: Class '" + className + "' not found! Defaulting to Adventurer.");
            blueprint = registry.get("ADVENTURER");
        }

        Character newChar = new Character();
        newChar.setName(customName);
        newChar.setClassType(blueprint);

        return newChar;
    }
}