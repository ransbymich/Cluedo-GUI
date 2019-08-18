package Cluedo.GUI;

import Cluedo.Board;
import Cluedo.Helpers.Type;
import Cluedo.Tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CluedoCanvas extends JPanel implements MouseListener {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;

    private Board board;

    public CluedoCanvas(Board board) {
        this.board = board;
        this.addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //board is in format [y][x]
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
