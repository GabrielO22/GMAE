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

    public static Button createButton(String text, String options){
        Button btn = new Button(text);
        String normal = "-fx-background-color: #555555; "
                + UIConstants.BORDER_WHITE
                + " -fx-text-fill: white; -fx-font-family: "
                + UIConstants.FONT_FAMILY + "; -fx-padding: 10 20;"
                + options
                + " -fx-cursor: hand;";
        String hover  = "-fx-background-color: " + UIConstants.BTN_HOVER + "; "
                + UIConstants.BORDER_WHITE
                + " -fx-text-fill: " + UIConstants.RETRO_YELLOW + ";"
                + " -fx-font-family: " + UIConstants.FONT_FAMILY + "; -fx-padding: 10 20;"
                + options
                + " -fx-cursor: hand;";

        btn.setStyle(normal);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e  -> btn.setStyle(normal));
        return btn;
    }

    public static Button createButton(String text){
        return createButton(text, "");
    }

    public static void applyTabStyle(Button btn, boolean selected) {
        String bg  = selected ? UIConstants.TAB_SELECTED   : UIConstants.TAB_UNSELECTED;
        String txt = selected ? UIConstants.WHITE           : "#222222";
        btn.setStyle(buildTabStyle(bg, txt));
    }

    public static String buildTabStyle(String bg, String txt) {
        return "-fx-background-color: " + bg + ";"
                + " -fx-text-fill: " + txt + ";"
                + " -fx-font-family: " + UIConstants.FONT_FAMILY + ";"
                + " -fx-font-size: 10px;"
                + " -fx-background-radius: 20;"
                + " -fx-border-radius: 20;"
                + " -fx-padding: 6 14;"
                + " -fx-cursor: hand;";
    }
}
