package tetris.gui;

/*
Startscherm van de game.
Optie voor kiezen moeilijkheid, standaard 'easy';
invoer van spelersnaam, verplicht veld;
Ranking knop om de highscores in een apart scherm te tonen;
Start knop met geluid over Arduino als deze is aangeklikt;
 */

import tetris.connections.ConnectieArduino;
import tetris.game.Game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Startscherm extends JFrame implements ActionListener {
    Game game;
    JButton jbStart, jbRank;
    JTextField jtNaam;
    JLabel melding, jlNaam;
    JRadioButton jrEasy, jrMedium, jrHard;
    ButtonGroup group;
    boolean isEasy, isMedium, isHard;

    public Startscherm(Game game) throws IOException {
        this.game = game;
        setSize(420, 350);
        setLayout(new BorderLayout());
        setTitle("Tetris hoofdmenu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //componenten initialiseren
        melding = new JLabel("");
        melding.setForeground(new Color(255, 72, 72));
        jlNaam = new JLabel("Naam:");
        jtNaam = new JTextField(15);

        jrEasy = new JRadioButton("Easy", true);
        jrEasy.addActionListener(this);
        jrMedium = new JRadioButton("Medium");
        jrMedium.addActionListener(this);
        jrHard = new JRadioButton("Hard");
        jrHard.addActionListener(this);
        group = new ButtonGroup();
        group.add(jrEasy);
        group.add(jrMedium);
        group.add(jrHard);

        jbRank = new JButton("Ranglijst");
        jbRank.addActionListener(this);
        jbRank.setBackground(Color.orange);

        jbStart = new JButton("Start spel");
        jbStart.addActionListener(this);
        jbStart.setPreferredSize(new Dimension(400, 50));
        jbStart.setBackground(Color.green);

        //bovenste panel voor naam vh spel
        JPanel logoPanel = new JPanel();
        JLabel jlTetris = new JLabel("TETRIS");
        jlTetris.setFont(new Font("Serif", Font.BOLD, 65));
        jlTetris.setForeground(new Color(255, 67, 46));
        logoPanel.add(jlTetris);
        add(logoPanel, BorderLayout.PAGE_START);


        //linker panel voor invoeren van naam
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.gridx = 0;
        c.gridy = 1;
        leftPanel.add(melding, c);
        c.gridy = 2;
        leftPanel.add(jlNaam,c);
        c.gridy = 3;
        leftPanel.add(jtNaam,c);
        add(leftPanel, BorderLayout.WEST);


        //midden van het scherm: moeilijkheid kiezen
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.gridx  = 0;
        c.gridy = 1;
        centerPanel.add(jrEasy, c);
        c.gridy = 2;
        centerPanel.add(jrMedium, c);
        c.gridy = 3;
        centerPanel.add(jrHard, c);
        add(centerPanel, BorderLayout.CENTER);

        //rechter kant van het scherm: ranking knop
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        rightPanel.add(jbRank, c);
        add(rightPanel, BorderLayout.EAST);

        //onderkant van het scherm: startknop
        add(jbStart, BorderLayout.PAGE_END);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbStart){
            if (jtNaam.getText().equals("")){ //melding als naam niet is ingevuld
                melding.setText("Naam moet worden ingevuld");
            } else { //spel starten, status in game.java omzetten naar isPlaying
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

                melding.setText(""); //melding weghalen
                dispose(); //dit scherm weghalen
            }

        } else if (e.getSource() == jbRank){ //ranglijst dialoog openen
            RankingDialog ranking = new RankingDialog(this);
            ranking.setVisible(true);
        } else if (e.getSource() == jrEasy) { //moeilijkheid op easy
            isEasy = true;
        } else if (e.getSource() == jrMedium) { //moeilijkheid op medium
            isMedium = true;
        } else if (e.getSource() == jrHard) { //moeilijkheid op hard
            isHard = true;
        }
    }

    public String getNaam(){
        return jtNaam.getText();
    }

    //alleen medium en hard omdat easy de standaard is
    public boolean getIsMedium() {
        return isMedium;
    }

    public boolean getIsHard() {
        return isHard;
    }
}
