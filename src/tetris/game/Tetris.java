package tetris.game;

/*
Algemene spelverloop van tetris.
Gui en game worden aangestuurd, connectie met Arduino en database worden gebruikt.
 */

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import tetris.connections.ConnectieArduino;

import tetris.connections.DatabaseConnectie;
import tetris.gui.GameOver;
import tetris.gui.Gui;
import tetris.gui.Pauzescherm;
import tetris.gui.Startscherm;
import tetris.input.KeyboardInput;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Tetris extends Canvas implements Runnable {
    private Game game;
    private Startscherm startscherm;
    private GameOver gameOverScherm;
    private Gui gui;
    private long lastIteration = System.currentTimeMillis();

    private final KeyboardInput keyboard = new KeyboardInput();

    private final ConnectieArduino connectieArduino = new ConnectieArduino();

    private SpriteSheetLoader sprites;

    JFrame container;


    public Tetris() throws IOException {
        game = new Game();
        startscherm = new Startscherm(game);
        gui = new Gui(game);

        addKeyListener(keyboard);
        sprites = new SpriteSheetLoader(20, 20,  6);
    }

    // gameLoop blijft status game checken
    public void run() {
        while (true) {
            //als het spel niet bezig is
            if (!game.isPlaying()) {
                gui.setVisible(false);
                startscherm.setVisible(true); //startscherm tonen
                game.resetScore(); //score resetten op 0
            }

            //als het spel is gepauzeerd (door LDR te bedekken)
            if (game.isPaused()) {
                Pauzescherm pauze = new Pauzescherm(container, game); //toon pauzescherm
                if (pauze.getQuit()) { //als op terug is geklikt in het scherm
                    game.setPause(); //terug naar startscherm
                } else {
                    game.setPause(false); //verder spelen
                }
            }

            //als het spel bezig is
            if (game.isPlaying()) {
                this.gui.setVisible(true);                //speelscherm zichtbaar
                gui.setNaam(startscherm.getNaam());     //geef naam door aan speelscherm
                gui.setScore(game.getScore());              //geef score door aan speelscherm

                gui.repaint();

                //als de speler game-over is
                if(game.gameOver()){
                    //geluid game over via Arduino
                    try {
                        ConnectieArduino.usedPort.getOutputStream().write(2);
                    } catch (IOException iOE) {
                        iOE.printStackTrace();
                    }
                    try {
                        ConnectieArduino.usedPort.getOutputStream().flush();
                    } catch (IOException iOE) {
                        iOE.printStackTrace();
                    }

                    gameOverScherm = new GameOver(container, game.getScore()); //scherm tonen

                    DatabaseConnectie.maakspeler(startscherm.getNaam()); //speler opslaan in database

                    int spelerID = DatabaseConnectie.getSpelerID(startscherm.getNaam()); //spelerID bij speler ophalen
                    DatabaseConnectie.insertspel(game.getScore(), true, true, spelerID); //behaalde score opslaan in database

                    if(gameOverScherm.getQuit()){ //als terug is geklikt op scherm
                        game.setPause(); // terug naar startscherm
                    }
                }

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

                        //aansturen board blokjes dmv de printlines van de Arduino
                        System.out.print(serialString);
                        if (serialString.equals("Left\r\n")) {
                            game.board.moveLeft();
                        } else if (serialString.equals("Right\r\n")) {
                            game.board.moveRight();
                        } else if (serialString.equals("Pause\r\n")) {
                            game.setPause(true);
                        } else if (serialString.equals("Rotate right\r\n")) {
                            game.board.rotateRight();
                        } else if (serialString.equals("Rotate left\r\n")) {
                            game.board.rotateLeft();
                        } else if (serialString.equals("Both pressed\r\n")){
                            game.drop();
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
        //snelheid instellen easy/medium/hard
        double delay = game.getIterationDelay();
        if (startscherm.getIsMedium()){
            delay = delay * 0.75;
        } else if (startscherm.getIsHard()){
            delay = delay * 0.5;
        }

        if (game.isDropping()) {
            game.moveDown();
            // per tick het blokje op y naar beneden doen
        } else if (System.currentTimeMillis() - lastIteration >= delay) {
            game.moveDown();
            lastIteration = System.currentTimeMillis();
        }

        if (keyboard.rotate()) {
            game.board.rotateLeft();
        } else if (keyboard.left()) {
            game.board.moveLeft();
        } else if (keyboard.right()) {
            game.board.moveRight();
        } else if (keyboard.drop()) {
            game.drop();
        }

        //bord controleren op volle rijen, en zo nodig deze weghalen
        int addedScore = game.board.fillNewBoard();
        if (addedScore != 0){ //score bijtellen als line vol is
            game.score += addedScore;
        }
    }
}
