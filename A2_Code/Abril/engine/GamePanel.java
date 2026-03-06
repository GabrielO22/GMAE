package engine;

import tile.TileManager;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 tile

    public final int maxScreenCol = 16; // 4 by 3 ratio
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    int FPS = 60;
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    Player player1;
    Player player2;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // Improves rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true); // required to receive key inputs


        player1 = new Player(100, 100, keyH, true);
        player2 = new Player(500, 100, keyH, false);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
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

        tileM.draw(g2);
        player1.draw(g2, tileSize);
        player2.draw(g2, tileSize);

        g2.dispose(); // Good practice to save memory
    }
}