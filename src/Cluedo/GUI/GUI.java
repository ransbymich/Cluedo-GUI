package Cluedo.GUI;

import Cluedo.Helpers.Type;

import javax.swing.*;
import java.util.List;

public class GUI extends JFrame {

    public GUI(){
        new CharacterSelection(this::initalize);
    }


    private void initalize(List<Cluedo.Helpers.Type> players){

    }

    public static void main(String[] args) {
        new GUI();
    }
}
