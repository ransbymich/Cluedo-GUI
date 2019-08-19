package Cluedo.Moves;

import Cluedo.Board;
import Cluedo.GUI.ConsolePanel;
import Cluedo.GUI.GUI;
import Cluedo.GameObjects.Player;
import Cluedo.Helpers.State;
import Cluedo.Helpers.Type;

import javax.swing.*;

public class GUISuggest extends Turn{
    private GUI gui;
    private Type weapon;
    private Type accused;
    private JFrame suggestionFrame;

    public GUISuggest(GUI gui, Type weapon, Type accused){
        this.gui = gui;
        this.weapon = weapon;
        this.accused = accused;
        this.suggestionFrame = new JFrame();


    }

    @Override
    public boolean execute(Board board) {
        State gameState = board.getState();

        if (gameState != State.SUGGEST_MOVE  && gameState != State.SUGGEST){
            gui.getConsole().println("You may not suggest right now!");
        }

        Player cPlayer = board.getCurrentPlayer();
        return true;
    }
}
