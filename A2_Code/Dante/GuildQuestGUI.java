import javax.swing.*;
import java.awt.*;

class GuildQuestGUI extends JFrame {
    private User currentUser;
    private DefaultListModel<String> campaignListModel;
    private JList<String> campaignDisplayList;

    public GuildQuestGUI(User user) {
        this.currentUser = user;

        // Setup Window Properties
        setTitle("GuildQuest - " + user.getDisplayName());
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        add(createHeaderPanel(), BorderLayout.NORTH);

        // Campaign List Panel
        campaignListModel = new DefaultListModel<>();
        refreshCampaignList();
        campaignDisplayList = new JList<>(campaignListModel);

        JScrollPane scrollPane = new JScrollPane(campaignDisplayList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Your Campaigns"));
        add(scrollPane, BorderLayout.CENTER);

        // Action Buttons
        JPanel footer = new JPanel();
        JButton addBtn = new JButton("New Campaign");
        JButton viewBtn = new JButton("View Details");
        JButton charBtn = new JButton("Manage Characters");

        // Action Logic
        addBtn.addActionListener(e -> addNewCampaign());
        viewBtn.addActionListener(e -> showRelatedEvents());
        charBtn.addActionListener(e -> showCharacters());

        footer.add(addBtn);
        footer.add(viewBtn);
        footer.add(charBtn);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.LIGHT_GRAY);

        String time = WorldClock.getInstance().getCurrentTime().toString();
        JLabel welcomeLabel = new JLabel(" Welcome, " + currentUser.getDisplayName() + " | " + time);

        JButton settingsBtn = new JButton("Settings");
        settingsBtn.addActionListener(e -> openSettingGUI());

        header.add(welcomeLabel, BorderLayout.WEST);
        header.add(settingsBtn, BorderLayout.EAST);
        return header;
    }

    private void openSettingGUI() {
        new SettingsGUI(this, currentUser.getSettings());
        refreshCampaignList();
    }

    private void addNewCampaign() {
        String name = JOptionPane.showInputDialog("Enter Campaign Name:");
        if (name != null && !name.isEmpty()) {
            currentUser.createCampaign(name);
            refreshCampaignList();
        }
    }

    private void showRelatedEvents() {
        int index = campaignDisplayList.getSelectedIndex();
        if (index != -1) {
            Campaign selected = currentUser.getCampaigns().get(index);
            new CampaignDetailGUI(selected, currentUser.getSettings());
        } else
            JOptionPane.showMessageDialog(this, "Please select a campaign first!");

    }

    private void showCharacters() {
        new CharacterListGUI(currentUser);
    }

    private void applyTheme() {
        if (campaignDisplayList == null)
            return;

        if (currentUser.getSettings().getTheme() == ThemeType.MODERN) {
            campaignDisplayList.setBackground(Color.DARK_GRAY);
            campaignDisplayList.setForeground(Color.CYAN);
        } else {
            campaignDisplayList.setBackground(Color.WHITE);
            campaignDisplayList.setForeground(Color.BLACK);
        }
    }

    private void refreshCampaignList() {
        applyTheme();
        campaignListModel.clear();
        for (Campaign c : currentUser.getCampaigns()) {
            campaignListModel.addElement(c.getName() + (c.isArchived() ? " [Archived]" : ""));
        }
    }
}
