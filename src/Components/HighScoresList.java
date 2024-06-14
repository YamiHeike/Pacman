package Components;

import Events.ToMainMenu;
import Utils.ColorScheme;
import Utils.ScoreKeeper;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Vector;

public class HighScoresList extends JPanel {
    /*
    * Data processing Panel class
    * Displays highscores JList with HSList model
    * TODO: model into panel
    */

    static Vector<String> data;

    public HighScoresList(Color bgColor, Color fgColor) {
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
        Button toMainMenu = new Button("Back",ColorScheme.BG_DARK, ColorScheme.ACCENT_YELLOW,new ToMainMenu());

        //Containers

        Panel panel = new Panel(ColorScheme.BG_DARK, ColorScheme.ACCENT_YELLOW);
        JScrollPane hsPane = new JScrollPane(highscores);
        panel.setLayout(new FlowLayout());
        panel.add(hsHeader);

        //Building the frame

        setLayout(new BorderLayout());
        setBackground(ColorScheme.BG_DARK);
        setForeground(ColorScheme.ACCENT_YELLOW);
        add(panel, BorderLayout.PAGE_START);
        add(hsPane, BorderLayout.CENTER);
        add(toMainMenu, BorderLayout.PAGE_END);

    }



}
