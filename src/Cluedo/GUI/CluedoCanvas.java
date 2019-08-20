package Cluedo.GUI;

import Cluedo.Board;
import Cluedo.GameObjects.Player;
import Cluedo.GameObjects.Room;
import Cluedo.Helpers.Position;
import Cluedo.Helpers.State;
import Cluedo.Moves.GUIMove;
import Cluedo.Tiles.EmptyTile;
import Cluedo.Tiles.DoorTile;
import Cluedo.Tiles.RoomTile;
import Cluedo.Tiles.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class handles all of the graphics part of the UI, drawing the cluedo board and the players on it.
 */
public class CluedoCanvas extends JPanel implements MouseListener{

    public static final int WIDTH = 625;        //Width of the canvas
    public static final int HEIGHT = 625;       //Height of the canvas

    public final static int TILE_SIZE = 23;     //The size of each tile
    public final static int Y_OFFSET = 23;       //The offset on the y axis for the background image
    public final static int X_OFFSET = 42;      //The offset on the x axis for the background image

    private static BufferedImage BOARD_IMAGE;

    static {
        try {
            BOARD_IMAGE = ImageIO.read(new File("Assets/tiles/cluedo_board.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load board image!");
            System.exit(0);
        }
    }

    private Board board;
    private GUI gui;

    public CluedoCanvas(Board board, GUI gui) {
        this.board = board;
        this.gui = gui;
        this.addMouseListener(this);
        this.setToolTipText("");
        ToolTipManager.sharedInstance().setInitialDelay(500);       //Set the tooltip delay to 0.5 seconds
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //render the image of the board
        //image of the board was taken from https://github.com/oisinq/Cluedo/blob/master/src/cluedo_board.jpg

        if (BOARD_IMAGE != null){
            g.drawImage(BOARD_IMAGE, 0, 0, this);
        }

        //render the image of each item which needs to be put ON TOP of the board

        for (Room room : board.getRooms().values()) {
            room.render(g);
        }

        for (Player player : board.getPlayerInstances()) {
            player.render(g);
        }
    }

    /**
     * Generates the text for the tooltip when needed based off it's position
     * @param mouseEvent    The mouse information
     * @return              The text for the toop tip
     */
    @Override
    public String getToolTipText(MouseEvent mouseEvent) {
        //Calculate the mouse positions x and y on the grid
        int x = (mouseEvent.getX() - X_OFFSET)/TILE_SIZE;
        int y = (mouseEvent.getY() - Y_OFFSET)/TILE_SIZE;
        //If it's not actually on the grid then don't show a tooltip
        if(x < 0 || x >= Board.BOARD_WIDTH || y < 0 || y >= Board.BOARD_HEIGHT) return null;
        Tile t = board.getBoard()[y][x];

        //Create the HTML for the tooltip, if the mouse is on an empty tile that has a player on it, then also put
        //the name of the player. If it's on a DoorTile or RoomTile then add the name of the room it represents.
        String tooltip = "<html>" + (x + 1) + "," + (char)(y + 'A');

        if(t instanceof EmptyTile && ((EmptyTile)t).getPlayer() != null){
            tooltip += "<br>" + ((EmptyTile)t).getPlayer().getName();
        }else if(t instanceof RoomTile){
            tooltip += "<br>" + ((RoomTile)t).getRoom().getType().getName();
        }else if(t instanceof DoorTile){
            tooltip += "<br>" + ((DoorTile)t).getRoom().getType().getName();
        }

        //Calculate the distance to the mouse from the current player
        Position playerPos = board.getCurrentPlayer().getPosition();
        int distance = Math.abs(x - playerPos.getX()) + Math.abs(y - playerPos.getY());

        tooltip += "<br>Distance : " + distance;

        return tooltip + "</html>";
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseEvent.BUTTON1 && board.getState() == State.MOVING){
            //Calculate the mouse x and y position on the grid
            int x = (mouseEvent.getX() - X_OFFSET)/TILE_SIZE;
            int y = (mouseEvent.getY() - Y_OFFSET)/TILE_SIZE;
            //If it's not on the grid then don't do anything
            if(x < 0 || x >= Board.BOARD_WIDTH || y < 0 || y >= Board.BOARD_HEIGHT) return;

            //Process the move turn, if it is successful then repaint the canvas and update the UI
            if(board.processTurn(new GUIMove(new Position(x, y), gui.getInfoPanel().getDiceRoll(), gui.getConsole()))){
               repaint();
               gui.getInfoPanel().update();
               gui.getConsole().setInputConsumer(null);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
