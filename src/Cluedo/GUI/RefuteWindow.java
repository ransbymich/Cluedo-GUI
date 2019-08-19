package Cluedo.GUI;

import Cluedo.Board;
import Cluedo.GameObjects.Player;
import Cluedo.Helpers.State;
import Cluedo.Helpers.Type;
import Cluedo.Helpers.TypeCellRender;
import Cluedo.Util.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RefuteWindow extends JFrame {

    private JPanel mPanel; //Main panel
    private GUI gui;
    private Board board;
    private ConsolePanel cp;

    private Cluedo.Helpers.Type room;
    private Cluedo.Helpers.Type accused;
    private Cluedo.Helpers.Type weapon;

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
        java.util.List<Player> players = Cluedo.Helpers.Type.getTypes(Cluedo.Helpers.Type.SubType.PLAYER).stream().filter(board::hasPlayer).map(board::getPlayer).collect(Collectors.toList());

        Player cPlayer = board.getCurrentPlayer();

        Set<Cluedo.Helpers.Type> suggestion = new HashSet<>(Arrays.asList(room, accused, weapon));

        Player refutingPlayer;

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

        JScrollPane sp = new JScrollPane(refutingCards, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        sp.setPreferredSize(new Dimension(250, 145));

        refutingCards.setCellRenderer(new TypeCellRender());
        refutingCards.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        refutingCards.setVisibleRowCount(1);

        mPanel.add(sp, GUIUtil.makeConstraints(0, 0, 1, 1, GridBagConstraints.CENTER));

        refutingCards.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton refuteBtn = new JButton("Refute");

        mPanel.add(refuteBtn, GUIUtil.makeConstraints(0, 1, 1, 1, GridBagConstraints.CENTER));
    }
}
