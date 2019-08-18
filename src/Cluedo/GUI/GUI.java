package Cluedo.GUI;

import Cluedo.Board;
import Cluedo.Util.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

public class GUI extends JFrame implements WindowListener {

    private JPanel mPanel; //Main panel

    private Board board;

    private CluedoCanvas canvas;

    public GUI(){
        new CharacterSelection(this::initalize);
    }


    private void initalize(List<Cluedo.Helpers.Type> players){
        this.addWindowListener(this);
        this.setVisible(true);
        this.setTitle("Cluedo - Tim Salisbury, Mike Ransby");

        board = new Board(players);

        mPanel = new JPanel(new GridBagLayout());
        this.getContentPane().add(mPanel);

        initalizeMenu();
        initalizeCanvas();

        this.pack();
    }


    private void initalizeMenu(){
        JMenuBar menuBar = new JMenuBar();

        JMenu mainMenu = new JMenu("Menu");

        menuBar.add(mainMenu);
        this.setJMenuBar(menuBar);
    }

    private void initalizeCanvas(){
        canvas = new CluedoCanvas(board);

        mPanel.add(canvas, GUIUtil.makeConstraints(0, 0, 1, 1, GridBagConstraints.CENTER));

    }


    @Override
    public void windowOpened(WindowEvent windowEvent) {
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        int close = JOptionPane.showConfirmDialog(this, "Are you sure you want to close Cluedo?");
        if(close == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }

    public static void main(String[] args) {
        new GUI();
    }
}
