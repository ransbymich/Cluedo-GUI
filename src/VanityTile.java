public class VanityTile extends Tile{
    private String label;

    public VanityTile(Position aPosition, String label) {
        super(aPosition);
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
