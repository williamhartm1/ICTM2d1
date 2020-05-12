import tetris.game.Game;
import tetris.gui.Tetris;

import java.io.IOException;

public class TetrisMain {
    public static void main(String[] args) throws IOException {
        //game houd het spelverloop bij
        Game spelverloop = new Game();

        //tetris print de gui
        Tetris gui = new Tetris(spelverloop);
        Thread guiThread = new Thread(gui, "gui thread");

        guiThread.start();
    }
}
