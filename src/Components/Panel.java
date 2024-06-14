package Components;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel implements SwingConstants {
    /*
     * Simple component class used for my JPanel to avoid code redundancy
     */


    private Color bgColor;
    private Color fgColor;
    public Panel(Color bgColor, Color fgColor) {
        setBackground(bgColor);
        setForeground(fgColor);
    }
}
