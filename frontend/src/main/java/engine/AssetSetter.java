package engine;

import object.ObjectKey;
import object.SuperObject;

import java.util.Random;

public class AssetSetter {
    GamePanel gamePanel;
    Random random = new Random();

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject () {
        int itemsToSpawn = 3;

        for (int i = 0; i < itemsToSpawn; i++) {
            SuperObject newItem = getRandomRealmItem();

            // search for valid walking tiles (collision == false)
            boolean validLocation = false;
            int randomCol = 0;
            int randomRow = 0;

            while (!validLocation) {
                randomCol = random.nextInt(gamePanel.maxWorldCol); // unfortunately include borders, might slow down game due to duds
                randomRow = random.nextInt(gamePanel.maxWorldRow);

                // Look up the tile at this random coordinate
                int tileNum = gamePanel.tileM.mapTileNum[randomCol][randomRow];

                // If the tile does NOT have collision, it's a valid floor!
                if (!gamePanel.tileM.tile[tileNum].collision) {
                    validLocation = true;
                }
            }

            newItem.worldX = randomCol * gamePanel.tileSize;
            newItem.worldY = randomRow * gamePanel.tileSize;
            gamePanel.obj[i] = newItem;
        }
    }

    // Loot Pool Logic
    private SuperObject getRandomRealmItem() {
        return switch (gamePanel.currentRealm.toUpperCase()) {
            case "LAVA" -> new ObjectKey(); // new OBJ_FireRelic();
            case "ICE" -> new ObjectKey(); // new OBJ_FrozenRelic();
            case "FOREST" -> new ObjectKey(); //new OBJ_ForestRelic();
            case "MUD" -> new ObjectKey(); // new OBJ_MudRelic();
            case "DESERT" -> new ObjectKey(); //new OBJ_DesertRelic();
            default -> new ObjectKey(); //new OBJ_GenericRelic();
        };
    }

}
