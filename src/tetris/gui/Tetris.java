package tetris.gui;

import tetris.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Tetris extends Canvas {
    private Game game = new Game();
    // zorgt voor memory management van het canvas
    private final BufferStrategy strategy;

    public Tetris() {
        JFrame container = new JFrame("Tetris");
        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(220, 600));
        panel.setLayout(null);

        setBounds(0, 0, 800, 600);
        panel.add(this);
        setIgnoreRepaint(true);

        container.pack();
        container.setResizable(false);
        container.setVisible(true);

        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        requestFocus();
        createBufferStrategy(2);
        strategy = getBufferStrategy();
    }

    private Graphics2D getGameGraphics() {
        return (Graphics2D) strategy.getDrawGraphics();
    }

    public void draw() {
        Graphics2D g = getGameGraphics();
        drawInitialBoard(g);

        g.dispose();
        strategy.show();
    }

    void gameLoop() {
        while (true) {
            if(game.isPlaying()) {
                tetrisLoop();
            }
            draw();
        }
    }

    void tetrisLoop() {

    }

    private void drawInitialBoard(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 800, 600);
        g.setColor(Color.GRAY);
        g.drawRect(10 - 1, 10 - 1, 10 * 20 + 2, 20 * 20 + 2);
    }

    public static void main(String[] args) {
        new Tetris().gameLoop();
    }
}
