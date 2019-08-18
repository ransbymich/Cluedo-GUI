package Cluedo.Tiles;

import Cluedo.GUI.CluedoCanvas;
import Cluedo.GUI.GUI;
import Cluedo.GameObjects.Room;
import Cluedo.Helpers.Position;
import Cluedo.Helpers.Type;

import java.awt.*;

import static Cluedo.GUI.CluedoCanvas.xOffset;
import static Cluedo.GUI.CluedoCanvas.yOffset;

public class RoomTile extends Tile {

    /**
     * @param aPosition The location of the tile.
     * @param room the room this roomTile is linked to
     */
    public RoomTile(Position aPosition, Room room) {
        super(aPosition);
        room.addDisplayTile(this);
    }

    /**
     * This method should do nothing...
     * The Room class will render items on top of this tile's position, if need be
     * @param g
     */
    @Override
    public void render(Graphics g) {

    }

    /**
     * @return RoomTiles are represented by two spaces in the inString
     */
    @Override
    public String toString() {
        return "  ";
    }
}
