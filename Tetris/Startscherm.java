package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Startscherm extends JDialog implements ActionListener {
    private JRadioButton jr1speler, jr2spelers, jrEasy, jrMedium, jrHard;
    private JButton jbStart;
    private JLabel jlDifficulty, jlPlayers;
    private boolean eenSpeler;
    

    public Startscherm(JFrame frame){
        super(frame, true);
        setTitle("Tetris starten");
        setSize(300, 200);
        setLayout(new FlowLayout());

        jlPlayers = new JLabel("Number of players");

        jr1speler = new JRadioButton("1 player", true);
        jr1speler.addActionListener(this);
        jr2spelers = new JRadioButton("2 players");
        jr2spelers.addActionListener(this);
        ButtonGroup g1 = new ButtonGroup();
        g1.add(jr1speler);
        g1.add(jr2spelers);

        jlDifficulty = new JLabel("Difficulty");

        jrEasy = new JRadioButton("easy", true);
        jrEasy.addActionListener(this);
        jrMedium = new JRadioButton("medium");
        jrMedium.addActionListener(this);
        jrHard = new JRadioButton("hard");
        jrMedium.addActionListener(this);
        ButtonGroup g2 = new ButtonGroup();
        g2.add(jrEasy);
        g2.add(jrMedium);
        g2.add(jrHard);

        add(jlPlayers);
        add(jr1speler);
        add(jr2spelers);
        add(jlDifficulty);
        add(jrEasy);
        add(jrMedium);
        add(jrHard);

        jbStart = new JButton("Start game");
        jbStart.addActionListener(this);
        add(jbStart);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == jr1speler){
            eenSpeler = true;
        }
        if (actionEvent.getSource() == jr2spelers){
            eenSpeler = false;
        }
        if (actionEvent.getSource() == jbStart){
            dispose();
        }
    }

    public boolean isEenSpeler(){
        return eenSpeler;
    }
}
