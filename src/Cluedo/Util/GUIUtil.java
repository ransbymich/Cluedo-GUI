package Cluedo.Util;

import java.awt.*;

public class GUIUtil {

    /**
     * Creates constraints for a GridBagLayout
     * @param x         The x position in the grid
     * @param y         The y position in the grid
     * @param width     The width of the cell
     * @param height    The height of the cell
     * @param anchor    The anchor position within the cell
     * @return          The GridBagContraints
     */
    public static GridBagConstraints makeConstraints(int x, int y, int width, int height, int anchor){
        GridBagConstraints gb = new GridBagConstraints();
        gb.gridx = x;
        gb.gridy = y;
        gb.gridwidth = width;
        gb.gridheight = height;
        gb.anchor = anchor;
        return gb;
    }
}
