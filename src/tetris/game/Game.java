package tetris.game;

public class Game {
    private final Board board;

    private boolean playing = false;

    public Game() {
        board = new Board();
    }

    public boolean isPlaying() {
        return playing;
    }
}
