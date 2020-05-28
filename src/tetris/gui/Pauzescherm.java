package tetris.gui;
/*
Pauzescherm als het spel is gepauzeerd.
Toont huidige speler en score, knoppen voor doorgaan met spel of spel afsluiten.
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import tetris.game.Game;

public class Pauzescherm extends JDialog implements ActionListener {
    private JButton jbContinue, jbQuit;
    boolean quit = false;

    public Pauzescherm(Frame frame, Game game, String naam){
        super(frame, true);
        setSize(200, 200);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setTitle("Tetris: paused");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel jlText = new JLabel("Spel is gepauzeerd");
        jlText.setFont(new Font("Sans-Serif", Font.ITALIC, 12));
        jlText.setBorder(new EmptyBorder(5, 30, 5, 30));
        add(jlText);

        JLabel jlNaam = new JLabel("Speler: " + naam);
        add(jlNaam);

        JLabel jlScore = new JLabel("Huidige score: " + game.getScore()); //toont huidige score tijdens pauzeren
        jlScore.setBorder(new EmptyBorder(0, 30, 15, 30));
        add(jlScore);

        jbContinue = new JButton("Verder gaan");
        jbContinue.setBackground(Color.GREEN);
        add(jbContinue);
        jbContinue.addActionListener(this);

        jbQuit = new JButton("Terug naar hoofdmenu");
        jbQuit.setBackground(Color.RED);
        add(jbQuit);
        jbQuit.addActionListener(this);


        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == jbQuit){
            //bevestiging vragen voor teruggaan
            int bevestiging = JOptionPane.showConfirmDialog(this, "Weet u het zeker? De score wordt niet opgeslagen als nu terug naar het hoofdmenu gaat.", "", JOptionPane.YES_NO_OPTION);
            if (bevestiging == JOptionPane.YES_OPTION){ //teruggaan is bevestigt
                quit = true; //zorgt ervoor dat startscherm zichtbaar wordt
                dispose();
            }
        } else if (actionEvent.getSource() == jbContinue){
            dispose(); //zorgt ervoor dat gameplay verder gaat
        }
    }

    public boolean getQuit(){
        return quit;
    }
}
