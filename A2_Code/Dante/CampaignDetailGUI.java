import javax.swing.*;
import java.awt.*;

public class CampaignDetailGUI extends JFrame implements CampaignUpdateListener {
    private Campaign campaign;
    private Settings userSettings;
    private DefaultListModel<String> eventListModel;
    private JList<String> eventDisplayList;

    public CampaignDetailGUI(Campaign campaign, Settings userSettings) {
        this.campaign = campaign;
        this.userSettings = userSettings;
        setTitle("Editing: " + campaign.getName());
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Event List
        eventListModel = new DefaultListModel<>();
        refreshEventList();
        eventDisplayList = new JList<>(eventListModel);
        add(new JScrollPane(eventDisplayList), BorderLayout.CENTER);

        // Control Panel (Add/Remove)
        JPanel controls = new JPanel();
        JButton addEventBtn = new JButton("Add Quest Event");
        JButton deleteEventBtn = new JButton("Delete Event");

        addEventBtn.addActionListener(e -> addNewEvent(userSettings));

        controls.add(addEventBtn);
        controls.add(deleteEventBtn);
        add(controls, BorderLayout.SOUTH);

        setVisible(true);
        campaign.addListener(this);
    }

    private void addNewEvent(Settings userSettings) {
        // Simplified example due to time constraint
        String title = JOptionPane.showInputDialog("Event Title:");
        if (title != null) {
            // a basic World Time and a dummy Realm for logic check
            WorldClockTime start = WorldClock.getInstance().getCurrentTime();
            WorldClockTime end = new WorldClockTime(start.toTotalMinutes() + 240);
            Realm defaultRealm = new Realm("Starting Village", 0, 0);
            defaultRealm.setTimeRule(new FixedOffsetRule(20));

            QuestEvent newEvent = new QuestEvent(title, start, end, defaultRealm);
            campaign.addEvent(newEvent);
        }
    }

    private void refreshEventList() {
        eventListModel.clear();
        for (QuestEvent qe : campaign.getEvents()) {
            String timeStr = formatTimeDisplay(qe);
            eventListModel.addElement(qe.getTitle() + " | " + timeStr);
        }
    }

    private String formatTimeDisplay(QuestEvent event) {
        WorldClockTime world = event.getStartTime();
        WorldClockTime local = event.getRealm().getLocalTime(world);

        switch (this.userSettings.getTimeDisplayPreference()) {
            case LOCAL_ONLY:
                return "Local: " + local;
            case WORLD_ONLY:
                return "World: " + world;
            default:
                return "W: " + world + " / L: " + local;
        }
    }

    @Override
    public void onChange() {
        refreshEventList();
    }
}