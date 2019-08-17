package Cluedo.Util;

import java.awt.*;

public class GUIUtil {

    public static GridBagConstraints setInsets(GridBagConstraints gb, int top, int bottom, int left, int right){
        gb.insets = new Insets(top, left, bottom, right);
        return gb;
    }

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
