package Windows;

import Components.*;
import Components.Panel;
import Entities.Enemy;
import Utils.ColorScheme;
import Utils.ScoreKeeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GamePanel extends JFrame implements WindowListener {

    private static int size;
    private static GamePanel instance;

    private GamePanel(int size) {

        this.size = size;

        Panel hdrPlaceholder = new Panel(ColorScheme.BG_DARK, ColorScheme.ACCENT_YELLOW);
        PacmanHeader header = new PacmanHeader();
        hdrPlaceholder.add(header);

        Enemy.setIsRunning(true);
        Enemy.setIsGeneratingUpgrades(true);
        ScoreKeeper.getInstance().startTracking();


        setLayout(new BorderLayout());
        setTitle("Pacman");
        setBackground(ColorScheme.BG_DARK);
        setSize(1000,1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        add(hdrPlaceholder, BorderLayout.PAGE_START);
        add(new Maze(size, 3, ColorScheme.BG_DARK,ColorScheme.ACCENT_BLUE),BorderLayout.CENTER);
        add(new ScoreDisplay(), BorderLayout.PAGE_END);

    }

    public static GamePanel getInstance(int size) {

        if(instance == null) {
            synchronized (GamePanel.class) {
                GamePanel.setSize(size);
                instance = new GamePanel(size);
            }
        }
        return instance;
    }

    public static void deleteGamePanel() {
        getInstance(size).setVisible(false);
        getInstance(size).dispose();
        instance = null;
    }

    public static void setSize(int size) {
        GamePanel.size = size;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        if(Game.getInstance() == null) {
            Game.openMenu();
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
