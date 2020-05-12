package tetris.gui;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private Gui gui;

    public GamePanel(Gui gui){
        this.gui = gui;
        setPreferredSize(new Dimension(220, 400));
    }

    public void paintComponent(Graphics graphics){
        Graphics2D g = (Graphics2D ) graphics;
        super.paintComponent(g);
        gui.draw(g);
    }

}
