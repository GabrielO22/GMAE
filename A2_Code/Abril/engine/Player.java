package engine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Player {
    int x, y;
    int speed;
    KeyHandler keyH;
    boolean isPlayer1; // Tells the object which keys to listen to

    // sprite vars
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    // animation vars
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Player(int startX, int startY, KeyHandler keyH, boolean isPlayer1) {
        this.x = startX;
        this.y = startY;
        this.speed = 4;
        this.keyH = keyH;
        this.isPlayer1 = isPlayer1;
        this.direction = "down";
        getPlayerImage();
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
            if (keyH.upPressed) { direction = "up"; y -= speed; isMoving = true; }
            else if (keyH.downPressed) { direction = "down"; y += speed; isMoving = true; }
            else if (keyH.leftPressed) { direction = "left"; x -= speed; isMoving = true; }
            else if (keyH.rightPressed) { direction = "right"; x += speed; isMoving = true; }
        } else {
            if (keyH.upPressed2) { direction = "up"; y -= speed; isMoving = true; }
            else if (keyH.downPressed2) { direction = "down"; y += speed; isMoving = true; }
            else if (keyH.leftPressed2) { direction = "left"; x -= speed; isMoving = true; }
            else if (keyH.rightPressed2) { direction = "right"; x += speed; isMoving = true; }
        }

        if (isMoving) {
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