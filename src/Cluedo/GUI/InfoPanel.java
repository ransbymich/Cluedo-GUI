package Cluedo.GUI;

import Cluedo.Board;
import Cluedo.GameObjects.Player;
import Cluedo.Helpers.Type;
import Cluedo.Util.GUIUtil;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    private JLabel pName;
    private JList<Type> list;
    private Board board;
    private JLabel redDie;
    private JLabel whiteDie;


    public InfoPanel(Board board) {
        super(new GridBagLayout());
        this.board = board;
        this.pName = new JLabel();
        this.list = new JList<>();

        this.add(pName, GUIUtil.makeConstraints(0, 0, 1, 1, GridBagConstraints.LINE_START));

        this.add(new JLabel("Hand"), GUIUtil.makeConstraints(0, 1, 1, 1, GridBagConstraints.LINE_START));

        list.setCellRenderer((jList, type, i, b, b1) -> {
            JLabel jl = new JLabel();
            Image img = GUI.ASSETS.get(type);
            jl.setSize(img.getWidth(null), img.getHeight(null));
            jl.setIcon(new ImageIcon(GUI.ASSETS.get(type)));
            return jl;
        });

        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(1);

        JScrollPane scrollPane = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(CluedoCanvas.WIDTH/2, 145));

        this.add(scrollPane, GUIUtil.makeConstraints(0, 2, 2, 1, GridBagConstraints.CENTER));

        redDie = new JLabel(new ImageIcon(GUI.RED_DIE[0]));
        whiteDie = new JLabel(new ImageIcon(GUI.WHITE_DIE[0]));

        this.add(redDie, GUIUtil.makeConstraints(3, 2, 1, 1, GridBagConstraints.CENTER));
        this.add(whiteDie, GUIUtil.makeConstraints(4, 2, 1, 1, GridBagConstraints.CENTER));

        update();
    }


    public void update(){
        Player player = board.getCurrentPlayer();
        this.pName.setText(player.getName());
        this.list.setListData(player.getHand().toArray(new Type[0]));
    }

    public void changeDice(int redDice, int whiteDice){
        redDie.setIcon(new ImageIcon(GUI.RED_DIE[redDice - 1]));
        whiteDie.setIcon(new ImageIcon(GUI.WHITE_DIE[whiteDice - 1]));
    }
}
