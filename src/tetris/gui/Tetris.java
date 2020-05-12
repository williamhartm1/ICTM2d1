package tetris.gui;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import tetris.connections.ConnectieArduino;
import tetris.game.BlockType;
import tetris.game.BoardCell;
import tetris.game.Game;
import tetris.game.SpriteSheetLoader;
import tetris.input.KeyboardInput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Tetris extends Canvas implements MouseListener, Runnable {
    private Game game;
    // zorgt voor memory management van het canvas
    private final BufferStrategy strategy;

    private final int CORNER = 5;

    private static final int BLOCK_WIDTH = 20;

    private long lastIteration = System.currentTimeMillis();

    private final KeyboardInput keyboard = new KeyboardInput();

    private final ConnectieArduino connectieArduino = new ConnectieArduino();

    private SpriteSheetLoader sprites;

    JFrame container;


    public Tetris(Game game) throws IOException {
        this.game = game;
        container = new JFrame("Tetris");
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
        container.setLocationRelativeTo(null);

        addKeyListener(keyboard);
        addMouseListener(this);

        createBufferStrategy(2);
        strategy = getBufferStrategy();
    }

    // gameLoop blijft status game checken
    public void run() {
        while (true) {
            if(game.isPlaying()) {
                // start luisteren naar events van arduino
                connectieArduino.usedPort.addDataListener(new SerialPortDataListener() { //make java listen for arduino input
                    String serialString;
                    @Override
                    public int getListeningEvents() {
                        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                    }

                    @Override
                    public void serialEvent(SerialPortEvent serialPortEvent) {

                        byte[] newData = new byte[connectieArduino.usedPort.bytesAvailable()];       //aantal beschikbare bytes
                        int numRead = connectieArduino.usedPort.readBytes(newData, newData.length);  //lees aantal bytes
                        String stringBuffer = new String(newData,0,numRead);

                        serialString = stringBuffer;

                        while (!stringBuffer.endsWith("\n")) {  //alles voor een newline wordt in serialString opgeslagen
                            numRead = connectieArduino.usedPort.readBytes(newData, newData.length);
                            stringBuffer = new String(newData, 0, numRead, StandardCharsets.UTF_8);
                            serialString += stringBuffer;
                        }

                        System.out.print(serialString);
                        if (serialString.equals("Left\r\n")) {
                            game.moveLeft();
                        } else if (serialString.equals("Right\r\n")){
                            game.moveRight();
                        } else if (serialString.equals("Pause\r\n")){
                            game.setPause(true);
                        } else if (serialString.equals("Rotate right\r\n")){
                            game.rotate();
                        } else if (serialString.equals("Rotate left\r\n")){
                            game.rotate();
                        }
                    }
                });

                tetrisLoop();
            }
            // game loop vertragen
            try {
                Thread.sleep(20);
            } catch (Exception e) { }
            draw();
        }
    }

    void tetrisLoop() {
        if (game.isDropping()) {
            game.moveDown();
            // per tick het blokje op y naar beneden doen
        } else if(System.currentTimeMillis() - lastIteration >= game.getIterationDelay()) {
            game.moveDown();
            lastIteration = System.currentTimeMillis();
        }

        if (keyboard.rotate()) {
            game.rotate();
        } else if (keyboard.left()) {
            game.moveLeft();
        } else if (keyboard.right()) {
            game.moveRight();
        } else if (keyboard.drop()) {
            game.drop();
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
            drawRankingButton(g);
            drawSettingsButton(g);
        }

        if (game.isPaused()){
            drawPauseMenu(g);
        }

        if(game.isPlaying()) {
            drawCells(g);
        }

        g.dispose();
        strategy.show();
    }

    private void drawPauseMenu(Graphics2D g){
        Pauzescherm pauze = new Pauzescherm(container, game);
        if (pauze.getQuit()){
            game.setPause(false, false);
            //canvas resetten
            game.removeBoardCells();
        } else {
            game.setPause(false);
        }
    }

    private void drawCells(Graphics2D g) {
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
        g.drawString("Dek de LDR af om het spel te pauzeren", 10, 500);
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
        g.fillRect(65, 450, 100, 30);
        g.setColor(Color.BLACK);
        g.drawString("Start game", 85 - 1, 465 - 1);
    }

    private void drawRankingButton(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.fillRect(65, 500, 100, 30);
        g.setColor(Color.BLACK);
        g.drawString("Ranking", 92 - 1, 515 - 1);
    }

    private void drawSettingsButton(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.fillRect(65, 550, 100, 30);
        g.setColor(Color.BLACK);
        g.drawString("Settings", 85 - 1, 620 - 1);
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
