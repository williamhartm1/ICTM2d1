package tetris.gui;

/*
Scherm als de speler game over is
knoppen voor terug naar hoofdmenu en opnieuw proberen
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        jlText.setFont(new Font("sans-serif", Font.BOLD, 20));
        jlText.setForeground(Color.RED);
        jlText.setBorder(new EmptyBorder(5, 30, 5, 30));
        add(jlText);

        JLabel jlScore = new JLabel("Score: " + this.score);
        jlScore.setBorder(new EmptyBorder(0, 30, 0, 30));
        add(jlScore);

        JLabel jlMelding = new JLabel("Score opgeslagen");
        jlMelding.setFont(new Font("sans-serif", Font.ITALIC, 14));
        jlMelding.setBorder(new EmptyBorder(0, 30, 30, 30));
        add(jlMelding);

        JButton jbQuit = new JButton("Terug naar hoofdmenu");
        jbQuit.setBorder(new EmptyBorder(10, 30, 10, 30));
        jbQuit.setBackground(Color.RED);
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
