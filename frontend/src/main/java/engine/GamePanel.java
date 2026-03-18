package engine;

import characters.Character;
import entity.Player;
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


    int FPS = 60;
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

    public SuperObject[] obj = new SuperObject[10];

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

        g2.dispose(); // Good practice to save memory
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
        int itemsLeft = 0;
        for (SuperObject superObject : obj) {
            if (superObject != null) {
                itemsLeft++;
            }
        }

        if (itemsLeft == 0) {
            System.out.println("All Relics Found!");
            gameThread = null;

            // Pseudo-code for when backend Inventory class is ready:
            for (SuperObject item : player1.inventory) {
                // p1Character.getInventory().addItem(item.backendItemId);
                System.out.println(p1Character.getName() + " stored: " + item.name);
            }

            for (SuperObject item : player2.inventory) {
                System.out.println(p2Character.getName() + " stored: " + item.name);
            }

            if (engine != null) {
                engine.returnToMainMenu();
            }
        }
    }
}