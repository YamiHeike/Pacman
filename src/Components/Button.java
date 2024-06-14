package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Button extends JButton {
    /*
    * Simple component class used for my JButtons to avoid code redundancy
    */
    public Button(String text, Color bgColor, Color fgColor, ActionListener action) {
        setText(text);
        setBackground(bgColor);
        setForeground(fgColor);
        addActionListener(action);
    }
}
