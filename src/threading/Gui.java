package threading;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import threading.connections.ConnectieArduino;
import threading.input.KeyboardInput;
import threading.game.BlockType;
import threading.game.BoardCell;
import threading.game.Game;
import threading.game.SpriteSheetLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;

public class Gui extends Canvas implements Runnable, MouseListener {
    private Game game;
    private SpriteSheetLoader sprites;

    private final int CORNER = 5;

    private static final int BLOCK_WIDTH = 20;

    private long lastIteration = System.currentTimeMillis();

    private final KeyboardInput keyboard = new KeyboardInput();

    private final ConnectieArduino connectieArduino = new ConnectieArduino();

    // zorgt voor memory management van het canvas
    private final BufferStrategy strategy;

    public Gui(Game game, SpriteSheetLoader sprites) {
        this.game = game;
        this.sprites = sprites;

        JFrame container = new JFrame("Tetris");
        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(220, 600));
        panel.setLayout(null);

        setBounds(0, 0, 800, 600);
        panel.add(this);

        container.pack();
        container.setResizable(false);
        container.setVisible(true);

        addKeyListener(keyboard);
        addMouseListener(this);

        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createBufferStrategy(2);
        strategy = getBufferStrategy();
    }

    public void run() {
        while(true){
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

                        System.out.println(serialString);
                        if (serialString.equals("Left\r\n")) {
                            game.moveLeft();
                        } else if (serialString.equals("Right\r\n")){
                            game.moveRight();
                        }
                    }
                });

                tetrisLoop();
            }
            draw();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

    private Graphics2D getGameGraphics() {
        return (Graphics2D) strategy.getDrawGraphics();
    }

    private void drawEmptyBoard(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 800, 600);
        g.setColor(Color.GRAY);
        g.drawRect(CORNER - 1, CORNER - 1, 10 * BLOCK_WIDTH + 2, 20 * BLOCK_WIDTH + 2);
    }

    private void drawStartGameButton(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.fillRect(65, 450, 100, 50);
        g.setColor(Color.BLACK);
        g.drawString("Start game", 85 - 1, 480 - 1);
    }

    // zorgt voor het tekenen van het speelveld elke
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
        System.out.println("asd");
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
