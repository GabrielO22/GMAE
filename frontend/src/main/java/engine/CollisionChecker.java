package engine;

import entity.Player;

public class CollisionChecker {
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile (Player player) {
        // pixel hit box
        int entityLeftX = player.worldX + player.solidArea.x;
        int entityRightX = player.worldX + player.solidArea.x + player.solidArea.width;
        int entityTopY = player.worldY + player.solidArea.y;
        int entityBottomY = player.worldY + player.solidArea.y + player.solidArea.height;

        // grid (row/column) hit box
        int entityLeftCol = entityLeftX / gamePanel.tileSize;
        int entityRightCol = entityRightX / gamePanel.tileSize;
        int entityTopRow = entityTopY / gamePanel.tileSize;
        int entityBottomRow = entityBottomY / gamePanel.tileSize;

        int tileNum1, tileNum2;

        // predict where player is going based on direction they face
        switch (player.direction) {
            case "up":
                entityTopRow = (entityTopY - player.speed) / gamePanel.tileSize;
                // Check the top-left and top-right corners of the hitbox
                tileNum1 = gamePanel.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gamePanel.tileM.tile[tileNum1].collision || gamePanel.tileM.tile[tileNum2].collision) {
                    player.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomY + player.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gamePanel.tileM.tile[tileNum1].collision || gamePanel.tileM.tile[tileNum2].collision) {
                    player.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftX - player.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gamePanel.tileM.tile[tileNum1].collision || gamePanel.tileM.tile[tileNum2].collision) {
                    player.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightX + player.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gamePanel.tileM.tile[tileNum1].collision || gamePanel.tileM.tile[tileNum2].collision) {
                    player.collisionOn = true;
                }
                break;
        }
    }

    // Check for object collisions
    public int checkObject(Player player, boolean isPlayer) {
        int index = 999;

        for (int i = 0; i < gamePanel.obj.length; i++) {
            if (gamePanel.obj[i] != null) {
                // Get player's solid area position
                player.solidArea.x = player.worldX + player.solidArea.x;
                player.solidArea.y = player.worldY + player.solidArea.y;

                // Get object's solid area position
                gamePanel.obj[i].solidArea.x = gamePanel.obj[i].worldX + gamePanel.obj[i].solidArea.x;
                gamePanel.obj[i].solidArea.y = gamePanel.obj[i].worldY + gamePanel.obj[i].solidArea.y;

                // Predict where player is going
                switch (player.direction) {
                    case "up": player.solidArea.y -= player.speed; break;
                    case "down": player.solidArea.y += player.speed; break;
                    case "left": player.solidArea.x -= player.speed; break;
                    case "right": player.solidArea.x += player.speed; break;
                }

                // If they intersect, the player is touching the object!
                if (player.solidArea.intersects(gamePanel.obj[i].solidArea)) {
                    index = i;
                }

                // Reset hitboxes back to default relative positions
                player.solidArea.x = 8; // Or whatever your default is
                player.solidArea.y = 16;
                gamePanel.obj[i].solidArea.x = gamePanel.obj[i].solidAreaDefaultX;
                gamePanel.obj[i].solidArea.y = gamePanel.obj[i].solidAreaDefaultY;
            }
        }
        return index;
    }
}
