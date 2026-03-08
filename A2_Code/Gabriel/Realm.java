import java.util.*;

public class Realm {
    private final String name;
    private String description = null;
    private final UUID id = UUID.randomUUID();
    private Integer xCoordinate = null;
    private Integer yCoordinate = null;
    private static final Set<String> existingNames = new HashSet<>();

    public Realm(String name) {
        checkDuplicate(name);
        this.name = name;
    }

    public Realm(String name, String description) {
        checkDuplicate(name);
        this.name = name;
        this.description = description;
    }

    public Realm(String name, int xCoordinate, int yCoordinate) {
        checkDuplicate(name);
        this.name = name;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public Realm(String name, String description, int xCoordinate, int yCoordinate) {
        checkDuplicate(name);
        this.name = name;
        this.description = description;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public UUID getID() {
        return id;
    }

    private static String normalize(String name) {
        return name.trim().toLowerCase();
    }

    private static void checkDuplicate(String name) {
        String key = normalize(name);
        if (existingNames.contains(key)) {
            throw new IllegalStateException("Realm name already exists: " + name);
        }
        existingNames.add(key);
    }

    public String getTitle() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public Integer getXCoordinate() {
        return xCoordinate;
    }
    public Integer getYCoordinate() {
        return yCoordinate;
    }
}
