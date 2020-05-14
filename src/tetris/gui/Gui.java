package tetris.gui;

import tetris.game.BoardCell;
import tetris.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Gui extends JFrame {
    private Game game;
    private final int CORNER = 5;
    private static final int BLOCK_WIDTH = 20;

    JPanel statistieken;
    GamePanel gamescherm;

    public Gui(Game game){
        this.game = game;
        setSize(420, 450);
        setLayout(new BorderLayout());
        setTitle("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        gamescherm = new GamePanel(this);
        add(gamescherm, BorderLayout.WEST);

        statistieken = new JPanel();
        add(statistieken, BorderLayout.CENTER);
        statistieken.setLayout(new BoxLayout(statistieken, BoxLayout.PAGE_AXIS));
        statistieken.setBackground(Color.lightGray);
        //statistieken.add(new JLabel("SPELER: "));
        //statistieken.add(new JLabel("SCORE: "));
        statistieken.add(new JLabel("PAUZE: DEK LDR AF"));

        setResizable(false);
    }

    public void draw(Graphics2D g) {
        drawEmptyBoard(g);
        drawCells(g);
    }


    public void drawCells(Graphics2D g) {
        BoardCell[][] cells = game.getBoardCells();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                BoardCell cell = cells[i][j];

                if(cell.isEmpty()) {
                    drawBlock(g, CORNER + i * 20, CORNER + (19 - j) * 20, Color.BLACK);
                } else {
                    //drawBlock(g, CORNER + i * 20, CORNER + (19 - j) * 20, getBlockSprite(cell.getBlockType()));
                }
            }
        }
    }

    public void drawEmptyBoard(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 800, 500);
        g.setColor(Color.GRAY);
        g.drawRect(CORNER - 1, CORNER - 1, 10 * BLOCK_WIDTH + 2, 20 * BLOCK_WIDTH + 2);
    }

    public void drawBlock(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        g.fillRect(x, y, BLOCK_WIDTH, BLOCK_WIDTH);
        g.drawRect(x, y, BLOCK_WIDTH, BLOCK_WIDTH);
    }

    public void drawBlock(Graphics g, int x, int y, BufferedImage sprite) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(sprite, x, y, null);
    }
}
