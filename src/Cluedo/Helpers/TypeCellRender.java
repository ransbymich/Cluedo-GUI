package Cluedo.Helpers;

import Cluedo.GUI.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Generic renderer for Type cards for JLists
 */
public class TypeCellRender implements ListCellRenderer<Cluedo.Helpers.Type> {

    @Override
    public Component getListCellRendererComponent(JList<? extends Type> jList, Type type, int i, boolean b, boolean b1) {
        JLabel jl = new JLabel();
        Image img = GUI.ASSETS.get(type);
        jl.setSize(img.getWidth(null), img.getHeight(null));
        jl.setIcon(new ImageIcon(GUI.ASSETS.get(type)));
        if(b){  //If the card is selected then give it a red background
            jl.setOpaque(true);
            jl.setBackground(Color.RED);
        }
        return jl;
    }
}
