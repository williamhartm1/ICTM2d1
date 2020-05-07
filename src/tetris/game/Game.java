package tetris.game;

public class Game {
    private final Board board;

    private boolean playing = false;
    private boolean isDropping = false;

    public Game() {
        board = new Board();
    }

    public boolean isPlaying() {
        return playing;
    }

    public boolean isDropping() {
        return isDropping;
    }

    public void startGame() {
        this.playing = true;
        this.isPlaying = true;
    }

    //  dropping delay
    public long getIterationDelay() {
        return (long) ((11 * 0.05) * 1000);
    }

    public BoardCell[][] getBoardCells() {
        return board.getBoardWithPiece();
    }

    public void moveDown() {
        if (!board.canCurrentPieceMoveDown()) {
            isDropping = false;
            board.setCurrentBlock(Block.getRandomBlock());
        } else {
            board.moveDown();
        }
    }

    public void rotate() {
        board.rotate();
    }

    public void drop() {
        isDropping = true;
    }

    public void moveLeft() {
        board.moveLeft();
    }

    public void moveRight() {
        board.moveRight();
    }
}
