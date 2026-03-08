package edu.uci.gmae.group16.engine;

import edu.uci.gmae.group16.reuse.Realm;

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

}
