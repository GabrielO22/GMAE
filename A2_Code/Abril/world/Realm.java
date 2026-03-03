package world;

import time.TimeRule;

public class Realm implements TimeRule {
    private final String realmId;
    private String realmName;
    private String realmDescription;
    private TimeRule localTimeRule;

    public Realm(String realmId, String realmName, String realmDescription, TimeRule localTimeRule) {
        this.realmId = realmId;
        this.realmName = realmName;
        this.realmDescription = realmDescription;
        this.localTimeRule = localTimeRule;
    }

    //getters
    public String getRealmId() { return realmId; }
    public String getRealmName() { return realmName; }
    public String getRealmDescription() { return realmDescription; }


    @Override
    public int convertWorldToLocal(int worldTime) {
        return localTimeRule.convertWorldToLocal(worldTime);
    }

    @Override
    public String getRuleDescription() {
        return localTimeRule.getRuleDescription();
    }

    // Logic
    public void updateTimeRule(TimeRule newTimeRule) {
        this.localTimeRule = newTimeRule;
    }

    public void updateDescription(String newDescription) {
        this.realmDescription = newDescription;
    }
}