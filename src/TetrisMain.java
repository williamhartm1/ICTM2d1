import tetris.TetrisConfig;
import tetris.connections.DatabaseConnectie;
import tetris.game.Tetris;

import java.io.IOException;

public class TetrisMain {
    public static void main(String[] args) throws IOException {
        Tetris tetris = new Tetris();
        DatabaseConnectie.maakspeler("Heleen");
        Thread tetrisThread = new Thread(tetris, "tetris Thread");

        tetrisThread.start();
        TetrisConfig test = new TetrisConfig();
    }
}
