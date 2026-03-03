package main;

import world.Realm;

public class Settings {
    private Realm currentRealm;

    enum Theme {CLASSIC, MODERN};
    private Theme theme = Theme.CLASSIC;

    public enum TimeDisplayMode {WORLD_CLOCK, REALM_LOCAL_CLOCK, BOTH};
    private TimeDisplayMode timeDisplayMode = TimeDisplayMode.WORLD_CLOCK;

    public Settings() {
        // default
    }

    public void setCurrentRealm(Realm realm) { this.currentRealm = realm; }
    public Realm getCurrentRealm() { return currentRealm; }

    public void changeTheme(Theme newTheme) { this.theme = newTheme; }
    public Theme getTheme() { return theme; }

    public void setDisplayPreference(TimeDisplayMode mode) { this.timeDisplayMode = mode; }
    public TimeDisplayMode getDisplayPreference() { return timeDisplayMode; }

}