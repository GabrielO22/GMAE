package tile;

import engine.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gamePanel;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gamePanel, String currentRealm) {
        this.gamePanel = gamePanel;
        tile = new Tile[20]; // num of tiles we can have

        // This 2D array will hold the numbers from our text file
        mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        getTileImage();

        String mapToLoad = "";
        switch (currentRealm) {
            case "FOREST": mapToLoad = "/maps/forest_relic_hunt.txt"; break;
            case "LAVA": mapToLoad = "/maps/lava_relic_hunt.txt"; break;
            case "ICE": mapToLoad = "/maps/ice_relic_hunt.txt"; break;
            case "DESERT": mapToLoad = "/maps/desert_relic_hunt.txt"; break;
            case "MUD": mapToLoad = "/maps/mud_relic_hunt.txt"; break;
            default: mapToLoad = "/maps/forest_relic_hunt.txt"; // Fallback
        }

        loadMap(mapToLoad);
    }

    public void getTileImage() {
        try {
            // 00 - B
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/boulder.png"));
            tile[0].collision = true;

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/brick_path.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/bush.png"));
            tile[2].collision = true;

            // 03 - D
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dark_grass.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt.png"));

            // 05 - F
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/flower.png"));

            // 06 - G
            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            // 07 - H
            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/hard_dirt.png"));
            tile[7].collision = true;

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/hard_sand.png"));
            tile[8].collision = true;

            // 09 - I
            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/ice.png"));

            tile[10] = new Tile();
            tile[10].image = ImageIO.read(getClass().getResourceAsStream("/tiles/ice_brick_path.png"));

            // 11 - L
            tile[11] = new Tile();
            tile[11].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lava.png"));
            tile[11].collision = true;

            tile[12] = new Tile();
            tile[12].image = ImageIO.read(getClass().getResourceAsStream("/tiles/light_grass.png"));

            // 13 - M
            tile[13] = new Tile();
            tile[13].image = ImageIO.read(getClass().getResourceAsStream("/tiles/metal.png"));

            tile[14] = new Tile();
            tile[14].image = ImageIO.read(getClass().getResourceAsStream("/tiles/metal_wall.png"));
            tile[14].collision = true;

            // 15 - R
            tile[15] = new Tile();
            tile[15].image = ImageIO.read(getClass().getResourceAsStream("/tiles/rock.png"));
            tile[15].collision = true;

            // 16 - S
            tile[16] = new Tile();
            tile[16].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));

            tile[17] = new Tile();
            tile[17].image = ImageIO.read(getClass().getResourceAsStream("/tiles/short_grass.png"));

            // 18 - T
            tile[18] = new Tile();
            tile[18].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tall_grass.png"));
            tile[18].collision = true;

            // 19 - W
            tile[19] = new Tile();
            tile[19].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            tile[19].collision = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                String line = br.readLine();

                while (col < gamePanel.maxWorldCol) {
                    String[] numbers = line.trim().split("\\s+");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {
            // where is our tile on the real map
            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;

            // where should tile be drawn relative to p1
            int screenX = worldX - gamePanel.player1.worldX + gamePanel.player1.screenX;
            int screenY = worldY - gamePanel.player1.worldY + gamePanel.player1.screenY;

            // only draw tile if it is visible within the screen
            if (worldX + gamePanel.tileSize > gamePanel.player1.worldX - gamePanel.player1.screenX &&
                    worldX - gamePanel.tileSize < gamePanel.player1.worldX + gamePanel.player1.screenX &&
                    worldY + gamePanel.tileSize > gamePanel.player1.worldY - gamePanel.player1.screenY &&
                    worldY - gamePanel.tileSize < gamePanel.player1.worldY + gamePanel.player1.screenY) {
                // Draw the corresponding image
                g2.drawImage(tile[tileNum].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            }

            // Move to the next column
            worldCol++;
            if (worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
