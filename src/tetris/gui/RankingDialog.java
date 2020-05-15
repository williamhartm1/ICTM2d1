package tetris.gui;

import tetris.connections.DatabaseConnectie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class RankingDialog extends JDialog implements ActionListener {
    JButton jbTerug;
    DatabaseConnectie connectie;

    public RankingDialog(Frame frame){
        super(frame, true);
        connectie = new DatabaseConnectie();

        setSize(300, 300);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setTitle("Tetris: ranking");


        ResultSet r = connectie.getGegevens(0);
        String[] highscores = new String[5];
        highscores = connectie.getHighscores();
        for(int i = 0;i < 5; i++){
            add(new JLabel(highscores[i]));
        }

        jbTerug = new JButton("Terug naar hoofdmenu");
        jbTerug.addActionListener(this);
        add(jbTerug);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }
}
