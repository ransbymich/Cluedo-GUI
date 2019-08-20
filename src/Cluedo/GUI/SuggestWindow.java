package Cluedo.GUI;

import Cluedo.Board;
import Cluedo.Helpers.Position;
import Cluedo.Helpers.State;
import Cluedo.Helpers.TypeCellRender;
import Cluedo.Moves.GUIAccuse;
import Cluedo.Moves.GUISuggest;
import Cluedo.Tiles.RoomTile;
import Cluedo.Util.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This class represents and manages a suggestion or accusation
 */
public class SuggestWindow extends JFrame {
    private GUI gui;
    private Board board;

    private JPanel mPanel; //Main panel

    private JList<Cluedo.Helpers.Type> players;
    private JList<Cluedo.Helpers.Type> weapons;

    private Cluedo.Helpers.Type room;

    private boolean isSuggestion;

    public SuggestWindow(GUI gui, Board board, boolean isSuggestion){
        this.gui = gui;
        this.board = board;

        this.isSuggestion = isSuggestion;

        initialize();
    }

    @SuppressWarnings("Duplicates")
    private void initialize(){
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setTitle(isSuggestion ? "Make a suggestion" : "Make an accusation");

        mPanel = new JPanel(new GridBagLayout());

        //Create the two JLists for the suggestion/accusation
        players = new JList<>(board.getPlayerTypes().toArray(new Cluedo.Helpers.Type[0]));
        weapons = new JList<>(Cluedo.Helpers.Type.getTypes(Cluedo.Helpers.Type.SubType.WEAPON).toArray(new Cluedo.Helpers.Type[0]));

        initializeList(players, 0);
        initializeList(weapons, 1);

        Position pPos = board.getCurrentPlayer().getPosition();
        room = ((RoomTile)board.getBoard()[pPos.getY()][pPos.getX()]).getRoom().getType();

        //Create the JLabel that holds the room card and add it to the bottom of the window
        mPanel.add(new JLabel(new ImageIcon(GUI.ASSETS.get(room))), GUIUtil.makeConstraints(0, 2, 1, 1, GridBagConstraints.CENTER));

        JButton suggestBtn = new JButton(isSuggestion ? "Make a suggestion" : "Make an accusation");

        //Add the completion button
        mPanel.add(suggestBtn, GUIUtil.makeConstraints(0, 3, 1, 1, GridBagConstraints.CENTER));

        suggestBtn.addActionListener(isSuggestion ? this::processSuggestion : this::processAccusation);

        this.getContentPane().add(mPanel);

        this.pack();

        this.setVisible(true);

    }

    /**
     * Processes the completion of a accusation
     * @param e The ActionEvent related to the button press
     */
    private void processAccusation(ActionEvent e){
        Cluedo.Helpers.Type person = players.getSelectedValue();
        Cluedo.Helpers.Type weapon = weapons.getSelectedValue();

        //If any one of the two card types have not been selected then reject the accusation
        if(person == null || weapon == null) {
            JOptionPane.showMessageDialog(this, "Must select one person and one weapon to suggest!");
        }else{
            gui.getConsole().println(board.getCurrentPlayer().getName() + " accuses " + person.getName() + " of murdering Mr. Black with " +
                    weapon + " in " + room.getName());

            board.processTurn(new GUIAccuse(weapon, person, gui));
            gui.getInfoPanel().update();
            this.setVisible(false);
            this.dispose();
        }
    }

    /**
     * Proccess the completion of a suggestion
     * @param e
     */
    private void processSuggestion(ActionEvent e){
        Cluedo.Helpers.Type person = players.getSelectedValue();
        Cluedo.Helpers.Type weapon = weapons.getSelectedValue();

        if(person == null || weapon == null){
            JOptionPane.showMessageDialog(this, "Must select one person and one weapon to suggest!");
        }else{
            //Process the suggestion
            board.processTurn(new GUISuggest(gui, weapon, person));
            gui.getInfoPanel().update();
            //If the state after suggestion process is refuting, then initialize the refuting process
            if(board.getState() == State.REFUTING){
                new RefuteWindow(gui, room, person, weapon, board);
            }else if(board.getState() == State.ACCUSE){ //If nobody was able to refute the suggestion then the state should be accuse
                gui.getConsole().println("Nobody was able to refute your suggestion!");
            }

            //Remove of this window
            this.setVisible(false);
            this.dispose();
        }
    }

    @SuppressWarnings("Duplicates")
    /**
     * Initializes a list within this window
     */
    private void initializeList(JList<Cluedo.Helpers.Type> list, int y){
        //Set up the horizontal scrollpane for the is
        JScrollPane sp = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setPreferredSize(new Dimension(200, 145));

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(1);

        list.setCellRenderer(new TypeCellRender());

        mPanel.add(sp, GUIUtil.makeConstraints(0, y, 1, 1, GridBagConstraints.CENTER));
    }
}
