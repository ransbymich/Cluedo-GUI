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
        scrollPane.setPreferredSize(new Dimension(250, 120));

        this.add(scrollPane, GUIUtil.makeConstraints(0, 2, 2, 1, GridBagConstraints.CENTER));

        update();
    }


    public void update(){
        Player player = board.getCurrentPlayer();
        this.pName.setText(player.getName());
        this.list.setListData(player.getHand().toArray(new Type[0]));
    }
}
