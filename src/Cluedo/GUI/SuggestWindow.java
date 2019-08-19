package Cluedo.GUI;

import Cluedo.Board;
import Cluedo.GameObjects.Player;
import Cluedo.Helpers.Position;
import Cluedo.Helpers.State;
import Cluedo.Helpers.Type;
import Cluedo.Helpers.TypeCellRender;
import Cluedo.Moves.GUISuggest;
import Cluedo.Tiles.RoomTile;
import Cluedo.Util.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

public class SuggestWindow extends JFrame {
    private GUI gui;
    private Board board;

    private JPanel mPanel; //Main panel

    private JList<Cluedo.Helpers.Type> players;
    private JList<Cluedo.Helpers.Type> weapons;

    private Cluedo.Helpers.Type room;

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
        room = ((RoomTile)board.getBoard()[pPos.getY()][pPos.getX()]).getRoom().getType();

        mPanel.add(new JLabel(new ImageIcon(GUI.ASSETS.get(room))), GUIUtil.makeConstraints(0, 2, 1, 1, GridBagConstraints.CENTER));

        JButton suggestBtn = new JButton("Make Suggestion");


        mPanel.add(suggestBtn, GUIUtil.makeConstraints(0, 3, 1, 1, GridBagConstraints.CENTER));

        suggestBtn.addActionListener(this::processSuggestion);

        this.getContentPane().add(mPanel);

        this.pack();

        this.setVisible(true);

    }

    private void processSuggestion(ActionEvent e){
        Cluedo.Helpers.Type person = players.getSelectedValue();
        Cluedo.Helpers.Type weapon = weapons.getSelectedValue();

        if(person == null || weapon == null){
            JOptionPane.showMessageDialog(this, "Must select one person and one weapon to suggest!");
        }else{
            board.processTurn(new GUISuggest(gui, weapon, person));
            gui.getInfoPanel().update();
            if(board.getState() == State.REFUTING){
                new RefuteWindow(gui, room, person, weapon, board);
            }
        }

        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private void initalizeList(JList<Cluedo.Helpers.Type> list, int x, int y){
        JScrollPane sp = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setPreferredSize(new Dimension(200, 145));

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(1);

        list.setCellRenderer(new TypeCellRender());



        mPanel.add(sp, GUIUtil.makeConstraints(x, y, 1, 1, GridBagConstraints.CENTER));
    }
}
