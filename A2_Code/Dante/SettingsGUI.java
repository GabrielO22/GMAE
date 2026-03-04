import javax.swing.*;
import java.awt.*;

public class SettingsGUI extends JDialog {
    public SettingsGUI(JFrame parent, Settings settings) {
        super(parent, "App Settings", true);
        setSize(300, 200);
        setLayout(new GridLayout(4, 1));

        // Time Preference Toggle
        add(new JLabel("Time Display:"));
        JComboBox<TimeDisplayPreference> timeBox = new JComboBox<>(TimeDisplayPreference.values());
        timeBox.setSelectedItem(settings.getTimeDisplayPreference());
        add(timeBox);

        // Theme Toggle
        add(new JLabel("Theme:"));
        JComboBox<ThemeType> themeBox = new JComboBox<>(ThemeType.values());
        themeBox.setSelectedItem(settings.getTheme());
        add(themeBox);

        JButton saveBtn = new JButton("Save & Apply");
        saveBtn.addActionListener(e -> saveSettings(settings, timeBox, themeBox));
        add(saveBtn);

        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void saveSettings(Settings settings, JComboBox<TimeDisplayPreference> timeBox,
            JComboBox<ThemeType> themeBox) {
        settings.setTimeDisplayPreference((TimeDisplayPreference) timeBox.getSelectedItem());
        settings.setTheme((ThemeType) themeBox.getSelectedItem());
        dispose();
    }
}