import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Initialize Logic
        User player = new User("Adventurer-01");
        player.createCampaign("The HoneyWood");
        player.createCampaign("Mystery of the Void");

        // Use the Singleton to set the starting time
        WorldClock clock = WorldClock.getInstance();
        System.out.println("The journey begins at: " + clock.getCurrentTime());

        // Simulate time passing (e.g., 2 hours later)
        clock.advanceTime(120);
        System.out.println("Time after trekking: " + clock.getCurrentTime());

        // Launch GUI
        SwingUtilities.invokeLater(() -> new GuildQuestGUI(player));
    }
}
