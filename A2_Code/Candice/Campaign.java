package GuildQuest.core;

import java.util.ArrayList;
import java.util.List;

public class Campaign {
    private static int count = 0;

    private String campaignID;
    private String title;
    private Visibility visibility = Visibility.Private;
    private boolean archived = false;
    private List<QuestEvent> questEventList;

    public Campaign(String title) {
        this.campaignID = "campaign" + count++;
        this.title = title;
        questEventList = new ArrayList<>();
    }

    public void rename(String title) {
        this.title = title;
    }

    public void archive() {
        archived = true;
    }

    public void unarchive() {
        archived = false;
    }

    public void changeVisibility(Visibility v) {
        this.visibility = v;
    }

    public void addEvent(String title, Realm realm, ClockTime startTime, ClockTime endTime) {
        questEventList.add(new QuestEvent(title, realm, startTime, endTime));
    }

    public void removeEvent(QuestEvent e) {
        questEventList.remove(e);
    }

    public List<QuestEvent> getEvents() {
        return List.copyOf(questEventList);
    }

    public void print() {
        String border = "-".repeat(20);
        String title = "\nCAMPAIGN: " + this.title;
        String ID = "\nID: " + this.campaignID;
        String visibility = "\nVisibility: " + this.visibility;
        String archived = "\nAcrhived: " + this.archived + "\n";
        String str =  border + title + ID + visibility + archived;
        System.out.println(str);
    }

    @Override
    public String toString() {
        return title;
    }
}
