package Cluedo.Tiles;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/

import Cluedo.GUI.CluedoCanvas;
import Cluedo.GUI.GUI;
import Cluedo.GameObjects.Player;
import Cluedo.Helpers.Position;

import java.awt.*;

import static Cluedo.GUI.CluedoCanvas.X_OFFSET;
import static Cluedo.GUI.CluedoCanvas.Y_OFFSET;

public class EmptyTile extends Tile {

    private Player player;

    /**
     * @param aPosition the location (x,y) of the tile
     * @param aPlayer pass the player object in here if there is supposed to be a player here, else null
     */
    public EmptyTile(Position aPosition, Player aPlayer) {
        super(aPosition);
        player = aPlayer;
        if(aPlayer != null){
            aPlayer.setPosition(aPosition);
        }
    }

    @Override
    public void render(Graphics g) {
        Position pos = getPosition();
        int size = CluedoCanvas.TILE_SIZE;

        Image myImage;
        if (player == null){
//            myImage = GUI.ASSETS.get(Type.CANDLE_STICK);
            return;
        } else {
            myImage = GUI.ASSETS.get(player.getType());
        }

        g.setColor(player.getType().getColor());
        g.fillOval((pos.getX() * size) + X_OFFSET,
                (pos.getY() * size) + Y_OFFSET,
                size, size);
    }

    /**
     * Sets the player on the tile
     * @param aPlayer The player to set
     */
    public void setPlayer(Player aPlayer) {
        player = aPlayer;
    }

    /**
     * Gets the player on this tile
     * @return The player on the tile
     */
    public Player getPlayer() {
        return player;
    }

  public String toString() {
    if (player == null) return "__";

        switch (player.getType()) {
            case COL_MUSTARD:
                return "MU";
            case MR_WHITE:
                return "WH";
            case DR_GREEN:
                return "GR";
            case MRS_PEACOCK:
                return "PC";
            case PROF_PLUM:
                return "PL";
            case MISS_SCARLETT:
                return "SC";
        }
        return "err";
    }
}