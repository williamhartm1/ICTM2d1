import tetris.game.Tetris;

import java.io.IOException;

public class TetrisMain {
    public static void main(String[] args) throws IOException {
        Tetris tetris = new Tetris();

        Thread tetrisThread = new Thread(tetris, "tetris Thread");

        tetrisThread.start();
    }
}
