package tetris.gui;

/*
Laat de top 5 highscores zien. Terugknop naar hoofdmenu.
 */

import tetris.connections.DatabaseConnectie;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RankingDialog extends JDialog implements ActionListener {
    JButton jbTerug;
    JLabel jlScore;

    public RankingDialog(Frame frame){
        super(frame, true);

        setSize(300, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setTitle("Tetris: ranking");


        JPanel topscores = new JPanel();
        topscores.setLayout(new BoxLayout(topscores, BoxLayout.Y_AXIS));

        String[] highscores;
        highscores = DatabaseConnectie.getHighscores();
        for(int i = 0;i < 5; i++){
            jlScore = new JLabel(i+1 + "    " + highscores[i]);
            jlScore.setAlignmentX(Component.CENTER_ALIGNMENT);
            topscores.add(jlScore);
        }

        add(topscores, BorderLayout.CENTER);

        jbTerug = new JButton("Terug naar hoofdmenu");
        jbTerug.addActionListener(this);
        jbTerug.setBackground(Color.orange);
        jbTerug.setPreferredSize(new Dimension(400, 50));
        add(jbTerug, BorderLayout.PAGE_END);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }
}
