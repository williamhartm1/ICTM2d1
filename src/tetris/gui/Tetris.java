package tetris.gui;

import tetris.game.BlockType;
import tetris.game.BoardCell;
import tetris.game.Game;
import tetris.game.SpriteSheetLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tetris extends Canvas implements MouseListener {
    private Game game = new Game();
    // zorgt voor memory management van het canvas
    private final BufferStrategy strategy;

    private final int CORNER = 5;

    private static final int BLOCK_WIDTH = 20;

    private long lastIteration = System.currentTimeMillis();

    private SpriteSheetLoader sprites;


    public Tetris() throws IOException {
        JFrame container = new JFrame("Tetris");
        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(220, 600));
        panel.setLayout(null);

        sprites = new SpriteSheetLoader(20, 20,  6);

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

    public static void main(String[] args) throws IOException {
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

        if (game.leftIsPressed()){
            game.moveLeft();
        }

        if (game.rightIsPressed()){
            game.moveRight();
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
                 BoardCell cell = cells[i][j];

                if(cell.isEmpty()) {
                    drawBlock(g, CORNER + i * 20, CORNER + (19 - j) * 20, Color.BLACK);
                } else {
                    drawBlock(g, CORNER + i * 20, CORNER + (19 - j) * 20, getBlockSprite(cell.getBlockType()));
                }
            }
        }
    }

    private BufferedImage getBlockSprite(BlockType blockType) {
        switch (blockType) {
            case I:
                return sprites.getSprite(0);
            case J:
                return sprites.getSprite(1);
            case L:
                return sprites.getSprite(2);
            case O:
                return sprites.getSprite(3);
            case S:
                return sprites.getSprite(4);
            default:
                return sprites.getSprite(5);
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

    private void drawBlock(Graphics g, int x, int y, BufferedImage sprite) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(sprite, x, y, null);
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
