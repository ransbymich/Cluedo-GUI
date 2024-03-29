package Cluedo.GUI;


import Cluedo.Board;
import Cluedo.GameObjects.Player;
import Cluedo.Helpers.State;
import Cluedo.Helpers.TypeCellRender;
import Cluedo.Util.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class creates and processes the refuting process of a suggestion
 */
public class RefuteWindow extends JFrame {

    private JPanel mPanel; //Main panel
    private GUI gui;
    private Board board;
    private ConsolePanel cp;

    private Cluedo.Helpers.Type room;
    private Cluedo.Helpers.Type accused;
    private Cluedo.Helpers.Type weapon;

    private Player refutingPlayer;

    private JList<Cluedo.Helpers.Type> refutingCards;

    public RefuteWindow(GUI gui, Cluedo.Helpers.Type room, Cluedo.Helpers.Type accused, Cluedo.Helpers.Type weapon, Board board){
        this.gui = gui;
        this.room = room;
        this.accused = accused;
        this.weapon = weapon;
        this.board = board;
        this.cp = gui.getConsole();

        this.mPanel = new JPanel(new GridBagLayout());

        initialize();

        this.setContentPane(mPanel);

        this.pack();

        this.setVisible(true);
    }

    private void initialize(){
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        java.util.List<Player> players = Cluedo.Helpers.Type.getTypes(Cluedo.Helpers.Type.SubType.PLAYER).stream().filter(board::hasPlayer).map(board::getPlayer).collect(Collectors.toList());

        Player cPlayer = board.getCurrentPlayer();

        Set<Cluedo.Helpers.Type> suggestion = new HashSet<>(Arrays.asList(room, accused, weapon));

        refutingPlayer = null;

        //Get the player that can actually refute
        for(Player p : players){
            if(p.getType() == cPlayer.getType()) continue;

            Set<Cluedo.Helpers.Type> hand = new HashSet<>(p.getHand());

            hand.retainAll(suggestion);

            java.util.List<Cluedo.Helpers.Type> refutationCards = new ArrayList<>(hand);

            if(refutationCards.size() != 0) {
                refutingPlayer = p;
                refutingCards = new JList<>();
                refutingCards.setListData(refutationCards.toArray(new Cluedo.Helpers.Type[0]));
                break;
            }
        }

        //Set up the JLists that show the cards that can be refuted with
        JScrollPane sp = new JScrollPane(refutingCards, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        sp.setPreferredSize(new Dimension(200, 145));

        refutingCards.setCellRenderer(new TypeCellRender());
        refutingCards.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        refutingCards.setVisibleRowCount(1);

        mPanel.add(new JLabel(refutingPlayer.getName()), GUIUtil.makeConstraints(0, 0, 1, 1, GridBagConstraints.CENTER));

        mPanel.add(sp, GUIUtil.makeConstraints(0, 1, 1, 1, GridBagConstraints.CENTER));

        refutingCards.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Set up the completion button
        JButton refuteBtn = new JButton("Refute");

        mPanel.add(refuteBtn, GUIUtil.makeConstraints(0, 2, 1, 1, GridBagConstraints.CENTER));

        refuteBtn.addActionListener(this::refute);

        refuteBtn.setBackground(Color.RED);
    }

    /**
     * Processes the refutation
     * @param e The action event related to the button press
     */
    private void refute(ActionEvent e){
        if(refutingCards.getSelectedValue() == null){
            cp.println("You must select a card to refute with!");
        }else{
            Cluedo.Helpers.Type refutingCard = refutingCards.getSelectedValue();
            cp.println(refutingPlayer.getName() + " refutes the suggestion with " + refutingCard.getName());
            board.setState(State.END_TURN);
            this.setVisible(false);
            this.dispose();
        }
    }
}
