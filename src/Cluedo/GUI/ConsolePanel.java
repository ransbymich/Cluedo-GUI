package Cluedo.GUI;

import Cluedo.Board;
import Cluedo.Helpers.Die;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PrintStream;

public class ConsolePanel extends JPanel implements ActionListener {

    private Board board;
    private GUI gui;

    private JTextArea console;
    private JTextField input;

    public ConsolePanel(Board board, GUI gui){
        super(new BorderLayout());

        this.board = board;
        this.gui = gui;

        console = new JTextArea(5, 20);
        console.setPreferredSize(new Dimension(250, 800));
        console.setEditable(false);
        this.add(console, BorderLayout.NORTH);
        input = new JTextField();

        this.add(input);
        input.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();

        println(">" + command);
        processCommand(command);


        input.setText("");
    }

    private void processCommand(String command){
        switch (command.toLowerCase()){
            case "move":
                processMove();
                break;

        }
    }

    private void processMove(){
        gui.getInfoPanel().changeDice(Die.roll(), Die.roll());
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 825);
    }

    public void println(Object o){
        console.append(o.toString() + "\n");
    }
}
