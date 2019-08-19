package Cluedo.GUI;

import Cluedo.Board;
import Cluedo.GameObjects.Player;
import Cluedo.Helpers.Die;
import Cluedo.Helpers.State;
import Cluedo.Helpers.Type;
import Cluedo.Util.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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

    private int diceRoll = 2;


    public InfoPanel(Board board, GUI gui) {
        super(new GridBagLayout());
        this.board = board;
        this.gui = gui;
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

        JPanel sidePanel = new JPanel(new GridBagLayout());

        this.add(sidePanel, GUIUtil.makeConstraints(3, 2, 1, 1, GridBagConstraints.CENTER));

        redDie = new JLabel(new ImageIcon(GUI.RED_DIE[0]));
        whiteDie = new JLabel(new ImageIcon(GUI.WHITE_DIE[0]));

        sidePanel.add(redDie, GUIUtil.makeConstraints(0, 0, 1, 1, GridBagConstraints.CENTER));
        sidePanel.add(whiteDie, GUIUtil.makeConstraints(0, 1, 1, 1, GridBagConstraints.CENTER));

        JPanel btnPanel = new JPanel(new GridBagLayout());

        sidePanel.add(btnPanel, GUIUtil.makeConstraints(1, 0, 1, 2, GridBagConstraints.CENTER));

        JButton completeBtn = new JButton("Complete Turn");
        suggestBtn = new JButton("Suggest");
        accuseBtn = new JButton("Accuse");
        moveBtn = new JButton("Move");

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

        moveBtn.addActionListener(this::processMove);

        stateLabel = new JLabel();

        sidePanel.add(stateLabel, GUIUtil.makeConstraints(2, 0, 1, 1, GridBagConstraints.CENTER));

        update();
    }

    public void processMove(ActionEvent e){
        if(board.getState() == State.MOVE || board.getState() == State.SUGGEST_MOVE){
            changeDice();
            board.setState(State.MOVE);
            update();
        }
    }


    public void update(){
        Player player = board.getCurrentPlayer();
        this.pName.setText(player.getName());
        this.list.setListData(player.getHand().toArray(new Type[0]));

        switch (board.getState()) {
            case END_TURN:
                suggestBtn.setEnabled(false);
                accuseBtn.setEnabled(false);
                moveBtn.setEnabled(false);
                break;
            case SUGGEST:
                suggestBtn.setEnabled(true);
                accuseBtn.setEnabled(false);
                moveBtn.setEnabled(false);
                break;
            case ACCUSE:
                suggestBtn.setEnabled(false);
                accuseBtn.setEnabled(true);
                moveBtn.setEnabled(false);
                break;
            case MOVE:
                suggestBtn.setEnabled(false);
                accuseBtn.setEnabled(false);
                moveBtn.setEnabled(true);
                break;
            case SUGGEST_MOVE:
                suggestBtn.setEnabled(true);
                accuseBtn.setEnabled(false);
                moveBtn.setEnabled(true);
                break;
        }
    }

    public int changeDice(){
        int redDice = Die.roll();
        int whiteDice = Die.roll();
        redDie.setIcon(new ImageIcon(GUI.RED_DIE[redDice - 1]));
        whiteDie.setIcon(new ImageIcon(GUI.WHITE_DIE[whiteDice - 1]));
        diceRoll = redDice + whiteDice;
        gui.getConsole().println("You rolled a " + diceRoll);
        return diceRoll;
    }

    public int getDiceRoll() {
        return diceRoll;
    }
}
