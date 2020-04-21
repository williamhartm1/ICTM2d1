package Tetris;

import javax.swing.*;
import java.awt.*;

public class Gamescherm extends JPanel {
    private int blockSize = 25;
    //private int move = 25;
    private int xMax = blockSize * 12;
    private int yMax = blockSize * 24;
    private int mesh[][] = new int[xMax][yMax];
    private Shape currentShape;

    private Shape[] shapes = new Shape[7];
    private Color[] colors = {Color.MAGENTA, Color.RED, Color.YELLOW, Color.ORANGE, Color.lightGray, Color.PINK, Color.BLUE};

    public Gamescherm() {

        shapes[0] = new Shape(new int[][]{{1, 1, 1, 1}}, colors[0]); //I-shape
        shapes[1] = new Shape(new int[][]{{1, 1, 0}, {0, 1, 1}}, colors[1]); // Z-shape
        shapes[2] = new Shape(new int[][]{{0, 1, 1}, {1, 1, 0}}, colors[2]); //S-shape
        shapes[3] = new Shape(new int[][]{{1, 1, 1}, {1, 0, 0}}, colors[3]); //L-shape
        shapes[4] = new Shape(new int[][]{{1, 1, 1}, {0, 0, 1}}, colors[4]); //J-shape
        shapes[5] = new Shape(new int[][]{{1, 1, 0}, {1, 1, 0}}, colors[5]); //O-shape
        shapes[6] = new Shape(new int[][]{{1, 1, 1}, {0, 1, 0}}, colors[6]); //T-shape

        setNextShape();
    }

    public void setNextShape(){
        int index = (int)(Math.random()*shapes.length);
        Shape newShape = new Shape(shapes[index].getShapes(), shapes[index].getColor());
        currentShape = newShape;
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        setBackground(Color.white);

        currentShape.render(g);

        //rechter kant van het scherm
        g.drawLine(xMax, 0, xMax, yMax);
        g.drawString("Player 1: ", xMax + 10, 30);
        g.drawString("Score: ", xMax + 10, 50);
        g.drawString("Level: ", xMax + 10, 70);

        //paint mesh
        for (int i = 0; i < 24; i++){
            g.drawLine(0, i*blockSize, xMax, i*blockSize);
        }
        for (int j = 0; j < 12; j++){
            g.drawLine(j*blockSize, 0, j*blockSize, yMax-50);
        }

        /*
        //i shape
        g.setColor(Color.YELLOW);
        int squareSize = size - 1;
        g.fillRect(xMax/2 - size, 1, squareSize, squareSize);
        g.fillRect(xMax/2 - size - size, 1, squareSize, squareSize);
        g.fillRect(xMax/2, 1, squareSize, squareSize);
        g.fillRect(xMax/2 + size, 1, squareSize, squareSize);
        */

    }

    public int getBlockSize() {
        return blockSize;
    }

    public int getxMax() {
        return xMax;
    }

    public int getyMax() {
        return yMax;
    }
}
