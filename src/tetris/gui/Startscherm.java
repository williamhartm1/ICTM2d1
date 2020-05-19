package tetris.gui;

/*
Startscherm van de game.
Optie voor kiezen moeilijkheid, standaard 'easy';
Textveld voor naam, moet worden ingevuld;
Ranking knop om de highscores in een apart scherm te tonen;
Start knop, naam moet worden ingevuld, met geluid over Arduino als deze is aangeklikt;
 */

import tetris.connections.DatabaseConnectie;
import tetris.connections.ConnectieArduino;
import tetris.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Startscherm extends JFrame implements ActionListener {
    Game game;
    JButton jbStart, jbRank;
    JTextField jtNaam;
    JLabel melding;
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

        melding = new JLabel("");
        add(melding);

        add(new JLabel("Naam:"));

        jtNaam = new JTextField(20);
        add(jtNaam);

        jrEasy = new JRadioButton("Easy", true);
        jrEasy.addActionListener(this);
        add(jrEasy);

        jrMedium = new JRadioButton("Medium");
        jrMedium.addActionListener(this);
        add(jrMedium);

        jrHard = new JRadioButton("Hard");
        jrHard.addActionListener(this);
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
            if (jtNaam.getText().equals("")){
                melding.setText("Naam moet worden ingevuld");      //melding als naam niet is ingevuld

            } else { //spel starten in game
                game.startGame();

                //communicatie naar Arduino voor geluid
                try {
                    ConnectieArduino.usedPort.getOutputStream().write(1);
                } catch (IOException iOE) {
                    iOE.printStackTrace();
                }
                try {
                    ConnectieArduino.usedPort.getOutputStream().flush();
                } catch (IOException iOE) {
                    iOE.printStackTrace();
                }

                melding.setText("");
                dispose(); //dit scherm weghalen
            }

        } else if (e.getSource() == jbRank){
            RankingDialog ranking = new RankingDialog(this);
            ranking.setVisible(true);
        } else if (e.getSource() == jrEasy) {
            isEasy = true;
        } else if (e.getSource() == jrMedium) {
            isMedium = true;
        } else if (e.getSource() == jrHard) {
            isHard = true;
        }
    }

    public String getNaam(){
        return jtNaam.getText();
    }

    public boolean getIsMedium() {
        return isMedium;
    }

    public boolean getIsHard() {
        return isHard;
    }
}
