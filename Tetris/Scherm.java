package Tetris;

import Les14.TekenPanel;
import javafx.scene.shape.Line;

import javax.swing.*;
import java.awt.*;


public class Scherm extends JFrame {
    public static void main(String[] args) {
        new Scherm();
    }

    private int size = 25;
    private int move = 25;
    private int xMax = size * 12;
    private int yMax = size * 24;
    private int score = 0;

    public Scherm (){
        //Startscherm dialoog = new Startscherm(this);

        setTitle("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(xMax + 150, yMax);

        Gamescherm scherm = new Gamescherm();
        add(scherm);

        setVisible(true);
    }
}
