package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

// This is the blueprint for ANYTHING that physically exists on the map.
public class Entity {
    public int worldX, worldY;
    public int speed;

    // Sprite variables
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;

    // Collision variables
    public Rectangle solidArea;
    public boolean collisionOn = false;
}