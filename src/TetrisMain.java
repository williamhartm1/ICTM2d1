/*
Tetris in java bestuurd door de Arduino, gemaakt voor KBS2a project door ICTM2D groep 1.
Auteurs:
Heleen de Gaaij  - s1146233
Chen ten Have    - s1103700
Kim de Jong      - s1140089
Jort Kuhlmann    - s1142021
William Hartman  - s1150636
Pascal Meijerman - S1143521
 */

import tetris.TetrisConfig;
import tetris.game.Tetris;

import java.io.IOException;

public class TetrisMain {
    public static void main(String[] args) throws IOException {
        Tetris tetris = new Tetris();
        Thread tetrisThread = new Thread(tetris, "tetris Thread");

        tetrisThread.start();
        //TetrisConfig test = new TetrisConfig();
    }
}
