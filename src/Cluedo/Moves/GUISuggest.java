package Cluedo.Moves;

import Cluedo.Board;
import Cluedo.GUI.CluedoCanvas;
import Cluedo.GUI.ConsolePanel;
import Cluedo.GUI.GUI;
import Cluedo.GameObjects.Player;
import Cluedo.Helpers.State;
import Cluedo.Helpers.Type;

import javax.swing.*;
import java.awt.*;

public class GUISuggest extends Turn{
    private GUI gui;

    //remember to change the state after end of turn
    public GUISuggest(GUI gui, Type weapon, Type accused){
        this.gui = gui;
    }

    @Override
    public boolean execute(Board board) {
        State gameState = board.getState();

        if (gameState != State.SUGGEST_MOVE  && gameState != State.SUGGEST){
            gui.getConsole().println("You may not suggest right now!");
            return false;
        }

        Player cPlayer = board.getCurrentPlayer();
        return true;
    }
}
