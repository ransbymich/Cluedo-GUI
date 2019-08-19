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
        JFrame suggestionFrame = new JFrame();
        JPanel panel = new JPanel();
        JList list = new JList();

        list.setCellRenderer((jList, type, i, b, b1) -> {
            JLabel jl = new JLabel();
            Image img = GUI.ASSETS.get(type);
            jl.setSize(img.getWidth(null), img.getHeight(null));
            jl.setIcon(new ImageIcon(GUI.ASSETS.get(type)));
            if(b){
                jl.setOpaque(true);
                jl.setBackground(Color.RED);
            }
            return jl;
        });

        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(1);

        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scrollPane = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(CluedoCanvas.WIDTH/2, 145));


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
