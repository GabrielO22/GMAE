package object;

import engine.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public String backendItemId;
    public boolean collision = false;
    public int worldX,  worldY;

    // object hitbox (sim to player hitbox)
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // Fills the whole tile by default
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;


    public void draw(Graphics2D g2, GamePanel gamePanel) {
        int screenX = worldX - gamePanel.getCameraX();
        int screenY = worldY - gamePanel.getCameraY();

        // only draw tile if it is visible within the screen + a little extra
        if (worldX + gamePanel.tileSize > gamePanel.getCameraX() &&
                worldX - gamePanel.tileSize < gamePanel.getCameraX() + gamePanel.screenWidth &&
                worldY + gamePanel.tileSize > gamePanel.getCameraY() &&
                worldY - gamePanel.tileSize < gamePanel.getCameraY() + gamePanel.screenHeight) {

            g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }

}
