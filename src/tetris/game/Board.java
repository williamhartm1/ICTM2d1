package tetris.game;

/*
Het bord van het spel, met alle blokken op het bord.
Bewegingen van het blok: vanzelf naar beneden vallen, draaien en bewegen.
Controleerd op volle rijen, volle kolommen, wanneer blok moet stoppen met naar beneden vallen.
 */

import java.awt.*;
import java.util.ArrayList;

public class Board {
    private static final int DROP_X = 5;
    private static final int DROP_Y = 19;

    //dimensies van het bord
    private static int WIDTH = 10;
    private static int HEIGHT = 20;

    private BoardCell[][] board;

    private Point blockCenter = new Point(DROP_X, DROP_Y);

    private Block currentBlock;


    //constructor: beginnen met een bord zonder blokken
    public Board() {    //nieuw leeg bord maken
        board = createEmptyBoard();
    }

    //bord met een blok erop maken
    public BoardCell[][] getBoardWithPiece() {
        BoardCell[][] output = new BoardCell[WIDTH][HEIGHT];

        for (int y = 0; y < WIDTH; y++) {
            System.arraycopy(board[y], 0, output[y], 0, board[0].length);
        }

        // add piece
        for (Point point : currentBlock.getPoints()) {
            int x = point.x + blockCenter.x;
            int y = point.y + blockCenter.y;
            output[x][y] = BoardCell.getCell(currentBlock.getType());
        }

        return output;
    }

    //maak een bord aan zonder blokken
    private BoardCell[][] createEmptyBoard() {
        BoardCell[][] boardX = new BoardCell[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x++) {
            boardX[x] = BoardCell.getEmptyArray(HEIGHT);
        }
        return boardX;
    }

    //nieuw blok op het bord zetten
    public void setCurrentBlock(Block block) {
        if (currentBlock != null) {
            addPieceToBoard();
        }
        currentBlock = block;
        resetPieceCenter();
    }

    //nieuw blok aanmaken
    private void addPieceToBoard() {
        for (Point point : currentBlock.getPoints()) {
            int x = blockCenter.x + point.x;
            int y = blockCenter.y + point.y;
            board[x][y] = BoardCell.getCell(currentBlock.getType());
        }
    }

    //controleren of een blok nog een rij naar beneden kan
    public boolean canCurrentPieceMoveDown() {
        return fit(currentBlock.getPoints(), 0, -1);
    }

    //blok naar links draaien
    public void rotateLeft() {
        Block rot = currentBlock.rotateLeft();
        if (fit(rot.getPoints(), 0, 0)) {  //controleren of dit past op huidige bord
            currentBlock = rot;
        }
    }

    //blok naar rechts draaien als dit past
    public void rotateRight(){
        Block rot = currentBlock.rotateRight();
        if (fit(rot.getPoints(), 0, 0)) {  //controleren of dit past op huidige bord
            currentBlock = rot;
        }
    }

    //controleren of blok nog naar links/rechts/beneden kan
    public boolean fit(Point[] points, int moveX, int moveY) {
        for (Point point : points) { //coordinaten na mogelijke verplaatsing van blok ophalen
            int x = blockCenter.x + point.x + moveX;
            int y = blockCenter.y + point.y + moveY;

            if (x < 0 || x >= WIDTH || y >= HEIGHT || y < 0) { //zijkanten controleren
                return false;
            }

            if (!board[x][y].isEmpty()) { //kijken of het vak nog leeg is
                return false;
            }
        }

        return true;
    }

    //midden van het blok updaten als 't naar beneden beweegt
    private void resetPieceCenter() {
        blockCenter.x = DROP_X;
        blockCenter.y = DROP_Y;
    }

    //blok naar beneden bewegen met coordinaten
    public void moveDown() {
        move(0, -1);
    }

    //blok naar links bewegen met coordinaten
    public void moveLeft() {
        if (fit(currentBlock.getPoints(), -1, 0)) { //controleren of dit past op huidige bord
            move( -1, 0);
        }
    }

    //blok naar rechts bewegen met coordinaten
    public void moveRight() {
        if (fit(currentBlock.getPoints(), 1, 0)) { //controleren of dit past op huidige bord
            move(1, 0);
        }
    }

    // coordinaten updaten na verplaatsing van het blok
    private void move(int moveX, int moveY) {
        blockCenter = new Point(blockCenter.x + moveX, blockCenter.y + moveY);
    }

    //controleren of kolom vol is, ofwel of een blok bovenaan al niet meer naar beneden kan verplaatsen
    public boolean isAtTop(){
        Point[] points = currentBlock.getPoints(); //coordinaten huidige blok ophalen
        for(Point point : points){
            int y = blockCenter.y + point.y -1;
            if ( y >= 18 && !canCurrentPieceMoveDown()){ // blok kan niet meer naar beneden, en is nog boven rij 18
                return true; //game over
            }
        }
        return false;
    }


    // Controleren of lijn vol is
    public boolean isLineCompleted(int line){
        for(int x = 0; x < WIDTH; x++){ //elke lijn doorlopen
            if(board[x][line].isEmpty()){ //controleren of vakje leegg is
                return false;
            }
        }
        return true;
    }

    // Complete lijnen verzamelen
    public ArrayList<Integer> collectCompletedLines(){
        ArrayList<Integer> completedLines = new ArrayList<>();
        for (int y =0; y < HEIGHT; y++){ //elke rij doorlopen
            if (isLineCompleted(y)){ //rij toevoegen als deze vol is
                completedLines.add(y);
            }
        }

        return completedLines;
    }


    // Nieuw bord vullen: fillNewBoard
    public int fillNewBoard(){
        BoardCell[][] newBoard = createEmptyBoard();     //   initialiseer nieuw bord
        ArrayList<Integer> completedLines = collectCompletedLines();     //   aanroepen collectCompletedLines
        boolean compleet;
        int currentYnewBoard = 0;     //   huidige y nieuw bord initialiseren: currentYnewBoard


        if (completedLines.size() != 0) {
            for (int y = 0; y < HEIGHT; y++) {     //   loop door y van het oude bord
                compleet = false;
                for (int i : completedLines) {     //      controleren of y in collectCompletedLines
                    if (i == y) {
                        compleet = true;
                    }
                }
                if (!compleet) { //  als niet compleet: overnemen in nieuw bord
                    for (int x = 0; x < WIDTH; x++) {
                        newBoard[x][currentYnewBoard] = board[x][y];
                    }
                    currentYnewBoard++;
                }
                //  als compleet: negeren
            }
            board = newBoard;
            return completedLines.size();
        }
        return 0;
    }
}
