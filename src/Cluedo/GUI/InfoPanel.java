package Cluedo.GUI;

import Cluedo.Board;
import Cluedo.GameObjects.Player;
import Cluedo.Helpers.Die;
import Cluedo.Helpers.State;
import Cluedo.Helpers.Type;
import Cluedo.Helpers.TypeCellRender;
import Cluedo.Moves.GUISuggest;
import Cluedo.Moves.Suggest;
import Cluedo.Util.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

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

        this.add(pName, GUIUtil.makeConstraints(0, 0, 1, 1, GridBagConstraints.LINE_START));

        this.add(new JLabel("Hand"), GUIUtil.makeConstraints(0, 1, 1, 1, GridBagConstraints.LINE_START));

        list.setCellRenderer(new TypeCellRender());

        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(1);

        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scrollPane = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(CluedoCanvas.WIDTH/2, 145));

        this.add(scrollPane, GUIUtil.makeConstraints(0, 2, 2, 1, GridBagConstraints.CENTER));

        JPanel sidePanel = new JPanel(new GridBagLayout());

        this.add(sidePanel, GUIUtil.makeConstraints(3, 2, 1, 1, GridBagConstraints.CENTER));

        redDie = new JLabel(new ImageIcon(GUI.RED_DIE[0]));
        whiteDie = new JLabel(new ImageIcon(GUI.WHITE_DIE[0]));

        sidePanel.add(redDie, GUIUtil.makeConstraints(0, 0, 1, 1, GridBagConstraints.CENTER));
        sidePanel.add(whiteDie, GUIUtil.makeConstraints(0, 1, 1, 1, GridBagConstraints.CENTER));

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

        completeBtn.addActionListener((e)->{
            board.completeTurn();
            gui.getConsole().setInputConsumer(null);
            this.update();
        });

        suggestBtn.addActionListener((e)->{
            board.setState(State.SUGGESTING);
            update();
            new SuggestWindow(gui, board);
        });

        moveBtn.addActionListener(this::processMove);

        stateLabel = new JLabel();

        sidePanel.add(stateLabel, GUIUtil.makeConstraints(2, 0, 1, 1, GridBagConstraints.CENTER));

        update();
    }

    public boolean checkAssumptions(List<Type> cards){
        if (cards.size() != 2) {
            gui.getConsole().println("Must select exactly two cards..\n Invalid Move!");
            return false;
        }
        List<Type> weapons = cards.stream().filter(t -> t.getSubType() == Type.SubType.WEAPON).collect(Collectors.toList());
        List<Type> players = cards.stream().filter(t -> t.getSubType() == Type.SubType.PLAYER).collect(Collectors.toList());

        if (weapons.size() != 1 || players.size() != 1){
            gui.getConsole().println("Cards invalid.\n Invalid Move!");
            return false;
        }

        return true;
    }

    public void processMove(ActionEvent e){
        if(board.getState() == State.MOVE || board.getState() == State.SUGGEST_MOVE){
            changeDice();
            board.setState(State.MOVING);
            update();
        }
    }


    public void update(){
        Player player = board.getCurrentPlayer();
        this.pName.setText(player.getName());
        this.list.setListData(player.getHand().toArray(new Type[0]));

        //changed this for easy debug
        switch (board.getState()) {
            case END_TURN:
                suggestBtn.setEnabled(false);//false
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
                suggestBtn.setEnabled(false);//false
                accuseBtn.setEnabled(true);
                moveBtn.setEnabled(false);
                completeBtn.setEnabled(true);
                break;
            case MOVE:
                suggestBtn.setEnabled(false);//false
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
                suggestBtn.setEnabled(false);
                accuseBtn.setEnabled(false);
                moveBtn.setEnabled(false);
                completeBtn.setEnabled(false);
                break;
        }
    }

    public int changeDice(){
        //TODO: Player can reroll dice
        int redDice = Die.roll();
        int whiteDice = Die.roll();
        redDie.setIcon(new ImageIcon(GUI.RED_DIE[redDice - 1]));
        whiteDie.setIcon(new ImageIcon(GUI.WHITE_DIE[whiteDice - 1]));
        diceRoll = redDice + whiteDice;
        gui.getConsole().println("You rolled a " + diceRoll);
        return diceRoll;
    }

    public java.util.List<Type> getSelected(){
        return list.getSelectedValuesList();
    }

    public int getDiceRoll() {
        return diceRoll;
    }
}
