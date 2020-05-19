package tetris.gui;
/*
Pauzescherm als het spel is gepauzeerd.
Toont huidige score, knoppen voor doorgaan met spel of spel afsluiten.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import tetris.game.Game;

public class Pauzescherm extends JDialog implements ActionListener {
    private JButton jbContinue, jbQuit;
    boolean quit = false;

    public Pauzescherm(Frame frame, Game game){
        super(frame, true);
        setSize(200, 200);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setTitle("Tetris: paused");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel jlText = new JLabel("Spel is gepauzeerd");
        add(jlText);

        JLabel jlScore = new JLabel("Huidige score is: " + game.getScore()); //toont huidige score voor pauzeren
        add(jlScore);

        jbContinue = new JButton("Verder gaan");
        add(jbContinue);
        jbContinue.addActionListener(this);

        jbQuit = new JButton("Terug naar hoofdmenu");
        add(jbQuit);
        jbQuit.addActionListener(this);


        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == jbQuit){
            quit = true;
            dispose();
        } else if (actionEvent.getSource() == jbContinue){
            dispose();
        }
    }

    public boolean getQuit(){
        return quit;
    }
}
