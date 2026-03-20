package entity;

import characters.Character;
import engine.GamePanel;
import engine.KeyHandler;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
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

        this.keyH = keyH;
        this.isPlayer1 = isPlayer1;
        this.myCharacter = character;
        this.direction = "down";


        // calculate speed based on character stats
        if (myCharacter != null) {
            this.speed = myCharacter.getSpeed();
        } else {
            this.speed = 4; // Fallback failsafe
        }

        getPlayerImage();

        // define collision area
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 30;
    }

    public void getPlayerImage() {
        // Get the correct folder and prefix based on the drafted character using helper func
        String basePath = getSpriteBasePath();

        // Dynamically load/scale the directional sprites by appending the suffixes
        up1 = loadAndScale(basePath + "_up_1.png");
        up2 = loadAndScale(basePath + "_up_2.png");
        down1 = loadAndScale(basePath + "_down_1.png");
        down2 = loadAndScale(basePath + "_down_2.png");
        left1 = loadAndScale(basePath + "_left_1.png");
        left2 = loadAndScale(basePath + "_left_2.png");
        right1 = loadAndScale(basePath + "_right_1.png");
        right2 = loadAndScale(basePath + "_right_2.png");

        //still = loadAndScale(basePath + "_still.png");
    }

    private String getSpriteBasePath() {
        if (myCharacter == null || myCharacter.getClassType() == null) {
            return "/player/adventurer/hamtaro"; // Failsafe default
        }

        String className = myCharacter.getClassType().getDisplayName();

        return switch (className) {
            case "Cleric" -> "/player/cleric/dexter";
            case "Guardian" -> "/player/guardian/boss";
            case "Mage" -> "/player/mage/bijou";
            case "Rogue" -> "/player/rogue/jingle";
            case "Warrior" -> "/player/warrior/panda";
            default -> "/player/adventurer/hamtaro";
        };
    }

    private BufferedImage loadAndScale(String imagePath) {
        BufferedImage scaledImage = null;
        try {
            // Load the raw, original mage
            BufferedImage original = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));

            // Create a new blank canvas strictly locked to our tile size
            scaledImage = new BufferedImage(gamePanel.tileSize, gamePanel.tileSize, BufferedImage.TYPE_INT_ARGB);

            // Draw the original image onto the new canvas, forcing it to fit
            Graphics2D g2 = scaledImage.createGraphics();
            g2.drawImage(original, 0, 0, gamePanel.tileSize, gamePanel.tileSize, null);
            g2.dispose(); // Clean up memory

        } catch (Exception e) {
            System.err.println("CRASH: Could not load or scale image -> " + imagePath);
            e.printStackTrace();
        }
        return scaledImage;
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

        // for hitbox debugging purposes
        //g2.setColor(Color.RED);
        //g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            // Identify the visual object the player just touched
            SuperObject grabbedVisualObject = gamePanel.obj[i];
            System.out.println("Player picked up visual object: " + grabbedVisualObject.name);

            //  Remove it from the 2D map
            gamePanel.obj[i] = null;

            // Translate the visual name into a backend Item
            if (myCharacter != null) {
                items.Item backendItem = null; // Prepare an empty backend item

                // Match the visual object's name to the factory
                switch (grabbedVisualObject.name) {
                    // Consumable
                    case "Health Potion": backendItem = items.ItemFactory.createHealthPotion(); break;

                    // Weapons
                    case "Sword": backendItem = items.ItemFactory.createSword(); break;
                    case "Assassin Blade": backendItem = items.ItemFactory.createAssassinBlade(); break;

                    // Buffs
                    case "Helmet": backendItem = items.ItemFactory.createHelmet(); break;
                    case "Armor": backendItem = items.ItemFactory.createArmor(); break;
                    case "Shield": backendItem = items.ItemFactory.createShield(); break;
                    case "Spear": backendItem = items.ItemFactory.createSpear(); break;
                    case "Hermes Boots": backendItem = items.ItemFactory.createHermesBoots(); break;

                    // Debuffs
                    case "Poison Vial": backendItem = items.ItemFactory.createPoisonVial(); break;
                    case "Cursed Rune": backendItem = items.ItemFactory.createCursedRune(); break;
                    case "Debuff Scroll": backendItem = items.ItemFactory.createDebuffScroll(); break;
                    case "Leaden Boots": backendItem = items.ItemFactory.createLeadenBoots(); break;

                    case "Ancient Seed":
                    case "Obsidian Skull":
                    case "Frozen Tear":
                    case "Golden Scarab":
                    case "Dinosaur Egg":
                    case "Mystery Relic":
                        // Relics trigger the win condition in the main update loop, so no backend stat item is needed
                        break;

                    default: System.out.println("Unknown item: " + grabbedVisualObject.name);
                }

                // If we successfully created a backend item, put it in the inventory
                if (backendItem != null) {
                    myCharacter.getInventory().addItem(backendItem, 1);

                    // IF it's an instant-use item, apply it  now
                    if (backendItem.getType() == items.ItemType.CONSUMABLE) {
                        backendItem.applyEffect(myCharacter);
                    }
                }
            }

            // Refresh stats (in case they picked up smt like speed boots)
            refreshStats();

            // Check if they won
            gamePanel.checkWinCondition();
        }
    }

    public void refreshStats() {
        if (myCharacter != null) {
            this.speed = myCharacter.getSpeed();
        }
    }
}