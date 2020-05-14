package tetris.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOver extends JDialog implements ActionListener {
    boolean terug = false;

    public GameOver(Frame frame){
        super(frame, true);

        setSize(200, 200);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setTitle("Tetris: paused");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel jlText = new JLabel("Game over!");
        add(jlText);

        //behaalde score
        //is opgeslagen

        JButton jbQuit = new JButton("Terug naar hoofdmenu");
        add(jbQuit);
        jbQuit.addActionListener(this);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        terug = true;
        dispose();
    }

    public boolean getQuit(){
        return terug;
    }
}
