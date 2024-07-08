package Components;

import Utils.ColorScheme;

import javax.swing.*;
import java.awt.*;

public class HighScoresCellRenderer extends JLabel implements ListCellRenderer {
    /*
    * Cell renderer adjusting highscores to the overall color scheme of the project
    * */

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setText((String) value);
        setOpaque(true);
        if(isSelected) {
            setBackground(ColorScheme.ACCENT_YELLOW);
            setForeground(ColorScheme.BG_DARK);
        } else {
            setBackground(ColorScheme.BG_DARK);
            setForeground(ColorScheme.ACCENT_YELLOW);
        }

        return this;
    }
}
