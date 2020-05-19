package tetris.gui;

import tetris.connections.DatabaseConnectie;

import tetris.connections.ConnectieArduino;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameOver extends JDialog implements ActionListener {
    boolean terug = false;
    int score;

    public GameOver(Frame frame, int score){
        super(frame, true);
        this.score = score;

        setSize(200, 200);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setTitle("Tetris: paused");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel jlText = new JLabel("Game over!");
        add(jlText);

        JLabel jlScore = new JLabel("Score: " + this.score);
        add(jlScore);

        add(new JLabel("Score opgeslagen"));

        JButton jbQuit = new JButton("Terug naar hoofdmenu");
        add(jbQuit);
        jbQuit.addActionListener(this);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        terug = true;
        dispose();
    }

    public boolean getQuit(){
        return terug;
    }
}
