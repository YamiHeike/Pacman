package Windows;

import Components.HSListModel;
import Components.Header;
import Components.Panel;
import Events.ToMainMenu;
import Utils.ColorScheme;
import Utils.ScoreKeeper;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Vector;

public class HighScoresList extends JFrame {
    /*
    * Data processing Panel class
    * Displays highscores JList with HSList model
    * TODO: model into panel
    */

    static Vector<String> data;
    private static HighScoresList instance;
    private Color bgColor = ColorScheme.BG_DARK;
    private Color fgColor = ColorScheme.ACCENT_YELLOW;

    private HighScoresList() {
        try {
            data = ScoreKeeper.getScores();
        } catch (
                IOException e) {
            data = new Vector<>();
        }

        //Components

        JList<String> highscores = new JList<>();
        HSListModel model = new HSListModel(data);
        highscores.setModel(model);

        Header hsHeader = new Header("HIGHSCORES",null, new Font(Font.MONOSPACED, Font.BOLD, 20),ColorScheme.BG_DARK, ColorScheme.ACCENT_YELLOW);

        //Containers

        Panel panel = new Panel(ColorScheme.BG_DARK, ColorScheme.ACCENT_YELLOW);
        JScrollPane hsPane = new JScrollPane(highscores);
        panel.setLayout(new FlowLayout());
        panel.add(hsHeader);

        //Building the frame
        setVisible(true);
        setLayout(new BorderLayout());
        //setBackground(ColorScheme.BG_DARK);
        //setForeground(ColorScheme.ACCENT_YELLOW);
        add(panel, BorderLayout.PAGE_START);
        add(hsPane, BorderLayout.CENTER);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static HighScoresList getInstance() {
        synchronized (HighScoresList.class) {
            if (instance == null) {
                return new HighScoresList();
            }
            return instance;
        }
    }

    public static void deleteHighscoresList() {
        getInstance().setVisible(false);
        getInstance().dispose();
        instance = null;
    }

    public static void openHighscoresList() {
        SwingUtilities.invokeLater(() -> getInstance());
    }

}
