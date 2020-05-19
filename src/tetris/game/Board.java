package tetris.game;

import java.awt.*;

public class Board {
    private static final int DROP_X = 5;
    private static final int DROP_Y = 19;

    private static int WIDTH = 10;
    private static int HEIGHT = 20;

    private BoardCell[][] board = new BoardCell[WIDTH][HEIGHT];

    private Point blockCenter = new Point(DROP_X, DROP_Y);

    private Block currentBlock;

    public Board() {
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

    public void rotateRight() {
        Block rot = currentBlock.rotateRight();
        if (fit(rot.getPoints(), 0, 0)) {
            currentBlock = rot;
        }
    }

    public boolean fit(Point[] points, int moveX, int moveY) {
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

    public boolean isAtTop() {
        Point[] points = currentBlock.getPoints();
        for (Point point : points) {
            int y = blockCenter.y + point.y - 1;
            //System.out.println(y);
            if (y >= 18 && !canCurrentPieceMoveDown()) {
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
            move(-1, 0);
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

    public void clearLine() {
        BoardCell[][] newBoard = createEmptyBoard();
        try {
            int y;
            for (y = 0; HEIGHT > y; y++) {
                boolean clearable = true;
                for (int x = 0; WIDTH > x; x++) {
                    if (board[x][y] == null) {
                        clearable = false;
                    }
                }
                if (clearable) {
                    System.out.println("ik ben clearable");
                    for(int workline = y;workline > 0;workline--){
                        int newline = y - 1;
                        if(newline != -1) {
                            for(int x = 0; x < WIDTH;x++)
                            newBoard[x][newline] = newBoard[x][newline];
                        }
                        else{
                            for(int x = 0;x < WIDTH;x++) {
                                newBoard[x][workline] = board[x][workline];
                            }
                        }
                    }
                }
                else{
                    System.out.println("ik ben niet clearable");
                    for(int m = 0;m < WIDTH;m++)
                    newBoard[m][y] = board[m][y];
                }
            }
            System.out.println("ik vervang het bord");
            board = newBoard;
        }
        catch(Exception ne){
            ne.printStackTrace();
            System.out.println("");
            System.out.println(ne.toString());
            }
        }
    }
