package tetris.game;

public class Game {
    private Board board;

    private boolean isPlaying = false;
    private boolean isDropping = false;
    private boolean isPaused = false;
    private boolean isGameOver = false;

    public Game() {
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
        board = new Board();
        //nieuw bord aanmaken
        //removeBoardCells();
        board.setCurrentBlock(Block.getRandomBlock());
        this.isPlaying = true;
    }

    public boolean gameOver() {
        //if(!board.canCurrentPieceMoveDown() && blok-y == 18 (top van het speelveld)
        if (board.isAtTop()){
            isGameOver = true;
        } else {
            isGameOver = false;
        }
        return isGameOver;
    }

    //  dropping delay
    public long getIterationDelay() {
        return (long) ((11 * 0.05) * 1000);
    }

    public BoardCell[][] getBoardCells() {
        return board.getBoardWithPiece();
    }

    public void removeBoardCells(){
        board = new Board();
    }

    public void moveDown() {
        if (!board.canCurrentPieceMoveDown()) {
            isDropping = false;
            board.setCurrentBlock(Block.getRandomBlock());
        } else {
            board.moveDown();
        }
    }

    public void rotateRight() {
        board.rotateRight();
    }

    public void rotateLeft(){
        board.rotateLeft();
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

    public void clearLine(){board.fillNewBoard();}


}
