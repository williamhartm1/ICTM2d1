package tetris.gui;

/*
Panel dat het bord en de blokjes tekent, en de spelstatistieken ernaast toont
 */

import tetris.game.BlockType;
import tetris.game.BoardCell;
import tetris.game.Game;
import tetris.game.SpriteSheetLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Gui extends JFrame {
    private Game game;
    private final int CORNER = 5;
    private static final int BLOCK_WIDTH = 20;

    private JLabel jlNaam, jlScore;

    private SpriteSheetLoader sprites;

    JPanel statistieken;
    GamePanel gamescherm;

    public Gui(Game game) throws IOException {
        this.game = game;
        setSize(420, 450);
        setLayout(new BorderLayout());
        setTitle("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //scherm met bord en blokjes
        gamescherm = new GamePanel(this);
        add(gamescherm, BorderLayout.WEST); //links plaatsen

        //stastieken (naam, score) van het spel
        statistieken = new JPanel();
        add(statistieken, BorderLayout.CENTER); //midden naast de andere panel
        statistieken.setLayout(new BoxLayout(statistieken, BoxLayout.PAGE_AXIS));
        statistieken.setBackground(new Color(38, 115, 191));

        sprites = new SpriteSheetLoader(20, 20,  6);

        jlNaam = new JLabel("");
        statistieken.add(jlNaam);

        jlScore = new JLabel("");
        statistieken.add(jlScore);

        statistieken.add(new JLabel("PAUZE: DEK LDR AF"));

        setResizable(false);
    }

    //naam aanpassen naar wat op startscherm is ingevuld
    public void setNaam(String naam){   //naam vanuit startscherm instellen
        jlNaam.setText("SPELER: " + naam);
    }

    //score aanpassen naar huidige score bijgehouden in board.java
    public void setScore(int score){    //score vanuit game instellen
        jlScore.setText("SCORE: " + score);
    }

    //teken graphics
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
                    drawBlock(g, CORNER + i * 20, CORNER + (19 - j) * 20, getBlockSprite(cell.getBlockType()));
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


    //sprites per blokje tekenen
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


}
