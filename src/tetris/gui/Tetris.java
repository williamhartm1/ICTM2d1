package tetris.gui;

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
            draw();
        }
    }

    void tetrisLoop() {
        // TODO: rotate, left, right, drop
    }

    private Graphics2D getGameGraphics() {
        return (Graphics2D) strategy.getDrawGraphics();
    }

    public void draw() {
        Graphics2D g = getGameGraphics();
        drawInitialBoard(g);
        if(!game.isPlaying()) {
            drawStartGameButton(g);
        }

        g.dispose();
        strategy.show();
    }

    private void drawStartGameButton(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.fillRect(65, 450, 100, 50);
        g.setColor(Color.BLACK);
        g.drawString("Start game", 85 - 1, 480 - 1);
    }

    private void drawInitialBoard(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 800, 600);
        g.setColor(Color.GRAY);
        g.drawRect(10 - 1, 10 - 1, 10 * 20 + 2, 20 * 20 + 2);
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
