import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== INITIALIZING GUILDQUEST SYSTEM ===\n");

        // ---------------------------------------------------------
        // 1. SETUP: World Clock & Users
        // ---------------------------------------------------------
        WorldClock clock = WorldClock.getInstance();
        User player1 = new User("U001", "DragonSlayer", "hero@guildquest.com");
        User player2 = new User("U002", "MageWeaver", "mage@guildquest.com"); // Added second user for sharing

        System.out.println("Users Created: " + player1.getUsername() + " & " + player2.getUsername());

        // ---------------------------------------------------------
        // 2. SETUP: Realms & Time Rules
        // ---------------------------------------------------------
        TimeRule standardTime = new TimeRule() {
            @Override
            public int convertWorldToLocal(int worldTime) { return worldTime; }
            @Override
            public String getRuleDescription() { return "Standard World Time"; }
        };

        TimeRule volcanicTime = new TimeRule() {
            @Override
            public int convertWorldToLocal(int worldTime) { return worldTime + (5 * 60); } // +300 minutes
            @Override
            public String getRuleDescription() { return "Volcanic Time (UTC+5)"; }
        };

        Realm earthRealm = new Realm("R1", "Green Earth", "Starting Zone", standardTime);
        Realm fireRealm = new Realm("R2", "Fire Realm", "Endgame Zone", volcanicTime);

        // ---------------------------------------------------------
        // 3. SETUP: Items & Factory
        // ---------------------------------------------------------
        System.out.println("\n=== INITIALIZING ITEM FACTORY ===");
        ItemFactory.initializeRegistry(); // Setup the item registry

        // Spawn items using the factory pattern instead of manual instantiation
        Item healthPotion = ItemFactory.createItem("pot_minor_hp");
        Item goldCoin = ItemFactory.createItem("misc_gold_coin");

        // We can still create unique custom items manually if we wish
        Item magicSword = new Item("W1", "Flame Blade", "A sword of fire", Item.ItemType.WEAPON, Item.ItemRarity.RARE);

        // ---------------------------------------------------------
        // 4. SETUP: Characters & Leveling
        // ---------------------------------------------------------
        System.out.println("\n=== CREATING CHARACTERS ===");
        Character hero = new Character("C1", "Arthur", "Warrior", player1.getUserId());
        player1.addCharacter(hero);

        System.out.println(hero.getName() + " created at Level " + hero.getLevel());

        // Test Leveling and Class changing
        hero.levelUp();
        hero.changeClass("Paladin");
        System.out.println(hero.getName() + " evolved into a Level " + hero.getLevel() + " Paladin!");

        // ---------------------------------------------------------
        // 5. SETUP: Quests & Loot
        // ---------------------------------------------------------
        System.out.println("\n=== SETTING UP QUESTS ===");
        QuestEvent training = new QuestEvent("Q1", "Basic Training", "Learn to fight",
                WorldClock.MINUTES_PER_DAY / 2, earthRealm); // Day 1, Noon

        QuestEvent bossFight = new QuestEvent("Q2", "Magma Dragon", "Slay the beast",
                WorldClock.MINUTES_PER_DAY + (WorldClock.MINUTES_PER_DAY / 2), fireRealm); // Day 2, Noon

        // Add participants
        bossFight.addParticipant(hero);

        // Add loot via the factory items we spawned
        bossFight.addLootItem(healthPotion, 5);
        bossFight.addLootItem(goldCoin, 100);
        bossFight.addLootItem(magicSword, 1);

        List<QuestEvent> eventsToView = new ArrayList<>();
        eventsToView.add(training);
        eventsToView.add(bossFight);

        // ---------------------------------------------------------
        // 6. SETUP: Campaign & Sharing Logic
        // ---------------------------------------------------------
        System.out.println("\n=== CAMPAIGN & SHARING ===");
        Campaign epicCampaign = new Campaign("Camp1", "The Dragon's Lair", player1);

        epicCampaign.addQuestEvent(training);
        epicCampaign.addQuestEvent(bossFight);
        player1.addCampaign(epicCampaign);

        System.out.println("Sharing campaign with " + player2.getUsername() + "...");
        epicCampaign.shareCampaign(player2, PermissionLevel.COLLABORATIVE);
        System.out.println("Successfully granted " + PermissionLevel.COLLABORATIVE + " permissions to " + player2.getUsername());

        // ---------------------------------------------------------
        // 7. TEST: Timelines & Views
        // ---------------------------------------------------------
        System.out.println("\n=== TEST 1: Day View (World Time) ===");
        clock.advanceTime(360); // Advance clock 6 hours

        TimelineView dayView = new DayView();
        dayView.display(eventsToView, player1, clock.getCurrentTime());
        // Should only show Training (Day 1)

        System.out.println("\n=== TEST 2: Month View (Seeing Future Events) ===");
        TimelineView monthView = new MonthView();
        monthView.display(eventsToView, player1, clock.getCurrentTime());

        System.out.println("\n=== TEST 3: Switching User Preference to LOCAL REALM TIME ===");
        player1.getSettings().setDisplayPreference(Settings.TimeDisplayMode.REALM_LOCAL_CLOCK);
        monthView.display(eventsToView, player1, clock.getCurrentTime());
        // Observe: The "Magma Dragon" time should shift by +5 hours in the printout

        // ---------------------------------------------------------
        // 8. TEST: State Pattern & Loot Distribution
        // ---------------------------------------------------------
        System.out.println("\n=== TEST 4: Quest State & Loot Distribution ===");
        System.out.println("Hero's Inventory Before:");
        hero.getInventory().listAllItems();

        System.out.println("\nStarting the boss fight...");
        bossFight.startQuest(); // Safely transitions from NotStarted to InProgress

        System.out.println("\nCompleting the boss fight...");
        bossFight.completeQuest(); // Automatically distributes loot exactly once & transitions to CompletedState

        System.out.println("\nHero's Inventory After:");
        hero.getInventory().listAllItems();
    }
}