package Cluedo.GUI;

import Cluedo.Board;
import Cluedo.Helpers.Type;
import Cluedo.Util.GUIUtil;
import Cluedo.Util.InputUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUI extends JFrame implements WindowListener {

    public static Map<Cluedo.Helpers.Type, Image> ASSETS;
    static {
        ASSETS = new HashMap<>();
        File assetDir = new File("Assets/");
        File[] files = assetDir.listFiles(file -> !file.isDirectory() && file.getName().contains(".jpg"));

        assert files != null;
        for(File file : files){
            try{
                Cluedo.Helpers.Type type = InputUtil.getTypeFromString(Cluedo.Helpers.Type.getTypes(), file.getName().replaceFirst("[.][^.]+$", "").toLowerCase());
                Image img = ImageIO.read(file);
                ASSETS.put(type, img.getScaledInstance(75, 100, Image.SCALE_SMOOTH));
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    public final static int TILE_SIZE = 30;

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

        initalizeMenu();

        mPanel = new JPanel(new GridBagLayout());

        initalizeCanvas();

        initalizeInfo();

        this.getContentPane().add(mPanel);

        this.pack();
    }

    private void initalizeInfo(){
        JPanel infoPanel = new InfoPanel(board);

        mPanel.add(infoPanel, GUIUtil.makeConstraints(0, 1, 1, 1, GridBagConstraints.LINE_START));


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
