package Cluedo.GUI;

import javax.swing.*;

public class GUI extends JFrame {


    public GUI(){
        new CharacterSelection((i)->{});
    }

    public static void main(String[] args) {
        new GUI();
    }
}
