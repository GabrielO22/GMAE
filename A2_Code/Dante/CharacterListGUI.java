import javax.swing.*;
import java.awt.*;

public class CharacterListGUI extends JFrame {
    private User user;
    private DefaultListModel<String> charModel;

    public CharacterListGUI(User user) {
        this.user = user;
        setTitle("Character Roster");
        setSize(400, 300);
        setLayout(new BorderLayout());

        charModel = new DefaultListModel<>();
        refreshList();
        JList<String> list = new JList<>(charModel);
        add(new JScrollPane(list), BorderLayout.CENTER);

        JButton addCharBtn = new JButton("Create Character");
        addCharBtn.addActionListener(e -> createNewCharacter());

        add(addCharBtn, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void createNewCharacter() {
        // basic demo example
        user.createCharacter("New Hero", "Warrior");
        refreshList();
    }

    private void refreshList() {
        charModel.clear();
        for (Character c : user.getCharacters()) {
            charModel.addElement(c.getName() + " (Lvl " + c.getLevel() + " " + c.getClazz() + ")");
        }
    }
}