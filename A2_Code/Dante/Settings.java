enum ThemeType {
    CLASSIC,
    MODERN
}

enum TimeDisplayPreference {
    WORLD_ONLY,
    LOCAL_ONLY,
    BOTH
}

class Settings {
    Realm currentRealm;
    ThemeType theme;
    TimeDisplayPreference timeDisplayPreference;

    Settings() {
        this.theme = ThemeType.CLASSIC;
        this.timeDisplayPreference = TimeDisplayPreference.BOTH;
    }

    public Realm getCurrentRealm() {
        return currentRealm;
    }

    public void setCurrentRealm(Realm currentRealm) {
        this.currentRealm = currentRealm;
    }

    public ThemeType getTheme() {
        return theme;
    }

    public void setTheme(ThemeType theme) {
        this.theme = theme;
    }

    public TimeDisplayPreference getTimeDisplayPreference() {
        return timeDisplayPreference;
    }

    public void setTimeDisplayPreference(TimeDisplayPreference newPreference) {
        this.timeDisplayPreference = newPreference;
    }
}