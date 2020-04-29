package Game;


public class Game {
    private final Board board;

    private boolean isPlaying = false;
    private boolean isDropping = false;
    private boolean leftIsPressed = false;
    private boolean rightIsPressed = false;


    public Game() {
        board = new Board();
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean isDropping() {
        return isDropping;
    }

    public void setLeftPressed(boolean leftPressed) {
        leftIsPressed = leftPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        rightIsPressed = rightPressed;
    }

    public boolean leftIsPressed(){
        return leftIsPressed;
    }

    public boolean rightIsPressed(){
        return rightIsPressed;
    }

    public void moveLeft(){
        board.moveLeft();
        this.leftIsPressed = false;
    }

    public void moveRight(){
        board.moveRight();
        this.rightIsPressed = false;
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

    public void moveDown() {
        if (!board.canCurrentPieceMoveDown()) {
            isDropping = false;
            board.setCurrentBlock(Block.getRandomBlock());
        } else {
            board.moveDown();
        }
    }
}

