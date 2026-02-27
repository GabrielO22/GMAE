public class Item {
    private String name;
    private String rarity;
    private String description;

    public Item(String name, String rarity, String description) {
        this.name = name;
        this.rarity = rarity;
        this.description = description;
    }

    public Item(String name, String rarity) {
        this.name = name;
        this.rarity = rarity;
        this.description = null;
    }

    public String getName() {
        return name;
    }
    public String getRarity() {
        return rarity;
    }
    public String getDescription() {
        return description;
    }
}
