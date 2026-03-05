package engine;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player {
    int x, y;
    int speed;
    Color color;
    KeyHandler keyH;
    boolean isPlayer1; // Tells the object which keys to listen to

    public Player(int startX, int startY, Color color, KeyHandler keyH, boolean isPlayer1) {
        this.x = startX;
        this.y = startY;
        this.speed = 4;
        this.color = color;
        this.keyH = keyH;
        this.isPlayer1 = isPlayer1;
    }

    public void update() {
        if (isPlayer1) {
            if (keyH.upPressed) y -= speed;
            if (keyH.downPressed) y += speed;
            if (keyH.leftPressed) x -= speed;
            if (keyH.rightPressed) x += speed;
        } else {
            if (keyH.upPressed2) y -= speed;
            if (keyH.downPressed2) y += speed;
            if (keyH.leftPressed2) x -= speed;
            if (keyH.rightPressed2) x += speed;
        }
    }

    public void draw(Graphics2D g2, int tileSize) {
        g2.setColor(color);
        g2.fillRect(x, y, tileSize, tileSize);
    }
}