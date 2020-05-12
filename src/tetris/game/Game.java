package tetris.game;

public class Game {
    private final Board board;

    private boolean isPlaying = false;
    private boolean isDropping = false;
    private boolean isPaused = false;

    public Game() {
        board = new Board();
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean isDropping() {
        return isDropping;
    }

    public boolean isPaused(){ return isPaused;}

    public void setPause(boolean isPaused){
        this.isPaused = isPaused;
    }

    public void setPause(boolean isPaused, boolean isPlaying){
        this.isPaused = isPaused;
        this.isPlaying = isPlaying;
    }

    public void startGame() {
        board.setCurrentBlock(Block.getRandomBlock());
        this.isPlaying = true;
    }

    //  dropping delay
    public long getIterationDelay() {
        return (long) ((11 * 0.05) * 1000);
    }

    public BoardCell[][] getBoardCells() {
        return board.getBoardWithPiece();
    }

    public void removeBoardCells(){
        board.removeAllPieces();
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
