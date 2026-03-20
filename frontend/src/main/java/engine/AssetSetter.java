package engine;

import items.LootManager;
import object.ObjectKey;
import object.SuperObject;

import java.util.List;
import java.util.Random;

public class AssetSetter {
    GamePanel gamePanel;
    Random random = new Random();

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        // Failsafe in case currentRealm is null during early boot
        String currentRealm = gamePanel.currentRealm != null ? gamePanel.currentRealm.toUpperCase() : "FOREST";

        // Determine total items allowed on the map based on the Design Document
        int totalItems = switch (currentRealm) {
            case "LAVA" -> 6;
            case "ICE" -> 10;
            case "DESERT" -> 8;
            case "MUD" -> 8;
            case "MINES" -> 7;
            default -> 8;
        };

        // Subtract 1 because the LootManager automatically includes the 1 Realm Trophy
        int commonItemCount = totalItems - 1;

        // Fetch the dynamically generated, weighted item list from our Manager
        List<String> itemsToSpawn = LootManager.generateLootForMap(currentRealm, commonItemCount); // loot pool

        for (int i = 0; i < itemsToSpawn.size(); i++) {
            String itemName = itemsToSpawn.get(i);

            // Create a generic visual object and assign its name
            SuperObject newItem = new SuperObject();
            newItem.name = itemName;
            newItem.loadImage(gamePanel);

            // search for valid walking tiles (collision == false)
            boolean validLocation = false;
            int randomCol = 0;
            int randomRow = 0;

            while (!validLocation) {
                randomCol = random.nextInt(gamePanel.maxWorldCol); // unfortunately includes borders, might slow down game due to duds
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
            if (i < gamePanel.obj.length) {
                gamePanel.obj[i] = newItem;
            } else {
                System.err.println("Warning: Map tried to spawn more items than gamePanel.obj array can hold");
            }
        }
    }

}
