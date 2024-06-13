package Components;

import Events.ToMainMenu;
import Utils.ColorScheme;
import Utils.ScoreKeeper;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Vector;

public class HighScoresList extends JPanel {
    static Vector<String> data; // = new Vector<>();

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
        JPanel hsConfig = new JPanel();
        JScrollPane hsPane = new JScrollPane(highscores);
        hsConfig.setLayout(new FlowLayout());
        hsConfig.add(hsHeader);

        //Building the frame
        setLayout(new BorderLayout());
        add(hsConfig, BorderLayout.PAGE_START);
        add(hsPane, BorderLayout.CENTER);
        add(toMainMenu, BorderLayout.PAGE_END);

        //Group styling

        for(Component c: this.getComponents()) {
            c.setBackground(bgColor);
            c.setForeground(fgColor);
        }
    }



}
