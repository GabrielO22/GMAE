package engine;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Player {
    GamePanel gamePanel;
    public int worldX, worldY; // Where they are on the 100x100 map
    public int screenX, screenY; // Where they are on the 16x12 screen/ monitor
    int speed;
    KeyHandler keyH;
    boolean isPlayer1; // Tells the object which keys to listen to

    // sprite vars
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    // collision
    public Rectangle solidArea;
    public boolean collisionOn = false;

    // animation vars
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Player(GamePanel gamePanel, int startWorldX, int startWorldY, KeyHandler keyH, boolean isPlayer1) {
        this.gamePanel = gamePanel;
        this.worldX = startWorldX; // true coordinates
        this.worldY = startWorldY;
        this.speed = 4;
        this.keyH = keyH;
        this.isPlayer1 = isPlayer1;
        this.direction = "down";

        // --- CAMERA SETUP ---
        if (isPlayer1) {
            // Player 1 is the camera anchor. Lock them to the center of the screen!
            screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
            screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);
        } else {
            // Player 2's screen position will be calculated dynamically in the draw loop
            screenX = 0;
            screenY = 0;
        }

        getPlayerImage();

        // define collision area
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/hamtaro_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/hamtaro_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/hamtaro_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/hamtaro_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/hamtaro_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/hamtaro_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/hamtaro_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/hamtaro_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        boolean isMoving = false;

        if (isPlayer1) {
            if (keyH.upPressed) { direction = "up"; isMoving = true; }
            else if (keyH.downPressed) { direction = "down"; isMoving = true; }
            else if (keyH.leftPressed) { direction = "left"; isMoving = true; }
            else if (keyH.rightPressed) { direction = "right"; isMoving = true; }
        } else {
            if (keyH.upPressed2) { direction = "up"; isMoving = true; }
            else if (keyH.downPressed2) { direction = "down"; isMoving = true; }
            else if (keyH.leftPressed2) { direction = "left"; isMoving = true; }
            else if (keyH.rightPressed2) { direction = "right"; isMoving = true; }
        }

        if (isMoving) {
            // check collision
            collisionOn = false;
            gamePanel.cChecker.checkTile(this);

            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            spriteCounter ++; //increment until 12 ticks, then trigger animation change
            if (spriteCounter >= 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }



    public void draw(Graphics2D g2, int tileSize) {
        BufferedImage image = null;

        if (!isPlayer1) {
            screenX = worldX - gamePanel.player1.worldX + gamePanel.player1.screenX;
            screenY = worldY - gamePanel.player1.worldY + gamePanel.player1.screenY;
        }

        switch (direction) {
            case "up":
                if (spriteNum == 1) {image = up1;}
                if (spriteNum == 2) {image = up2;}
                break;
            case "down":
                if (spriteNum == 1) {image = down1;}
                if (spriteNum == 2) {image = down2;}
                break;
            case "left":
                if (spriteNum == 1) {image = left1;}
                if (spriteNum == 2) {image = left2;}
                break;
            case "right":
                if (spriteNum == 1) {image = right1;}
                if (spriteNum == 2) {image = right2;}
                break;
        }

        g2.drawImage(image, screenX, screenY, tileSize, tileSize, null);
    }
}