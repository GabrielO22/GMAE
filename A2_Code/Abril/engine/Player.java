package engine;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Player {
    GamePanel gamePanel;
    int x, y;
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

    public Player(GamePanel gamePanel, int startX, int startY, KeyHandler keyH, boolean isPlayer1) {
        this.gamePanel = gamePanel;
        this.x = startX;
        this.y = startY;
        this.speed = 4;
        this.keyH = keyH;
        this.isPlayer1 = isPlayer1;
        this.direction = "down";
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
                    case "up": y -= speed; break;
                    case "down": y += speed; break;
                    case "left": x -= speed; break;
                    case "right": x += speed; break;
                }
            }

            spriteCounter ++; //inc until 12 ticks, then trigger animation change
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

        g2.drawImage(image, x, y, tileSize, tileSize, null);
    }
}