package engine;

import reuse.Realm;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RealmRegistry {
    private static final Map<UUID, Realm> realms = new LinkedHashMap<>();

    public static void register(Realm realm) {
        Objects.requireNonNull(realm, "Realm cannot be null");
        realms.put(realm.getID(), realm);
    }

    public static Realm getRandom() {
        if (realms.isEmpty()) {
            throw new IllegalStateException("No realms registered! Add realms in Setup.init().");
        }
        List<Realm> values = new ArrayList<>(realms.values());
        return values.get(ThreadLocalRandom.current().nextInt(values.size()));
    }

    public static List<Realm> list() {
        return List.copyOf(realms.values());
    }
}

/*package engine;

import reuse.Realm;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RealmRegistry {
    private static final List<Realm> registry = new LinkedList<>();

    public static Realm getRandom() {
        return registry.get(ThreadLocalRandom.current().nextInt(0, registry.size()));
    }
    public static void addRealm(Realm realm) {
        registry.add(realm);
    }
    public static Realm getRealm(String realmName) {
        for (Realm realm : registry) {
            if (realm.getName().equals(realmName)) {
                return realm;
            }
        }
        return null;
    }
    public static List<Realm> getRegistry() {
        return registry;
    }

}*/
