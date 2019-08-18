package Cluedo.Tiles;

import Cluedo.GUI.CluedoCanvas;
import Cluedo.GUI.GUI;
import Cluedo.Helpers.Position;
import Cluedo.Helpers.Type;

import java.awt.*;

import static Cluedo.GUI.CluedoCanvas.xOffset;
import static Cluedo.GUI.CluedoCanvas.yOffset;

public class VanityTile extends Tile {
    private String label;

    /**
     * @param aPosition The location of the tile.
     * @param label Label for the tile, used any time that the tile is printed.
     */
    public VanityTile(Position aPosition, String label) {
        super(aPosition);
        this.label = label;
    }

    @Override
    public void render(Graphics g) {
        Position pos = getPosition();

        Image myImage  = GUI.ASSETS.get(Type.LOUNGE);

        int size = CluedoCanvas.TILE_SIZE;

        g.drawImage(myImage,
                (pos.getX() * size) + xOffset,
                (pos.getY() * size) + yOffset,
                size, size, null);
    }

    /**
     * @return the label of the tile, provided in the constructor
     */
    @Override
    public String toString() {
        return label;
    }
}
