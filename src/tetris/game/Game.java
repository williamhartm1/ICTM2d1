package tetris.game;

/*
De toestand van het spel bepalen, oftewel of het spel op pauze staat, game over is, bezig is;
Bepalen of blokje nog naar beneden kan;
Snelheid bepalen;
Score bijhouden;
 */

public class Game {
    protected Board board;

    public int score = 0; //score bijhouden
    public int difficulty = 1; //moeilijkheid; easy=1; medium=2; hard=3;

    //status van het spel
    //gecontroleerd door tetris.java om juiste schermen te tonen
    private boolean isPlaying = false;
    private boolean isDropping = false;
    private boolean isPaused = false;
    private boolean isGameOver = false;

    public Game() {
    }

    //game pauzeren zodat pauzescherm zichtbaar wordt in tetris.java
    public void setPause(boolean isPaused){
        this.isPaused = isPaused;
    }

    //game status terugzetten naar startscherm vanaf het pauzescherm
    public void setPause(){
        this.isPaused = false;
        this.isPlaying = false;
    }

    //start spel
    public void startGame() {
        board = new Board();            //nieuw leeg bord aanmaken
        board.setCurrentBlock(Block.getRandomBlock());  //eerste blok op bord
        this.isPlaying = true; //status vh spel veranderen
    }

    //wanneer is spel afgelopen
    public boolean gameOver() {
        if (board.isAtTop()){ //controleren of blok bovenaan het bord staat
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

    //bord met blokken
    public BoardCell[][] getBoardCells() {
        return board.getBoardWithPiece();
    }

    //moeilijkheid instellen vanaf tetris.java
    public void setDifficulty(int difficulty){
        this.difficulty = difficulty;
    }

    //vanzelf droppen van blokje
    public void moveDown() {
        if (!board.canCurrentPieceMoveDown()) { //controleren of blok nog verder naar beneden kan
            isDropping = false; //status veranderen
            board.setCurrentBlock(Block.getRandomBlock()); //nieuw blok aanmaken
            score += (10 * difficulty); //score afhankelijk van moeilijkheid optellen
        } else {
            board.moveDown(); //anders gewoon nog 'n stap naar beneden droppen
        }
    }

    //score resetten bij start nieuw spel
    public void resetScore(){
        score = 0;
    }

    //sneller laten vallen blokje
    public void drop() {
        isDropping = true;
    }

    //getters
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



}
