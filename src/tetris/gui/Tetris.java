package tetris.gui;

import tetris.game.BlockType;
import tetris.game.BoardCell;
import tetris.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

public class Tetris extends Canvas implements MouseListener {
    private Game game = new Game();
    // zorgt voor memory management van het canvas
    private final BufferStrategy strategy;

    private final int CORNER = 5;

    private static final int BLOCK_WIDTH = 20;

    private long lastIteration = System.currentTimeMillis();


    public Tetris() {
        JFrame container = new JFrame("Tetris");
        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(220, 600));
        panel.setLayout(null);

        setBounds(0, 0, 800, 600);
        panel.add(this);


        container.pack();
        container.setResizable(false);
        container.setVisible(true);

        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addMouseListener(this);

        createBufferStrategy(2);
        strategy = getBufferStrategy();
    }

    public static void main(String[] args) {
        new Tetris().gameLoop();
    }

    // gameLoop blijft status game checken
    void gameLoop() {
        while (true) {
            if(game.isPlaying()) {
                tetrisLoop();
            }
            // slow down game loop
            try {
                Thread.sleep(20);
            } catch (Exception e) { }
            draw();
        }
    }

    void tetrisLoop() {
        // TODO: rotate, left, right, drop
        if (game.isDropping()) {
            game.moveDown();
        } else if(System.currentTimeMillis() - lastIteration >= game.getIterationDelay()) {
            game.moveDown();
            lastIteration = System.currentTimeMillis();
        }
    }

    private Graphics2D getGameGraphics() {
        return (Graphics2D) strategy.getDrawGraphics();
    }

    public void draw() {
        Graphics2D g = getGameGraphics();
        drawEmptyBoard(g);

        if(!game.isPlaying()) {
            drawStartGameButton(g);
        }

        if(game.isPlaying()) {
            drawCells(g);
        }

        g.dispose();
        strategy.show();
    }

    private void drawCells(Graphics2D g) {
        BoardCell[][] cells = game.getBoardCells();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                drawBlock(g, CORNER + i * 20, CORNER + (19 - j) * 20, getBoardCellColor(cells[i][j]));
            }
        }
    }

    private Color getBoardCellColor(BoardCell boardCell) {
        if (boardCell.isEmpty()) {
            return Color.BLACK;
        }
        return getBlockColor(boardCell.getBlockType());
    }

    private Color getBlockColor(BlockType blockType) {
        switch (blockType) {
            case I:
                return Color.RED;
            case J:
                return Color.GRAY;
            case L:
                return Color.CYAN;
            case O:
                return Color.BLUE;
            case S:
                return Color.GREEN;
            default:
                return Color.MAGENTA;
        }
    }

    private void drawStartGameButton(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.fillRect(65, 450, 100, 50);
        g.setColor(Color.BLACK);
        g.drawString("Start game", 85 - 1, 480 - 1);
    }

    private void drawEmptyBoard(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 800, 600);
        g.setColor(Color.GRAY);
        g.drawRect(CORNER - 1, CORNER - 1, 10 * BLOCK_WIDTH + 2, 20 * BLOCK_WIDTH + 2);
    }

    private void drawBlock(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        g.fillRect(x, y, BLOCK_WIDTH, BLOCK_WIDTH);
        g.drawRect(x, y, BLOCK_WIDTH, BLOCK_WIDTH);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!game.isPlaying() && e.getX() > 65 && e.getX() < 165 && e.getY() > 450 && e.getY() < 500) {
            game.startGame();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
