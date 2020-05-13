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

        setSize(200, 200);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setTitle("Tetris: ranking");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(new JLabel("RANKING: zie console"));
        jbTerug = new JButton("Terug naar hoofdmenu");
        add(jbTerug);
        jbTerug.addActionListener(this);

        ResultSet r = connectie.getGegevens(0);
        connectie.printData(r);


        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }
}
