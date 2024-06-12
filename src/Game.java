import Components.HSListModel;
import Components.MainMenu;
import Utils.ColorScheme;
import Utils.ScoreKeeper;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Vector;


//TODO: remember about maxScore! So that the score fits in int variable
//TODO: implement ComponentListener
public class Game extends JFrame {
    public Game() {
        // Data

        //TODO: eventually delete, redundant
        Vector<String> data;
        try {
            data = ScoreKeeper.getScores();
        } catch (IOException e) {
            data = new Vector<>();
        }

        // Components


        //HIGHSCORES

        JLabel hsLabel = new JLabel("Highscores".toUpperCase());
        hsLabel.setFont(new Font("Arial",Font.PLAIN, 20));
        //hsLabel.setOpaque(true);


        //Highscores list

        JList<String> ranking = new JList<>();
        HSListModel listModel = new HSListModel(data);
        ranking.setModel(listModel);



        // Containers

        JScrollPane highScores = new JScrollPane(ranking);
        ranking.setBackground(ColorScheme.BG_DARK);
        ranking.setForeground(ColorScheme.LIGHT_GRAY);
        JPanel menu = new JPanel();

        menu.setLayout(new FlowLayout(FlowLayout.CENTER));


        //Logic

        //Dashboard Layout
       add(new MainMenu(ColorScheme.ACCENT_YELLOW, ColorScheme.BG_DARK));

        //Frame Construction

        menu.add(hsLabel,BorderLayout.PAGE_START);

        // General Settings
        setTitle("Pacman");
        setBackground(ColorScheme.BG_DARK);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
