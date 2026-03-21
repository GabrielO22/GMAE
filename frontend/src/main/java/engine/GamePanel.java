package engine;

import characters.Character;
import entity.Player;
import javafx.application.Platform;
import object.SuperObject;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCALE
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 tile

    // SCREEN SETTINGS
    public final int maxScreenCol = 16; // 4 by 3 ratio
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 25;
    public final int maxWorldRow = 25;

    // TIMER SYSTEM
    int FPS = 60;
    public double playTime; // How much time is left in seconds
    public boolean isTimeUp = false; // Flag to stop the game
    public boolean isGameOver = false;

    /** Returns the time limit in seconds for the given realm name. */
    public static double getTimeLimitForRealm(String realmName) {
        return switch (realmName != null ? realmName.toUpperCase() : "FOREST") {
            case "LAVA"   -> 90.0;   // Short and punishing
            case "ICE"    -> 150.0;  // Large map, more time
            case "DESERT" -> 120.0;  // Standard survival
            case "MUD"    -> 120.0;  // Standard debuff
            case "MINES"  -> 105.0;  // Slightly shorter weapon run
            default       -> 120.0;  // FOREST and fallback — 2 minutes
        };
    }


    public String currentRealm;
    TileManager tileM;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    public Player player1;
    public Player player2;
    public Character p1Character;
    public Character p2Character;

    public SuperObject[] obj = new SuperObject[20];

    public Engine engine;


    public GamePanel(Engine engine, String realmName, Character p1Char, Character p2Char) {
        this.engine = engine;
        this.currentRealm = realmName;
        p1Character =  p1Char;
        p2Character = p2Char;
        tileM = new TileManager(this, currentRealm);

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // Improves rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true); // required to receive key inputs


        player1 = new Player(this, 48, 48, keyH, true, p1Character);
        player2 = new Player(this, 96, 48, keyH, false, p2Character);

    }

    public void setupGame() {
        assetSetter.setObject();
        playTime = getTimeLimitForRealm(currentRealm); // per-realm time limit
        isTimeUp = false;
        isGameOver = false;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.setDaemon(true);
        gameThread.start();
    }

    // THE GAME LOOP
    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        if (isTimeUp) {
            return;
        }

        // 1. Tick down the timer
        playTime -= (1.0 / 60.0);

        // 2. Check for Timeout
        if (playTime <= 0) {
            playTime = 0;
            triggerGameOver();
        }

        player1.update();
        player2.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // erase previous frame
        Graphics2D g2 = (Graphics2D) g;

        int camX = getCameraX();
        int camY = getCameraY();

        // TILE
        tileM.draw(g2); // internally uses cameraX

        // OBJECTS
        for (SuperObject superObject : obj) {
            if (superObject != null) {
                superObject.draw(g2, this);
            }
        }

        // PLAYERS
        player1.draw(g2, player1.worldX - camX, player1.worldY - camY);
        player2.draw(g2, player2.worldX - camX, player2.worldY - camY);

        // TIMER HUD
        g2.setFont(new Font("Monospaced", Font.BOLD, 18));

        int minutes = (int) playTime / 60;
        int seconds = (int) playTime % 60;
        String timeString = String.format("TIME  %d:%02d", minutes, seconds);

        // Semi-transparent background bar
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRect(0, 0, screenWidth, 36);

        // Timer — center, red under 30s
        g2.setColor(playTime <= 30.0 ? Color.RED : Color.WHITE);
        FontMetrics fm = g2.getFontMetrics();
        int timeX = (screenWidth - fm.stringWidth(timeString)) / 2;
        g2.drawString(timeString, timeX, 24);

        // P1 relic count — top left
        if (engine.getPlayer1Profile() != null) {
            g2.setColor(new Color(0x0B6B80));
            g2.setFont(new Font("Monospaced", Font.BOLD, 14));
            g2.drawString("P1 Relics: " + engine.getPlayer1Profile().getRelicsCollected(), 10, 24);
        }

        // P2 relic count — top right
        if (engine.getPlayer2Profile() != null) {
            g2.setColor(new Color(0x8B0000));
            g2.setFont(new Font("Monospaced", Font.BOLD, 14));
            String p2Str = "P2 Relics: " + engine.getPlayer2Profile().getRelicsCollected();
            FontMetrics fm2 = g2.getFontMetrics();
            g2.drawString(p2Str, screenWidth - fm2.stringWidth(p2Str) - 10, 24);
        }

        // Mid-game banner when all relics are collected
        if (allRelicsFound) {
            g2.setFont(new Font("Monospaced", Font.BOLD, 22));
            String banner = "All Relics Found! Returning to menu...";
            FontMetrics fmb = g2.getFontMetrics();
            int bx = (screenWidth - fmb.stringWidth(banner)) / 2;
            g2.setColor(new Color(0, 0, 0, 180));
            g2.fillRect(bx - 10, screenHeight / 2 - 30, fmb.stringWidth(banner) + 20, 40);
            g2.setColor(new Color(0xFF, 0xFF, 0x00));
            g2.drawString(banner, bx, screenHeight / 2);
        }

        Toolkit.getDefaultToolkit().sync(); // force display to sync with frame rate of game
    }

    // Calculate the camera's top-left world coordinates
    public int getCameraX() {
        // Find the visual center by adding the width of one half-tile to the average (offset)
        int midX = (player1.worldX + player2.worldX + tileSize) / 2;
        int camX = midX - (screenWidth / 2);

        // CLAMPING: Prevent showing the black void on left/right
        return Math.max(0, Math.min(camX, (maxWorldCol * tileSize) - screenWidth));
    }

    public int getCameraY() {
        int midY = (player1.worldY + player2.worldY + tileSize) / 2;
        int camY = midY - (screenHeight / 2);

        // CLAMPING: Prevent showing the black void on top/bottom
        return Math.max(0, Math.min(camY, (maxWorldRow * tileSize) - screenHeight));
    }

    public void checkWinCondition() {
        if (isGameOver) return;
        for (SuperObject superObject : obj) {
            if (superObject != null) return; // at least one item remains
        }
        // All relics collected — show banner for 3 seconds then exit
        if (!allRelicsFound) {
            allRelicsFound = true;
            isTimeUp = true; // freeze player movement and timer
            saveInventoriesToBackend();
            Platform.runLater(() -> {
                javafx.animation.PauseTransition pause =
                        new javafx.animation.PauseTransition(javafx.util.Duration.seconds(3));
                pause.setOnFinished(e -> {
                    gameThread = null; // stop game loop after banner shown
                    engine.returnToMainMenu();
                });
                pause.play();
            });
        }
    }

    private boolean allRelicsFound = false;

    private void triggerGameOver() {
        if (isGameOver) return;

        isGameOver = true;
        isTimeUp = true;
        gameThread = null;

        saveInventoriesToBackend();

        Platform.runLater(() -> {
            System.out.println("TIME IS UP! Relic hunt failed.");
            if (engine != null) {
                engine.returnToMainMenu();
            }
        });
    }

    private items.Item convertToBackendItem(String itemName) {
        return switch (itemName) {
            case "Health Potion" -> items.ItemFactory.createHealthPotion();
            case "Sword" -> items.ItemFactory.createSword();
            case "Assassin Blade" -> items.ItemFactory.createAssassinBlade();
            case "Helmet" -> items.ItemFactory.createHelmet();
            case "Armor" -> items.ItemFactory.createArmor();
            case "Shield" -> items.ItemFactory.createShield();
            case "Spear" -> items.ItemFactory.createSpear();
            case "Hermes Boots" -> items.ItemFactory.createHermesBoots();
            case "Poison Vial" -> items.ItemFactory.createPoisonVial();
            case "Cursed Rune" -> items.ItemFactory.createCursedRune();
            case "Debuff Scroll" -> items.ItemFactory.createDebuffScroll();
            case "Leaden Boots" -> items.ItemFactory.createLeadenBoots();
            case "Ancient Seed" -> items.ItemFactory.createAncientSeed();
            case "Obsidian Skull" -> items.ItemFactory.createObsidianSkull();
            case "Frozen Tear" -> items.ItemFactory.createFrozenTear();
            case "Golden Scarab" -> items.ItemFactory.createGoldenScarab();
            case "Dinosaur Egg" -> items.ItemFactory.createDinosaurEgg();
            case "Mystery Relic" -> items.ItemFactory.createMysteryRelic();
            default -> null; // Ignored items (like the Obsidian Skull or missing items)
        };
    }

    private void saveInventoriesToBackend() {
        System.out.println("Saving loot to character profiles...");

        // Process Player 1's Loot
        for (SuperObject visualItem : player1.inventory) {
            items.Item backendItem = convertToBackendItem(visualItem.name);
            if (backendItem != null) {
                p1Character.getInventory().addItem(backendItem, 1);
                System.out.println(p1Character.getName() + " secured a " + backendItem.getName());
            }
        }

        // Process Player 2's Loot
        for (SuperObject visualItem : player2.inventory) {
            items.Item backendItem = convertToBackendItem(visualItem.name);
            if (backendItem != null) {
                p2Character.getInventory().addItem(backendItem, 1);
                System.out.println(p2Character.getName() + " secured a " + backendItem.getName());
            }
        }
    }
}