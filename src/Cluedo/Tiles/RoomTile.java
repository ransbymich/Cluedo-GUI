package Cluedo.Tiles;

import Cluedo.GameObjects.Room;
import Cluedo.Helpers.Position;

import java.awt.*;

public class RoomTile extends Tile {

    private Room room;

    /**
     * @param pos The location of the tile.
     * @param room the room this roomTile is linked to
     */
    public RoomTile(Position pos, Room room) {
        super(pos);
        room.addInternalTile(this);
        this.room = room;
    }

    public Room getRoom() {
        return room;
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
