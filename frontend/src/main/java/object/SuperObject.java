package object;

import engine.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

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


    public void loadImage(GamePanel gamePanel) {
        // Automatically converts "Health Potion" to "/objects/health_potion.png"
        String formattedName = name.toLowerCase().replace(" ", "_");
        String imagePath = "/objects/" + formattedName + ".png";

        try {
            // Load the raw original Stardew image
            BufferedImage original = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));

            // Pre-scale it to perfectly match the game's tileSize (e.g., 48x48)
            image = new BufferedImage(gamePanel.tileSize, gamePanel.tileSize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = image.createGraphics();
            g2.drawImage(original, 0, 0, gamePanel.tileSize, gamePanel.tileSize, null);
            g2.dispose();

        } catch (Exception e) {
            System.err.println("CRASH: Could not find object image at -> " + imagePath);
            // can optionally load a purple/black "missing texture" square here as a failsafe
        }
    }

    public void draw(Graphics2D g2, GamePanel gamePanel) {
        int screenX = worldX - gamePanel.getCameraX();
        int screenY = worldY - gamePanel.getCameraY();

        // only draw tile if it is visible within the screen + a little extra
        if (worldX + gamePanel.tileSize > gamePanel.getCameraX() &&
                worldX - gamePanel.tileSize < gamePanel.getCameraX() + gamePanel.screenWidth &&
                worldY + gamePanel.tileSize > gamePanel.getCameraY() &&
                worldY - gamePanel.tileSize < gamePanel.getCameraY() + gamePanel.screenHeight) {

            // Draw the pre-scaled image
            if (image != null) {
                g2.drawImage(image, screenX, screenY, null); // width/height removed since it's pre-scaled
            }
        }
    }

}
