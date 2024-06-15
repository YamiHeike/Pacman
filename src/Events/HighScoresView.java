package Events;
import Windows.HighScoresList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HighScoresView implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        HighScoresList.openHighscoresList();
    }

}
