package tetris.gui;

import tetris.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Startscherm extends JFrame implements ActionListener {
    Game game;
    JButton jbStart, jbRank;
    JTextField jtNaam;
    JRadioButton jrEasy, jrMedium, jrHard;
    ButtonGroup group;
    boolean isEasy, isMedium, isHard;

    public Startscherm(Game game){
        this.game = game;
        setSize(320, 200);
        setLayout(new FlowLayout());
        setTitle("Tetris hoofdmenu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new JLabel("Naam:"));

        jtNaam = new JTextField(20);
        add(jtNaam);

        jrEasy = new JRadioButton("Easy", true);
        add(jrEasy);
        jrMedium = new JRadioButton("Medium");
        add(jrMedium);
        jrHard = new JRadioButton("Hard");
        add(jrHard);

        group = new ButtonGroup();
        group.add(jrEasy);
        group.add(jrMedium);
        group.add(jrHard);

        jbStart = new JButton("Start spel");
        add(jbStart);
        jbStart.addActionListener(this);

        jbRank = new JButton("Ranking");
        add(jbRank);
        jbRank.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbStart){
            game.startGame();
            dispose();
        } else if (e.getSource() == jbRank){
            RankingDialog ranking = new RankingDialog(this);
        }
    }

    public String getNaam(){
        return jtNaam.getText();
    }
}
