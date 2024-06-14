package Components;

import javax.swing.*;
import java.awt.*;

public class Header extends JLabel implements SwingConstants {

    /*
     * Simple component class used for my JLabels to avoid code redundancy
     */

    private String headerText;
    private ImageIcon icon;
    private Color bgColor;
    private Color fgColor;
    private Font font;

    public Header(String hText, ImageIcon icon, Font font, Color bgColor, Color fgColor) {
        this.headerText = hText;
        this.icon = icon;
        this.font = font;
        this.bgColor = bgColor;
        this.fgColor = fgColor;

        setOpaque(true);
        setText(hText);
        setIcon(icon);
        setFont(font);
        setBackground(bgColor);
        setForeground(fgColor);
        setAlignmentX(CENTER);
        setAlignmentY(CENTER);
    }

}
