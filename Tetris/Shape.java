package Tetris;

import java.awt.*;

public class Shape {
    private Gamescherm gamescherm;
    private int shapes[][];
    private Color color;

    public Shape(int shapes[][], Color color){
        this.shapes = shapes;
        this.color = color;
    }


    public void render(Graphics g){
        for(int row = 0; row < shapes.length; row++)
            for(int col = 0 ; col < shapes[row].length; col++)
                if(shapes[row][col] != 0)
                    g.fillRect(gamescherm.getxMax()/2, 0, gamescherm.getBlockSize(), gamescherm.getBlockSize());
    }


    public Color getColor(){
        return color;
    }

    public int[][] getShapes() {
        return shapes;
    }
}
