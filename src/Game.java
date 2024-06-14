import Components.HSListModel;
import Components.MainMenu;
import Utils.ColorScheme;
import Utils.ScoreKeeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;
import java.util.Vector;


//TODO: implement ComponentListener
public class Game extends JFrame {
    public Game() {


        add(new MainMenu(ColorScheme.ACCENT_YELLOW, ColorScheme.BG_DARK));


        // General Settings
        setTitle("Pacman");
        setBackground(ColorScheme.BG_DARK);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}