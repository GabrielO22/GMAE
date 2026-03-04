import java.util.UUID;

class MapIdentity {
    UUID mapID;
    double x;
    double y;

    MapIdentity(double x, double y) {
        this.mapID = UUID.randomUUID();
        this.x = x;
        this.y = y;
    }
}
