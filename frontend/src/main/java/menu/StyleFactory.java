package menu;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class StyleFactory {
    public static String createBackground(String color){
        return "-fx-background-color: " + color + ";";
    }

    public static String createTextStyle(int sz, String color)
    {
        return "-fx-font-family: " + UIConstants.FONT_FAMILY + "; -fx-font-size: " + sz
                + "px; -fx-text-fill: " + color + "; ";
    }

    public static Label createLabel(String text, int sz, String color, String options) {
        Label label = new Label(text);
        label.setStyle(createTextStyle(sz, color)+ options + ";");
        return label;
    }

    public static Label createLabel(String text, int sz, String color) {
        return createLabel(text, sz, color, "");
    }

    public static Button createButton(String text){
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: #555555; " + UIConstants.BORDER_WHITE
                + " -fx-text-fill: white; -fx-font-family: " + UIConstants.FONT_FAMILY + ";");
        return btn;
    }
}
