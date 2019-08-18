package Cluedo.GUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import Cluedo.Helpers.Type.*;
import Cluedo.Util.GUIUtil;
import Cluedo.Util.InputUtil;


public class CharacterSelection extends JFrame {

    private static final int MINIMUM_PLAYERS = 3;

    private Consumer<List<Cluedo.Helpers.Type>> onCompletion;
    private JPanel mPanel; //Main panel

    private Map<Cluedo.Helpers.Type, JRadioButton> radioButtions;

    public CharacterSelection(Consumer<List<Cluedo.Helpers.Type>> onCompletion){
        this.onCompletion = onCompletion; //Consumer for when character completion is complete

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Select Characters Playing");

        initalize();

        this.pack();
    }

    private void initalize(){
        mPanel = new JPanel(new GridBagLayout());
        this.add(mPanel);

        radioButtions = new HashMap<>();

        List<Cluedo.Helpers.Type> allPlayers = Cluedo.Helpers.Type.getTypes(Cluedo.Helpers.Type.SubType.PLAYER);
        for(int i = 0; i < allPlayers.size(); i++){    //Add all radio buttons to menu
            Cluedo.Helpers.Type player = allPlayers.get(i);
            JRadioButton rb = new JRadioButton(player.getName());

            mPanel.add(rb, GUIUtil.makeConstraints(0, i, 1, 1, GridBagConstraints.LINE_START));

            radioButtions.put(player, rb);
        }

        JButton completeBtn = new JButton("Finish");

        mPanel.add(completeBtn, GUIUtil.makeConstraints(0, allPlayers.size(), 1, 1, GridBagConstraints.CENTER));

        completeBtn.addActionListener(actionEvent -> {
            List<Cluedo.Helpers.Type> players = new ArrayList<>();
            for(JRadioButton rb : radioButtions.values()){
                if(rb.isSelected()){
                    players.add(InputUtil.getTypeFromString(allPlayers, rb.getText().replaceAll("\\.", "").toLowerCase()));
                }
            }

            if(players.size() >= MINIMUM_PLAYERS){
                this.setVisible(false);
                onCompletion.accept(players);
            }else{
                JOptionPane.showMessageDialog(this, "Must have three or more players!");
            }
        });
    }
}
