package Cluedo.GUI;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import Cluedo.Util.GUIUtil;
import Cluedo.Util.InputUtil;

/**
 * This class allows the user to select which characters within cluedo are playing in this game, once selection is complete
 * the passed consumer is then run with the list of players.
 */
public class CharacterSelection extends JFrame {

    private static final int MINIMUM_PLAYERS = 3;

    private Consumer<List<Cluedo.Helpers.Type>> onCompletion;
    private JPanel mPanel; //Main panel

    private Map<Cluedo.Helpers.Type, JRadioButton> radioButtons;

    public CharacterSelection(Consumer<List<Cluedo.Helpers.Type>> onCompletion){
        this.onCompletion = onCompletion; //Consumer for when character completion is complete

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Select Characters Playing");

        initialize();

        this.pack();
    }

    /**
     * Initializes the UI
     */
    private void initialize(){
        mPanel = new JPanel(new GridBagLayout());
        this.add(mPanel);

        radioButtons = new HashMap<>();

        //Generate all of the radio buttons, and add them to the map
        List<Cluedo.Helpers.Type> allPlayers = Cluedo.Helpers.Type.getTypes(Cluedo.Helpers.Type.SubType.PLAYER);
        for(int i = 0; i < allPlayers.size(); i++){    //Add all radio buttons to menu
            Cluedo.Helpers.Type player = allPlayers.get(i);
            JRadioButton rb = new JRadioButton(player.getName());

            mPanel.add(rb, GUIUtil.makeConstraints(0, i, 1, 1, GridBagConstraints.LINE_START));

            radioButtons.put(player, rb);
        }

        JButton completeBtn = new JButton("Finish");

        mPanel.add(completeBtn, GUIUtil.makeConstraints(0, allPlayers.size(), 1, 1, GridBagConstraints.CENTER));

        //Once user is done selecting players all of the a list is constructed containing all of those selected and then passed onto
        //the consumer. There is also a check to ensure that three or more people have actually been selected.
        completeBtn.addActionListener(actionEvent -> {
            List<Cluedo.Helpers.Type> players = new ArrayList<>();
            for(JRadioButton rb : radioButtons.values()){
                if(rb.isSelected()){
                    players.add(InputUtil.getTypeFromString(allPlayers, rb.getText().replaceAll("\\.", "").toLowerCase()));
                }
            }

            if(players.size() >= MINIMUM_PLAYERS){
                this.setVisible(false);
                this.dispose();
                onCompletion.accept(players);
            }else{
                JOptionPane.showMessageDialog(this, "Must have three or more players!");
            }
        });
    }
}
