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

        console = new JTextArea(5, 20);
//        console.setPreferredSize(new Dimension(250, 800));
        console.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(console);
        scrollPane.setPreferredSize(new Dimension(250, 800));

        this.add(scrollPane, BorderLayout.NORTH);
        input = new JTextField();

        this.add(input);
        input.addActionListener(this);

        console.setBackground(Color.BLACK);
        console.setForeground(Color.WHITE);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();

        println(">" + command);
        if(inputConsumer != null){
            inputConsumer.accept(command);
        }else{
            processCommand(command);
        }

        input.setText("");
    }

    public void setInputConsumer(Consumer<String> inputConsumer) {
        this.inputConsumer = inputConsumer;
    }

    private void processCommand(String command){
        switch (command.toLowerCase()){
            case "move":
                processMove();
                break;

        }
    }

    public void clearText(){
        console.setText("");
    }

    private void processMove(){
        if(board.getState() != MOVE && board.getState() != SUGGEST_MOVE){
            println("Unable to move right now.");
            return;
        }

        int diceRoll = gui.getInfoPanel().changeDice();

        println("Entered the coordinate to go to: ");

        this.setInputConsumer((s)->{

            if(!Pattern.matches(InputUtil.COORDINATE_REGEX, s)){
                println("Input does not match an option: " + InputUtil.COORDINATE_REGEX);
                return;
            }

            Position movePosition = Position.positionFromString(s);

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

    public void println(Object o){
        console.append(o.toString() + "\n");
    }
}
