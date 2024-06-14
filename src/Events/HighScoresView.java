package Events;
import Windows.HighScoresList;
import Utils.ColorScheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HighScoresView implements ActionListener {

    /*
    *Essentially: the GOTO scorekeeper logic
    *
    *
    *
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        HighScoresList.openHighscoresList();
        System.out.println("Instance opened");
    }

}
