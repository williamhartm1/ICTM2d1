package tetris.game;

/*
De toestand van het spel bepalen, oftewel of het spel op pauze staat, game over is, bezig is;
Bepalen of blokje nog naar beneden kan;
Snelheid bepalen;
Score bijhouden;
 */

public class Game {
    protected Board board;

    private int score = 0;

    private boolean isPlaying = false;
    private boolean isDropping = false;
    private boolean isPaused = false;
    private boolean isGameOver = false;

    public Game() {
    }

    //game pauze voor pauzescherm
    public void setPause(boolean isPaused){
        this.isPaused = isPaused;
    }

    //game terug naar startscherm vanaf dialoog
    public void setPause(){
        this.isPaused = false;
        this.isPlaying = false;
    }

    //start spel
    public void startGame() {
        board = new Board();            //nieuw leeg bord aanmaken
        board.setCurrentBlock(Block.getRandomBlock());  //eerste blok op bord
        this.isPlaying = true;
    }

    //wanneer is spel afgelopen
    public boolean gameOver() {
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

    //vanzelf droppen van blokje
    public void moveDown() {
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

    public void drop() {
        isDropping = true;
    }

    public void resetScore(){
        score = 0;
    }

}
