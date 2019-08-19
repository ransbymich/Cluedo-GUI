package Cluedo.GUI;

import Cluedo.Board;
import Cluedo.GameObjects.Player;
import Cluedo.Helpers.Position;
import Cluedo.Helpers.Type;
import Cluedo.Tiles.RoomTile;
import Cluedo.Util.GUIUtil;

import javax.swing.*;
import java.awt.*;

public class SuggestWindow extends JFrame {
    private GUI gui;
    private Board board;

    private JPanel mPanel; //Main panel

    private JList<Cluedo.Helpers.Type> players;
    private JList<Cluedo.Helpers.Type> weapons;

    public SuggestWindow(GUI gui, Board board){
        this.gui = gui;
        this.board = board;

        initalize();
    }

    private void initalize(){
        this.setTitle("Make a suggestion");

        mPanel = new JPanel(new GridBagLayout());

        players = new JList<>(board.getPlayerTypes().toArray(new Cluedo.Helpers.Type[0]));
        weapons = new JList<>(Cluedo.Helpers.Type.getTypes(Cluedo.Helpers.Type.SubType.WEAPON).toArray(new Cluedo.Helpers.Type[0]));

        initalizeList(players, 0, 0);
        initalizeList(weapons, 0, 1);

        Position pPos = board.getCurrentPlayer().getPosition();
        Cluedo.Helpers.Type room = ((RoomTile)board.getBoard()[pPos.getY()][pPos.getX()]).getRoom().getType();

        mPanel.add(new JLabel(new ImageIcon(GUI.ASSETS.get(room))), GUIUtil.makeConstraints(0, 2, 1, 1, GridBagConstraints.CENTER));

        this.getContentPane().add(mPanel);

        this.pack();

        this.setVisible(true);

    }

    private void initalizeList(JList<Cluedo.Helpers.Type> list, int x, int y){
        JScrollPane sp = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setPreferredSize(new Dimension(200, 145));

        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(1);

        list.setCellRenderer((jList, type, i, selected, b1) -> {
            JLabel jl = new JLabel();
            Image img = GUI.ASSETS.get(type);
            jl.setSize(img.getWidth(null), img.getHeight(null));
            jl.setIcon(new ImageIcon(GUI.ASSETS.get(type)));
            if(selected){
                jl.setOpaque(true);
                jl.setBackground(Color.RED);
            }
            return jl;
        });

        mPanel.add(sp, GUIUtil.makeConstraints(x, y, 1, 1, GridBagConstraints.CENTER));
    }
}
