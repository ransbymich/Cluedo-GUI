package Cluedo.GUI;

import Cluedo.Board;
import Cluedo.Helpers.Type;
import Cluedo.Util.GUIUtil;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    private JLabel pName;
    private Board board;


    public InfoPanel(Board board) {
        super(new GridBagLayout());
        this.board = board;
        pName = new JLabel();

        this.add(pName, GUIUtil.makeConstraints(0, 0, 1, 1, GridBagConstraints.LINE_START));

        this.add(new JLabel("Hand"), GUIUtil.makeConstraints(0, 1, 1, 1, GridBagConstraints.LINE_START));

        update();
    }


    public void update(){

    }
}
