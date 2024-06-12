package Events;
import Components.HighScoresList;
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
        JButton src = (JButton) e.getSource();
        Container parent = src.getParent().getParent(); //OK, this is the correct component
        parent.remove(1);
        parent.add(new HighScoresList(ColorScheme.BG_DARK, ColorScheme.ACCENT_YELLOW), BorderLayout.CENTER); //TODO: debug
        parent.revalidate();
        parent.repaint();
    }

}
