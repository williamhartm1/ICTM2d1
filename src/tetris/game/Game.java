package tetris.game;

public class Game {
    private Board board;

    private int score = 0;

    private boolean isPlaying = false;
    private boolean isDropping = false;
    private boolean isPaused = false;
    private boolean isGameOver = false;

    public Game() {
    }

    public void setPause(boolean isPaused){ //game pauze voor pauzescherm
        this.isPaused = isPaused;
    }

    public void setPause(){  //game terug naar startscherm vanaf dialoog
        this.isPaused = false;
        this.isPlaying = false;
    }

    public void startGame() {   //start spel
        board = new Board();            //nieuw leeg bord aanmaken
        board.setCurrentBlock(Block.getRandomBlock());  //eerste blok op bord
        this.isPlaying = true;
    }

    public boolean gameOver() { //wanneer is spel afgelopen
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

    public void moveDown() {    //vanzelf droppen van blokje
        if (!board.canCurrentPieceMoveDown()) { //als blok niet verder kan, nieuw blok genereren en score +10
            isDropping = false;
            board.setCurrentBlock(Block.getRandomBlock());
            score += 10;
        } else {
            board.moveDown(); //anders gewoon nog n stap naar beneden droppen
        }
    }


    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean isDropping() {
        return isDropping;
    }

    public boolean isPaused(){ return isPaused;}

    public int getScore(){
        return score;
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

    public void clearLine(){board.clearLine();}

}
