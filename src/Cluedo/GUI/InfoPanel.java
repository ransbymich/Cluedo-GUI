package Cluedo.GUI;

import Cluedo.Board;
import Cluedo.GameObjects.Player;
import Cluedo.Helpers.Die;
import Cluedo.Helpers.State;
import Cluedo.Helpers.Type;
import Cluedo.Helpers.TypeCellRender;

import Cluedo.Util.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This class represents and manages the information panel seen at the bottom of the UI
 */
public class InfoPanel extends JPanel {

    private JLabel pName; //player name
    private JList<Type> list;
    private Board board;
    private JLabel redDie;
    private JLabel whiteDie;
    private JLabel stateLabel;
    private GUI gui;

    private JButton suggestBtn;
    private JButton accuseBtn;
    private JButton moveBtn;
    private JButton completeBtn;

    private int diceRoll = 2;

    public InfoPanel(Board board, GUI gui) {
        super(new GridBagLayout());
        this.board = board;
        this.gui = gui;
        this.pName = new JLabel();
        this.list = new JList<>();

        //Create the two JLabels for the players name and "Hand"
        this.add(pName, GUIUtil.makeConstraints(0, 0, 1, 1, GridBagConstraints.LINE_START));

        this.add(new JLabel("Hand"), GUIUtil.makeConstraints(0, 1, 1, 1, GridBagConstraints.LINE_START));

        //Setup the JList to show the cards in the players hand
        list.setCellRenderer(new TypeCellRender());

        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(1);

        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        //Setup the horizontal scroll pane for the list
        JScrollPane scrollPane = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(CluedoCanvas.WIDTH/2, 145));

        this.add(scrollPane, GUIUtil.makeConstraints(0, 2, 2, 1, GridBagConstraints.CENTER));

        //Set up a new panel to hold all of the components on the side of the information panel
        JPanel sidePanel = new JPanel(new GridBagLayout());

        this.add(sidePanel, GUIUtil.makeConstraints(3, 2, 1, 1, GridBagConstraints.CENTER));

        //Set up the labels for the dice
        redDie = new JLabel(new ImageIcon(GUI.RED_DIE[0]));
        whiteDie = new JLabel(new ImageIcon(GUI.WHITE_DIE[0]));

        sidePanel.add(redDie, GUIUtil.makeConstraints(0, 0, 1, 1, GridBagConstraints.CENTER));
        sidePanel.add(whiteDie, GUIUtil.makeConstraints(0, 1, 1, 1, GridBagConstraints.CENTER));

        //Set up a new panel to hold all of the buttons
        JPanel btnPanel = new JPanel(new GridBagLayout());

        sidePanel.add(btnPanel, GUIUtil.makeConstraints(1, 0, 1, 2, GridBagConstraints.CENTER));

        completeBtn = new JButton("Complete Turn (c)");
        suggestBtn = new JButton("Suggest (s)");
        accuseBtn = new JButton("Accuse (a)");
        moveBtn = new JButton("Move (m)");

        completeBtn.setPreferredSize(new Dimension(150, 37));
        suggestBtn.setPreferredSize(new Dimension(150, 37));
        accuseBtn.setPreferredSize(new Dimension(150, 37));
        moveBtn.setPreferredSize(new Dimension(150, 37));

        btnPanel.add(completeBtn, GUIUtil.makeConstraints(0, 0, 1, 1, GridBagConstraints.CENTER));
        btnPanel.add(suggestBtn, GUIUtil.makeConstraints(0, 1, 1, 1, GridBagConstraints.CENTER));
        btnPanel.add(accuseBtn, GUIUtil.makeConstraints(0, 2, 1, 1, GridBagConstraints.CENTER));
        btnPanel.add(moveBtn, GUIUtil.makeConstraints(0, 3, 1, 1, GridBagConstraints.CENTER));

        //Set up all the listeners for the buttons
        completeBtn.addActionListener((e)->{
            board.completeTurn();
            gui.getConsole().setInputConsumer(null);
            this.update();
        });

        suggestBtn.addActionListener((e)->{
            if(board.getState() == State.SUGGEST_MOVE || board.getState() == State.SUGGEST){
                board.setState(State.SUGGESTING);
                update();
                new SuggestWindow(gui, board, true);
            }
        });

        accuseBtn.addActionListener((e)->{
            if(board.getState() == State.ACCUSE){
                board.setState(State.ACCUSING);
                update();
                new SuggestWindow(gui, board, false);
            }
        });

        moveBtn.addActionListener((e)->{
            if(board.getState() == State.MOVE || board.getState() == State.SUGGEST_MOVE){
                changeDice();
                board.setState(State.MOVING);
                update();
            }
        });

        stateLabel = new JLabel();

        sidePanel.add(stateLabel, GUIUtil.makeConstraints(2, 0, 1, 1, GridBagConstraints.CENTER));

        update();
    }

    /**
     * Updates the panel based off current game state
     */
    public void update(){
        Player player = board.getCurrentPlayer();
        this.pName.setText(player.getName());
        this.list.setListData(player.getHand().toArray(new Type[0]));

        //Change which buttons are useable in each state
        switch (board.getState()) {
            case END_TURN:
                suggestBtn.setEnabled(false);
                accuseBtn.setEnabled(false);
                moveBtn.setEnabled(false);
                completeBtn.setEnabled(true);
                break;
            case SUGGEST:
                suggestBtn.setEnabled(true);
                accuseBtn.setEnabled(false);
                moveBtn.setEnabled(false);
                completeBtn.setEnabled(true);
                break;
            case ACCUSE:
                suggestBtn.setEnabled(false);
                accuseBtn.setEnabled(true);
                moveBtn.setEnabled(false);
                completeBtn.setEnabled(true);
                break;
            case MOVE:
                suggestBtn.setEnabled(false);
                accuseBtn.setEnabled(false);
                moveBtn.setEnabled(true);
                completeBtn.setEnabled(true);
                break;
            case SUGGEST_MOVE:
                suggestBtn.setEnabled(true);
                accuseBtn.setEnabled(false);
                moveBtn.setEnabled(true);
                completeBtn.setEnabled(true);
                break;
            case SUGGESTING:
            case MOVING:
            case REFUTING:
            case ACCUSING:
                suggestBtn.setEnabled(false);
                accuseBtn.setEnabled(false);
                moveBtn.setEnabled(false);
                completeBtn.setEnabled(false);
                break;
        }
    }

    /**
     * Rolls two dice, updates the information panel to show what the dice rolled and then return the sum of the two
     * numbers
     * @return  The sum of the two dice rolled
     */
    public int changeDice(){
        int redDice = Die.roll();
        int whiteDice = Die.roll();
        redDie.setIcon(new ImageIcon(GUI.RED_DIE[redDice - 1]));
        whiteDie.setIcon(new ImageIcon(GUI.WHITE_DIE[whiteDice - 1]));
        diceRoll = redDice + whiteDice;
        gui.getConsole().println("You rolled a " + diceRoll);
        return diceRoll;
    }

    /**
     * Gets the cards selected from the hand
     * @return  The cards selected in hand
     */
    public java.util.List<Type> getSelected(){
        return list.getSelectedValuesList();
    }

    /**
     * Gets the dice rolled
     * @return  The dice roll
     */
    public int getDiceRoll() {
        return diceRoll;
    }
}
