package tetris.gui;

/*
frame met twee panels.
Spel met bord en blokken wordt links op het scherm getoond;
spelstatistieken naam, score, pauze instructie worden rechts op het scherm getoond;
 */

import tetris.game.BlockType;
import tetris.game.BoardCell;
import tetris.game.Game;
import tetris.game.SpriteSheetLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

        sprites = new SpriteSheetLoader(20, 20,  6);

        //scherm met bord en blokjes
        gamescherm = new GamePanel(this);
        add(gamescherm, BorderLayout.WEST); //links plaatsen

        //stastieken (naam, score) van het spel
        statistieken = new JPanel();
        add(statistieken, BorderLayout.CENTER); //midden naast de andere panel
        statistieken.setLayout(new BoxLayout(statistieken, BoxLayout.PAGE_AXIS));
        statistieken.setBackground(new Color(38, 115, 191));

        //naam, wordt later ingevuld vanuit game.java
        jlNaam = new JLabel("");
        jlNaam.setForeground(Color.white);
        jlNaam.setBorder(new EmptyBorder(25, 20, 0, 0));
        statistieken.add(jlNaam);


        //score, wordt telkens geupdatet en doorgegeven vanuit game
        jlScore = new JLabel("");
        jlScore.setForeground(Color.white);
        jlScore.setBorder(new EmptyBorder(0, 20, 0, 0));
        statistieken.add(jlScore);

        //instructie voor pauzeknop
        JLabel jlPauze = new JLabel("PAUZE: DEK LDR AF");
        jlPauze.setForeground(Color.white);
        jlPauze.setBorder(new EmptyBorder(50, 20, 0, 0));
        statistieken.add(jlPauze);

        //trademark voor de lol
        JLabel jlTrademark = new JLabel("© ICTM2D groep 1");
        jlTrademark.setBorder(new EmptyBorder(250, 50,0,0));
        jlTrademark.setForeground(Color.lightGray);
        jlTrademark.setFont(new Font("serif", Font.ITALIC, 12));
        statistieken.add(jlTrademark);


        setResizable(false);
    }

    //naam aanpassen naar wat op startscherm is ingevuld
    public void setNaam(String naam){   //naam vanuit startscherm instellen
        jlNaam.setText("SPELER: " + naam);
    }

    //score aanpassen naar huidige score bijgehouden in game.java
    public void setScore(int score){    //score vanuit game instellen
        jlScore.setText("SCORE: " + score);
    }

    //teken graphics
    public void draw(Graphics2D g) {
        drawEmptyBoard(g);
        drawCells(g);
    }

    //tekenen van blokken op het bord
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

    //achtergrond van het bord tekenen
    public void drawEmptyBoard(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 800, 500);
        g.setColor(Color.GRAY);
        g.drawRect(CORNER - 1, CORNER - 1, 10 * BLOCK_WIDTH + 2, 20 * BLOCK_WIDTH + 2);
    }

    //blok tekenen zonder sprites
    public void drawBlock(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        g.fillRect(x, y, BLOCK_WIDTH, BLOCK_WIDTH);
        g.drawRect(x, y, BLOCK_WIDTH, BLOCK_WIDTH);
    }

    //blok tekenen met sprites
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
