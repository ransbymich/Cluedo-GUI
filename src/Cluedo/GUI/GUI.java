package Cluedo.GUI;

import Cluedo.Board;
import Cluedo.Helpers.State;
import Cluedo.Util.GUIUtil;
import Cluedo.Util.InputUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUI extends JFrame implements WindowListener {

    public static Map<Cluedo.Helpers.Type, Image> ASSETS;   //Stores all of the assets used in the UI
    public static Image[] RED_DIE;
    public static Image[] WHITE_DIE;
    static {
        //First, load all of the assets
        ASSETS = new HashMap<>();
        File assetDir = new File("Assets/cards");
        File[] files = assetDir.listFiles(file -> !file.isDirectory() && file.getName().contains(".png"));  //Filter out potentially useless files

        assert files != null;
        for(File file : files){
            try{    //Finally actually load the images
                Cluedo.Helpers.Type type = InputUtil.getTypeFromString(Cluedo.Helpers.Type.getTypes(), file.getName().replaceFirst("[.][^.]+$", "").toLowerCase());
                Image img = ImageIO.read(file);
                ASSETS.put(type, img.getScaledInstance(100, 125, Image.SCALE_SMOOTH));
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        //Loads the die images
        RED_DIE = new Image[6];
        WHITE_DIE = new Image[6];

        String dicePath = "Assets/dice";
        for(int i = 0; i < 6; i++){

            try{
                RED_DIE[i] = ImageIO.read(new File(dicePath + "/red-" + (i + 1) + ".png"));
                WHITE_DIE[i] = ImageIO.read(new File(dicePath + "/white-" + (i + 1) + ".png"));
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    private JPanel mPanel; //Main panel

    private Board board;

    private CluedoCanvas canvas;
    private ConsolePanel console;
    private InfoPanel infoPanel;
    private GUI gui;

    public GUI(){
        //On construction make the user select playing characters and then run the initialize function
        new CharacterSelection(this::initialize);
    }

    /**
     * Initializes the UI, gets passed a list of players currently paying
     * @param players
     */
    private void initialize(List<Cluedo.Helpers.Type> players){
        try{
            //General UI setup
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        }catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e){
            e.printStackTrace();
        }
        this.gui = this;
        this.addWindowListener(this);
        this.setTitle("Cluedo - Tim Salisbury, Mike Ransby");

        board = new Board(players);

        initializeMenu();

        mPanel = new JPanel(new GridBagLayout());

        initializeCanvas();

        initializeInfo();

        initializeConsole();

        initializesKeybindings();

        this.getContentPane().add(mPanel);

        this.pack();
        this.setVisible(true);
        this.setResizable(false);
    }

    /**
     * Initializes the console panel on the right side of the UI
     */
    private void initializeConsole(){
        console = new ConsolePanel(board, this);

        mPanel.add(console, GUIUtil.makeConstraints(1, 0, 1, 2, GridBagConstraints.CENTER));
    }

    /**
     * Initializes the information panel seen at the bottom of the UI
     */
    private void initializeInfo(){
        infoPanel = new InfoPanel(board, this);

        mPanel.add(infoPanel, GUIUtil.makeConstraints(0, 1, 1, 1, GridBagConstraints.LINE_START));
    }

    /**
     * Initializes the menu bar at the top of the screen
     */
    private void initializeMenu(){
        JMenuBar menuBar = new JMenuBar();

        JMenu mainMenu = new JMenu("Menu");
        JMenuItem reset = new JMenuItem("Reset");
//        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem quit = new JMenuItem("Quit");
        mainMenu.add(reset);
//        mainMenu.add(newGame);
        mainMenu.add(quit);

        reset.addActionListener((l)->{
            board.resetGame();
            canvas.repaint();
            infoPanel.update();
            getConsole().clearText();
        });

//        newGame.addActionListener((l)->{
//            this.setVisible(false);
//            board.resetGame();
//            canvas.repaint();
//            infoPanel.update();
//            getConsole().clearText();
//            new CharacterSelection(this::initialize);
//        });

        quit.addActionListener((l)-> System.exit(0));

        menuBar.add(mainMenu);
        this.setJMenuBar(menuBar);
    }

    /**
     * Initializes the graphics canvas for the UI
     */
    private void initializeCanvas(){
        canvas = new CluedoCanvas(board, this);

        mPanel.add(canvas, GUIUtil.makeConstraints(0, 0, 1, 1, GridBagConstraints.CENTER));
    }

    private void initializesKeybindings(){
        InputMap inputMap = mPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("C"), "completeTurn");
        inputMap.put(KeyStroke.getKeyStroke("M"), "move");
        inputMap.put(KeyStroke.getKeyStroke("S"), "suggest");
        inputMap.put(KeyStroke.getKeyStroke("A"), "accuse");

        ActionMap actionMap = mPanel.getActionMap();
        actionMap.put("completeTurn", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(board.getState() != State.GAME_FINISHED){
                    board.completeTurn();
                    infoPanel.update();
                    console.println("Completed Turn.");
                }
            }
        });
        actionMap.put("move", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(board.getState() == State.MOVE || board.getState() == State.SUGGEST_MOVE){
                    infoPanel.changeDice();
                    board.setState(State.MOVING);
                    infoPanel.update();
                }
            }
        });
        actionMap.put("suggest", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(board.getState() == State.SUGGEST || board.getState() == State.SUGGEST_MOVE){
                    board.setState(State.SUGGESTING);
                    infoPanel.update();
                    new SuggestWindow(gui, board, true);
                }
            }
        });
        actionMap.put("accuse", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(board.getState() == State.ACCUSE){
                    board.setState(State.ACCUSING);
                    infoPanel.update();
                    new SuggestWindow(gui, board, false);
                }
            }
        });
    }

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    public CluedoCanvas getCanvas() {
        return canvas;
    }

    public ConsolePanel getConsole() {
        return console;
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
