/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


// line 126 "model.ump"
// line 211 "model.ump"
public class Tile {

    private Position position;

    public Tile(Position aPosition) {
        position = aPosition;
    }

    /**
     * Sets a position of a tile
     * @param aPosition The position to set it to
     */
    public boolean setPosition(Position aPosition) {
        boolean wasSet = false;
        position = aPosition;
        wasSet = true;
        return wasSet;
    }

    /**
     * Gets the position of a tile
     * @return  The position of the tile
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Checks whether or not a tile contains a player.
     * @return  Whether or not it contains a player.
     */
    public boolean isPlayer() {
        Tile item = this;
        if (this instanceof EmptyTile) {
            return ((EmptyTile) item).getPlayer() != null;
        }
        return false;
    }
}