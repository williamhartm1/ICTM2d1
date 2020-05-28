package tetris.gui;

/*
Scherm als de speler game over is.
Melding dat de score is opgeslagen;
knoppen voor terug naar hoofdmenu en opnieuw proberen;
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

        //tekst Game Over
        JLabel jlText = new JLabel("Game over!");
        jlText.setFont(new Font("sans-serif", Font.BOLD, 20));
        jlText.setForeground(Color.RED);
        jlText.setBorder(new EmptyBorder(5, 30, 5, 30));
        add(jlText);

        //toon score meegegeven bij constructor
        JLabel jlScore = new JLabel("Score: " + this.score);
        jlScore.setBorder(new EmptyBorder(0, 30, 0, 30));
        add(jlScore);

        //melding dat score is opgeslagen
        JLabel jlMelding = new JLabel("Score opgeslagen");
        jlMelding.setFont(new Font("sans-serif", Font.ITALIC, 14));
        jlMelding.setBorder(new EmptyBorder(0, 30, 30, 30));
        add(jlMelding);

        //knop om terug naar hoofdmenu te gaan
        JButton jbQuit = new JButton("Terug naar hoofdmenu");
        jbQuit.setBorder(new EmptyBorder(10, 30, 10, 30));
        jbQuit.setBackground(Color.RED);
        jbQuit.addActionListener(this);
        add(jbQuit);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        terug = true; //spelstatus aanpassen, startscherm wordt weer zichtbaar
        dispose(); //dit spel wegdoen
    }

    public boolean getQuit(){
        return terug;
    }
}
