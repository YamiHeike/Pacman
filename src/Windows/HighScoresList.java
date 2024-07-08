package Windows;

import Components.HSListModel;
import Components.Header;
import Components.HighScoresCellRenderer;
import Components.Panel;
import Utils.ColorScheme;
import Utils.ScoreKeeper;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Vector;

public class HighScoresList extends JFrame implements WindowListener, ListSelectionListener {
    /*
    * Data processing Panel class
    * Displays highscores JList with HSList model
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
        highscores.setBackground(ColorScheme.BG_DARK);
        highscores.setForeground(ColorScheme.ACCENT_YELLOW);
        highscores.setCellRenderer(new HighScoresCellRenderer());


        Header hsHeader = new Header("HIGHSCORES",null, new Font(Font.MONOSPACED, Font.BOLD, 20),ColorScheme.BG_DARK, ColorScheme.ACCENT_YELLOW);

        //Containers

        Panel panel = new Panel(ColorScheme.BG_DARK, ColorScheme.ACCENT_YELLOW);
        JScrollPane hsPane = new JScrollPane(highscores);
        panel.setLayout(new FlowLayout());
        panel.add(hsHeader);

        //Building the frame
        setVisible(true);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.PAGE_START);
        add(hsPane, BorderLayout.CENTER);
        pack();
        addWindowListener(this);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    public static HighScoresList getInstance() {
        synchronized (HighScoresList.class) {
            if (instance == null) {
                instance = new HighScoresList();
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

    @Override

    public void windowClosing(WindowEvent e) {
        deleteHighscoresList();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {}



    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}


}
