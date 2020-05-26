package tetris.game;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    private static final int DROP_X = 5;
    private static final int DROP_Y = 19;

    private static int WIDTH = 10;
    private static int HEIGHT = 20;

    private BoardCell[][] board = new BoardCell[WIDTH][HEIGHT];

    private Point blockCenter = new Point(DROP_X, DROP_Y);

    private Block currentBlock;


    public Board() {    //nieuw leeg bord maken
        board = createEmptyBoard();
    }

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

    private BoardCell[][] createEmptyBoard() {
        BoardCell[][] boardX = new BoardCell[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x++) {
            boardX[x] = BoardCell.getEmptyArray(HEIGHT);
        }
        return boardX;
    }

    public void setCurrentBlock(Block block) {
        if (currentBlock != null) {
            addPieceToBoard();
        }
        currentBlock = block;
        resetPieceCenter();
    }

    private void addPieceToBoard() {
        for (Point point : currentBlock.getPoints()) {
            int x = blockCenter.x + point.x;
            int y = blockCenter.y + point.y;
            board[x][y] = BoardCell.getCell(currentBlock.getType());
        }
    }

    public boolean canCurrentPieceMoveDown() {
        return fit(currentBlock.getPoints(), 0, -1);
    }

    public void rotateLeft() {
        Block rot = currentBlock.rotateLeft();
        if (fit(rot.getPoints(), 0, 0)) {
            currentBlock = rot;
        }
    }

    public void rotateRight(){
        Block rot = currentBlock.rotateRight();
        if (fit(rot.getPoints(), 0, 0)) {
            currentBlock = rot;
        }
    }

    public boolean fit(Point[] points, int moveX, int moveY) { //check of blok nog naar links/rechts/beneden kan
        for (Point point : points) {
            int x = blockCenter.x + point.x + moveX;
            int y = blockCenter.y + point.y + moveY;

            if (x < 0 || x >= WIDTH || y >= HEIGHT || y < 0) {
                return false;
            }

            if (!board[x][y].isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public boolean isAtTop(){   //check of blok bovenaan het bord niet meer naar beneden kan, oftewel game over is
        Point[] points = currentBlock.getPoints();
        for(Point point : points){
            int y = blockCenter.y + point.y -1;
            if ( y >= 18 && !canCurrentPieceMoveDown()){
                return true;
            }
        }
        return false;
    }

    private void resetPieceCenter() {
        blockCenter.x = DROP_X;
        blockCenter.y = DROP_Y;
    }

    public void moveDown() {
        move(0, -1);
    }

    public void moveLeft() {
        if (fit(currentBlock.getPoints(), -1, 0)) {
            move( -1, 0);
        }
    }

    public void moveRight() {
        if (fit(currentBlock.getPoints(), 1, 0)) {
            move(1, 0);
        }
    }

    // Helper to current block center X and Y
    private void move(int moveX, int moveY) {
        blockCenter = new Point(blockCenter.x + moveX, blockCenter.y + moveY);
    }



    // Controleren of lijn vol is
    public boolean isLineCompleted(int line){
        for(int x = 0; x < WIDTH; x++){
            if(board[x][line].isEmpty()){
                return false;
            }
        }
        return true;
    }

    // Complete lijnen verzamelen
    public ArrayList<Integer> collectCompletedLines(){
        ArrayList<Integer> completedLines = new ArrayList<>();
        for (int y =0; y < HEIGHT; y++){
            if (isLineCompleted(y)){
                completedLines.add(y);
            }
        }

        return completedLines;
    }

    //board[x][y]
    //i is een completedline
    //y is de huidige lijn


    // Nieuw bord vullen: fillNewBoard

    public void fillNewBoard(){
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
        }
    }
}
