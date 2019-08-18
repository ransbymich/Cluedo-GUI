package Cluedo.GUI;

import Cluedo.Board;

import javax.swing.*;
import java.awt.*;

public class CluedoCanvas extends JPanel {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;

    private Board board;

    public CluedoCanvas(Board board) {
        this.board = board;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }
}
