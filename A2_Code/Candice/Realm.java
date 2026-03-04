package GuildQuest.core;

public class Realm {
    private static int count = 0;

    String realmID;
    String name;
    String description;
    LocalTimeRule localTimeRule;

    public Realm(String name, String description, LocalTimeRule localTimeRule) {
        this.realmID = "realm" + count++;
        this.name = name;
        this.description = description;
        this.localTimeRule = localTimeRule;
    }

    public static Realm createRealm(String name, String description, LocalTimeRule localTimeRule) {
        return new Realm(name, description, localTimeRule);
    }

    public ClockTime convertToLocalTime(ClockTime WorldTime) {
        return localTimeRule.convertToLocalTime(WorldTime);
    }

    public void print() {
        String border = "-".repeat(20);
        String name = "\nREALM: " + this.name;
        String ID = "\nID: " + this.realmID;
        String description = "\nDescription: " + this.description;
        String timeRule = "\nTime Rule: " + this.localTimeRule + "\n";
        String str = border + name + ID + description + timeRule;
        System.out.println(str);
    }

    @Override
    public String toString() {
        return name;
    }
}
