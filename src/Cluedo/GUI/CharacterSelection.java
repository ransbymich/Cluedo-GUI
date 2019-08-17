package Cluedo.GUI;


import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

import Cluedo.Helpers.Type;


public class CharacterSelection extends JFrame {

    private Consumer<List<Type>> onCompletion;
    private JPanel mPanel; //Main panel

    public CharacterSelection(Consumer<List<Type>> onCompletion){
        this.onCompletion = onCompletion; //Consumer for when character completion is complete

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Select Characters Playing");

        initalize();
    }

    private void initalize(){
        mPanel = new JPanel(new GridBagLayout());
        this.add(mPanel);

        List<Cluedo.Helpers.Type> players = Cluedo.Helpers.Type.getTypes(Cluedo.Helpers.Type.SubType.PLAYER);
//        for(int i = 0; i < )

    }
}
