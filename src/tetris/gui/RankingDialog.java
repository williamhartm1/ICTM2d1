package tetris.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RankingDialog extends JDialog implements ActionListener {
    JButton jbTerug;

    public RankingDialog(Frame frame){
        super(frame, true);
        setSize(200, 200);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setTitle("Tetris: ranking");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(new JLabel("RANKING:"));
        jbTerug = new JButton("Terug naar hoofdmenu");
        add(jbTerug);
        jbTerug.addActionListener(this);


        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }
}
