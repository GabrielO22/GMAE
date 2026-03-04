package GuildQuest.core;

import java.util.List;
import java.util.ArrayList;

public class QuestEvent {
    private static int count = 0;

    String eventID;
    String title;
    ClockTime start;
    ClockTime end;
    List<Character> characterList;
    Realm realm;

    public QuestEvent(String title, Realm realm, ClockTime start, ClockTime end) {
        this.eventID = "event" + count++;
        this.title = title;
        this.realm = realm;
        this.start = start;
        this.end = end;
        characterList = new ArrayList<>();
    }

    public QuestEvent(String title, Realm realm, ClockTime start) {
        this(title, realm, start, null);
    }

    public void rename(String newTitle) {
        this.title = newTitle;
    }

    public void updateTime(ClockTime startTime, ClockTime endTime) {
        this.start = startTime;
        this.end = endTime;
    }

    public void updateTime(ClockTime startTime) {
        this.start = startTime;
        this.end = null;
    }

    public void updateRealm(Realm r) {
        this.realm = r;
    }

    public Character addCharacter(Character c) {
        characterList.add(c);
        return c;
    }

    public void removeCharacter(Character c) {
        characterList.remove(c);
    }

    public List<Character> getCharacters() {
        return List.copyOf(characterList);
    }

    public void print() {
        String border = "-".repeat(20);
        String title = "\nEVENT: " + this.title;
        String ID = "\nID: " + this.eventID;
        String realm = "\nRealm: " + this.realm;
        String startTime = "\nStart Time: " + this.start;
        String endTime = "\nEnd Time: " + this.end + "\n";
        String str = border + title + ID + realm + startTime + endTime;
        System.out.println(str);
    }

    @Override
    public String toString() {
        return title;
    }
}
