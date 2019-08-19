package Cluedo.GUI;

import Cluedo.Board;
import Cluedo.GameObjects.Player;
import Cluedo.GameObjects.Room;
import Cluedo.Helpers.Die;
import Cluedo.Helpers.Position;
import Cluedo.Helpers.State;
import Cluedo.Helpers.Type;
import Cluedo.Moves.GUIMove;
import Cluedo.Tiles.EmptyTile;
import Cluedo.Tiles.EntryTile;
import Cluedo.Tiles.RoomTile;
import Cluedo.Tiles.Tile;
import com.sun.source.doctree.EndElementTree;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CluedoCanvas extends JPanel implements MouseListener {

    public static final int WIDTH = 625;
    public static final int HEIGHT = 650;

    public final static int TILE_SIZE = 23;
    public final static int yOffset = 23;
    public final static int xOffset = 42;

    private static BufferedImage boardImg = null;

    static {
        try {
            boardImg = ImageIO.read(new File("Assets/tiles/cluedo_board.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Board board;
    private GUI gui;

    public CluedoCanvas(Board board, GUI gui) {
        this.board = board;
        this.gui = gui;
        this.addMouseListener(this);
        this.setToolTipText("");
        ToolTipManager.sharedInstance().setInitialDelay(1000);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        //render the image of the board
        //image of the board was taken from https://github.com/oisinq/Cluedo/blob/master/src/cluedo_board.jpg

        if (boardImg != null){
            g.drawImage(boardImg, 0, 0, this);
        }

        //render the image of each item which needs to be put ON TOP of the board
//        Tile[][] tiles = board.getBoard();
//        for (int y = 0; y < tiles.length; y++){
//            for (int x = 0; x < tiles[0].length; x++){
//                tiles[y][x].render(g);
//            }
//        }

        for (Room room : board.getRooms().values()) {
            room.render(g);
        }

        for (Player player : board.getPlayerInstances()) {
            player.render(g);
        }
    }

    @Override
    public String getToolTipText(MouseEvent mouseEvent) {
        int x = (mouseEvent.getX() - xOffset)/TILE_SIZE;
        int y = (mouseEvent.getY() - yOffset)/TILE_SIZE;
        if(x < 0 || x >= Board.BOARD_WIDTH || y < 0 || y >= Board.BOARD_HEIGHT) return null;
        Tile t = board.getBoard()[y][x];

        String tooltip = "<html>" + (x + 1) + "," + (char)(y + 'A');

        if(t instanceof EmptyTile && ((EmptyTile)t).getPlayer() != null){
            tooltip += "<br>" + ((EmptyTile)t).getPlayer().getName();
        }else if(t instanceof RoomTile){
            tooltip += "<br>" + ((RoomTile)t).getRoom().getType().getName();
        }else if(t instanceof EntryTile){
            tooltip += "<br>" + ((EntryTile)t).getRoom().getType().getName();
        }

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
        if(mouseEvent.getButton() == MouseEvent.BUTTON1 && board.getState() == State.MOVE){
            int x = (mouseEvent.getX() - xOffset)/TILE_SIZE;
            int y = (mouseEvent.getY() - yOffset)/TILE_SIZE;
            if(x < 0 || x >= Board.BOARD_WIDTH || y < 0 || y >= Board.BOARD_HEIGHT) return;

            if(board.processTurn(new GUIMove(new Position(x, y), gui.getInfoPanel().getDiceRoll(), gui.getConsole()))){
               repaint();
               gui.getInfoPanel().update();
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
