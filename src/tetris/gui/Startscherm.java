package tetris.gui;

/*
Startscherm van de game.
Optie voor kiezen moeilijkheid, standaard 'easy';
Textveld voor naam, moet worden ingevuld;
Ranking knop om de highscores in een apart scherm te tonen;
Start knop, naam moet worden ingevuld, met geluid over Arduino als deze is aangeklikt;
 */

import tetris.connections.ConnectieArduino;
import tetris.game.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
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
        setSize(420, 450);
        setLayout(new BorderLayout());
        setTitle("Tetris hoofdmenu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //componenten initialiseren
        melding = new JLabel("");
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

        jbRank = new JButton("Ranking");
        jbRank.addActionListener(this);
        jbRank.setBackground(Color.orange);

        jbStart = new JButton("Start spel");
        jbStart.addActionListener(this);
        jbStart.setPreferredSize(new Dimension(400, 50));
        jbStart.setBackground(Color.green);


        //bovenste panel voor een logo
        JPanel logoPanel = new JPanel();
        BufferedImage logo = ImageIO.read(new File("C:\\Users\\leens\\Documents\\ICT\\Periode_4\\KBS\\Code_git\\ICTM2d1\\src\\tetris\\sprites\\Logo.png"));
        JLabel iconLabel = new JLabel(new ImageIcon(logo));
        logoPanel.add(iconLabel);
        add(logoPanel, BorderLayout.PAGE_START);


        //linker panel voor invoeren van naam
        JPanel leftPanel = new JPanel(new GridBagLayout());
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
        c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        rightPanel.add(jbRank, c);
        add(rightPanel, BorderLayout.EAST);

        //onderkant van het scherm: startknop
        add(jbStart, BorderLayout.PAGE_END);

        pack();
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
