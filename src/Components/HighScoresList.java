package Components;

import Events.ToMainMenu;
import Utils.ColorScheme;
import Utils.ScoreKeeper;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Vector;

public class HighScoresList extends JPanel {
    //Getting data logic will be placed in a ScoreKeepr relying public method
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
        JLabel hsHeader = new JLabel("HIGHSCORES");
        hsHeader.setOpaque(true);
        hsHeader.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        hsHeader.setBackground(ColorScheme.BG_DARK);
        hsHeader.setForeground(ColorScheme.ACCENT_YELLOW);
        JButton toMainMenu = new JButton("Back");


        //Containers
        JPanel hsConfig = new JPanel();
        JScrollPane hsPane = new JScrollPane(highscores);

        hsConfig.setLayout(new FlowLayout());
        hsConfig.add(hsHeader);
        //General Settings

        setLayout(new BorderLayout());

        //Event delegation
        toMainMenu.addActionListener(new ToMainMenu());

        //Building the frame
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
