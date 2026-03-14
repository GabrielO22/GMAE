package entity;

import characters.Character;
import engine.GamePanel;
import engine.KeyHandler;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyH;
    boolean isPlayer1;
    public Character myCharacter;
    public java.util.ArrayList<SuperObject> inventory = new java.util.ArrayList<>();


    public Player(GamePanel gamePanel, int startWorldX, int startWorldY, KeyHandler keyH, boolean isPlayer1, Character character) {
        this.gamePanel = gamePanel;
        this.worldX = startWorldX;
        this.worldY = startWorldY;
        this.speed = 4;

        this.keyH = keyH;
        this.isPlayer1 = isPlayer1;
        this.myCharacter = character;
        this.direction = "down";


        // calculate speed based on character stats
        double speedModifier = 1.0;
        if (myCharacter != null && myCharacter.getClassType() != null) {
            speedModifier = myCharacter.getClassType().getSpeed();
        }
        this.speed = (int) (4 * speedModifier);

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

            int objIndex = gamePanel.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            if (!collisionOn) {
                int nextX = worldX;
                int nextY = worldY;

                switch (direction) {
                    case "up": nextY -= speed; break;
                    case "down": nextY += speed; break;
                    case "left": nextX -= speed; break;
                    case "right": nextX += speed; break;
                }

                // Calculate the CENTER of this player's potential next position
                int centerX = nextX + (gamePanel.tileSize / 2);
                int centerY = nextY + (gamePanel.tileSize / 2);

                // Calculate the CENTER of the partner
                Player partner = isPlayer1 ? gamePanel.player2 : gamePanel.player1;
                int partnerCenterX = partner.worldX + (gamePanel.tileSize / 2);
                int partnerCenterY = partner.worldY + (gamePanel.tileSize / 2);

                // Calculate distance between CENTERS
                int distanceX = Math.abs(centerX - partnerCenterX);
                int distanceY = Math.abs(centerY - partnerCenterY);

                // The limit should be the screen size minus one full tile to keep them fully on screen
                int maxDistX = gamePanel.screenWidth - gamePanel.tileSize;
                int maxDistY = gamePanel.screenHeight - gamePanel.tileSize;

                // World boundaries
                boolean insideWorldX = nextX >= 0 && nextX + gamePanel.tileSize <= gamePanel.maxWorldCol * gamePanel.tileSize;
                boolean insideWorldY = nextY >= 0 && nextY + gamePanel.tileSize <= gamePanel.maxWorldRow * gamePanel.tileSize;

                // Move only if within tether and world bounds
                if (distanceX <= maxDistX && distanceY <= maxDistY && insideWorldX && insideWorldY) {
                    worldX = nextX;
                    worldY = nextY;
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

    public void draw(Graphics2D g2, int screenX, int screenY) {
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

        g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            // Add to this specific player's inventory
            inventory.add(gamePanel.obj[i]);

            System.out.println("Player picked up: " + gamePanel.obj[i].name);

            // Remove the object from the map
            gamePanel.obj[i] = null;

            // Tell the GamePanel to check if the game is over
            gamePanel.checkWinCondition();
        }
    }
}