package tetris.game;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import tetris.connections.ConnectieArduino;

import tetris.gui.Gui;
import tetris.gui.Pauzescherm;
import tetris.gui.Startscherm;
import tetris.input.KeyboardInput;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;

public class Tetris extends Canvas implements Runnable {
    private Game game;
    private Startscherm startscherm;
    private Gui gui;
    private long lastIteration = System.currentTimeMillis();

    private final KeyboardInput keyboard = new KeyboardInput();

    private final ConnectieArduino connectieArduino = new ConnectieArduino();

    //private SpriteSheetLoader sprites;

    JFrame container;


    public Tetris() {
        game = new Game();
        gui = new Gui(game);
        startscherm = new Startscherm(game);

        //sprites = new SpriteSheetLoader(20, 20,  6);
    }

    // gameLoop blijft status game checken
    public void run() {
        while (true) {
            if (!game.isPlaying()) {
                gui.setVisible(false);
                startscherm.setVisible(true);
            }

            if (game.isPaused()) {
                Pauzescherm pauze = new Pauzescherm(container, game);
                if (pauze.getQuit()) {
                    game.setPause(false, false);
                    //canvas resetten
                    game.removeBoardCells();
                } else {
                    game.setPause(false);
                }
            }

            if (game.isPlaying()) {
                this.gui.setVisible(true);
                gui.repaint();

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
                        String stringBuffer = new String(newData, 0, numRead);

                        serialString = stringBuffer;

                        while (!stringBuffer.endsWith("\n")) {  //alles voor een newline wordt in serialString opgeslagen
                            numRead = connectieArduino.usedPort.readBytes(newData, newData.length);
                            stringBuffer = new String(newData, 0, numRead, StandardCharsets.UTF_8);
                            serialString += stringBuffer;
                        }

                        System.out.print(serialString);
                        if (serialString.equals("Left\r\n")) {
                            game.moveLeft();
                        } else if (serialString.equals("Right\r\n")) {
                            game.moveRight();
                        } else if (serialString.equals("Pause\r\n")) {
                            game.setPause(true);
                        } else if (serialString.equals("Rotate right\r\n")) {
                            game.rotateRight();
                        } else if (serialString.equals("Rotate left\r\n")) {
                            game.rotateLeft();
                        }
                    }
                });
                tetrisLoop();
            }
            // game loop vertragen
            try {
                Thread.sleep(20);
            } catch (Exception e) {
            }
        }
    }

    void tetrisLoop() {
        if (game.isDropping()) {
            game.moveDown();
            // per tick het blokje op y naar beneden doen
        } else if (System.currentTimeMillis() - lastIteration >= game.getIterationDelay()) {
            game.moveDown();
            lastIteration = System.currentTimeMillis();
        }

        if (keyboard.rotate()) {
            game.rotateLeft();
        } else if (keyboard.left()) {
            game.moveLeft();
        } else if (keyboard.right()) {
            game.moveRight();
        } else if (keyboard.drop()) {
            game.drop();
        }
    }
}
