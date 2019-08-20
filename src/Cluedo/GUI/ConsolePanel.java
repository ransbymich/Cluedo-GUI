package Cluedo.GUI;

import Cluedo.Board;
import Cluedo.Helpers.Die;
import Cluedo.Helpers.Position;
import Cluedo.Helpers.State;
import Cluedo.Moves.GUIMove;
import Cluedo.Util.InputUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import static Cluedo.Helpers.State.MOVE;
import static Cluedo.Helpers.State.SUGGEST_MOVE;

/**
 * This class manages and represents the console seen on the right side of the UI
 */
public class ConsolePanel extends JPanel implements ActionListener {

    private Consumer<String> inputConsumer;

    private Board board;
    private GUI gui;

    private JTextArea console;
    private JTextField input;

    public ConsolePanel(Board board, GUI gui){
        super(new BorderLayout());

        this.board = board;
        this.gui = gui;

        //Create the console itself
        console = new JTextArea(5, 20);
        console.setEditable(false);

        //Create the scroll pane for the console
        JScrollPane scrollPane = new JScrollPane(console);
        scrollPane.setPreferredSize(new Dimension(250, 800));

        this.add(scrollPane, BorderLayout.NORTH);

        //Create and add the input text field at the bottom of the console
        input = new JTextField();

        this.add(input);
        input.addActionListener(this);

        console.setBackground(Color.BLACK);
        console.setForeground(Color.WHITE);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();

        //Add the command typed into the input to the console
        println(">" + command);
        if(inputConsumer != null){
            inputConsumer.accept(command);
        }else{
            processCommand(command);
        }

        input.setText("");
    }

    /**
     * Sets the input consumer for all inputs into the console, used for when waiting on an input from the user
     * @param inputConsumer
     */
    public void setInputConsumer(Consumer<String> inputConsumer) {
        this.inputConsumer = inputConsumer;
    }

    /**
     * Proccesses a move typed into the console
     * @param command
     */
    private void processCommand(String command){
        switch (command.toLowerCase()){
            case "move":
                processMove();
                break;

        }
    }

    /**
     * Clears the text of the console
     */
    public void clearText(){
        console.setText("");
    }

    /**
     * Proccesses a move command, ensures that the states are met
     */
    private void processMove(){
        if(board.getState() != MOVE && board.getState() != SUGGEST_MOVE){
            println("Unable to move right now.");
            return;
        }

        //Roll the dice and update UI
        int diceRoll = gui.getInfoPanel().changeDice();

        println("Entered the coordinate to go to: ");

        //Set the input consumer to catch whatever the user inputs
        this.setInputConsumer((s)->{

            if(!Pattern.matches(InputUtil.COORDINATE_REGEX, s)){
                println("Input does not match an option: " + InputUtil.COORDINATE_REGEX);
                return;
            }

            Position movePosition = Position.positionFromString(s);

            //On a successful input process the coordinate to move to, and if it was successful then update the UI
            if(board.processTurn(new GUIMove(movePosition, diceRoll, this))){
                this.setInputConsumer(null);
                gui.getCanvas().repaint();
                gui.getInfoPanel().update();
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 825);
    }

    /**
     * Prints an object to the console followed by a new line character
     * @param o The object to print to console
     */
    public void println(Object o){
        console.append(o.toString() + "\n");
    }
}
