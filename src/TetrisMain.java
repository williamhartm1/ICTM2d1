import tetris.connections.DatabaseConnectie;
import tetris.game.Tetris;

import java.io.IOException;

public class TetrisMain {
    public static void main(String[] args) throws IOException {
        Tetris tetris = new Tetris();
        DatabaseConnectie.maakspeler("Heleen");
        Thread tetrisThread = new Thread(tetris, "tetris Thread");

        int[] hoi = new int[3];
        System.out.println(hoi[1]);

        tetrisThread.start();
    }
}
