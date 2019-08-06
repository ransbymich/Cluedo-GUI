public class VanityTile extends Tile{
    private String label;

    /**
     * @param aPosition The location of the tile.
     * @param label Label for the tile, used any time that the tile is printed.
     */
    public VanityTile(Position aPosition, String label) {
        super(aPosition);
        this.label = label;
    }

    /**
     * @return the label of the tile, provided in the constructor
     */
    @Override
    public String toString() {
        return label;
    }
}
