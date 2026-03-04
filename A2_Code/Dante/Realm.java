import java.util.UUID;

class Realm {
    UUID realmID;
    String name;
    String description;
    MapIdentity mapIdentity;
    LocalTimeRule localTimeRule;

    public Realm(String name, double x, double y) {
        this.realmID = UUID.randomUUID();
        this.name = name;
        this.mapIdentity = new MapIdentity(x, y);
    }

    public UUID getRealmID() {
        return realmID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MapIdentity getMapIdentity() {
        return mapIdentity;
    }

    public void setMapIdentity(MapIdentity mapIdentity) {
        this.mapIdentity = mapIdentity;
    }

    public void setTimeRule(LocalTimeRule localTimeRule) {
        this.localTimeRule = localTimeRule;
    }

    public WorldClockTime getLocalTime(WorldClockTime worldTime) {
        return localTimeRule.toLocal(worldTime);
    }
}
