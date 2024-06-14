package Windows;

import Components.MainMenu;

import Utils.ColorScheme;

import javax.swing.*;

public class Game extends JFrame {
    private static Game instance;
    private Game() {


        add(new MainMenu(ColorScheme.ACCENT_YELLOW, ColorScheme.BG_DARK));


        // General Settings
        setTitle("Pacman");
        setBackground(ColorScheme.BG_DARK);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static Game getInstance() {
        if (instance == null) {
            synchronized (Game.class) {
                if (instance == null) {
                    instance = new Game();
                }
            }
        }
        return instance;
    }

    public static void deleteInstance() {
        instance = null;
    }

    public static void closeMenu() {
        getInstance().setVisible(true);
        getInstance().dispose();
    }

    public static void openMenu() {
        SwingUtilities.invokeLater(() -> Windows.Game.getInstance());
    }

}