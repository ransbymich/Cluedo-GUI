package Cluedo.GUI;

import Cluedo.Board;
import Cluedo.Helpers.Type;
import Cluedo.Tiles.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CluedoCanvas extends JPanel implements MouseListener {

    public static final int WIDTH = 650;
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

    public CluedoCanvas(Board board) {
        this.board = board;
        this.addMouseListener(this);
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
        Tile[][] tiles = board.getBoard();
        for (int y = 0; y < tiles.length; y++){
            for (int x = 0; x < tiles[0].length; x++){
                tiles[y][x].render(g);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int x = (mouseEvent.getX() - xOffset)/TILE_SIZE;
        int y = (mouseEvent.getY() - yOffset)/TILE_SIZE;

        System.out.println(x + " : " + y);
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
