package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Button extends JButton {
    public Button(String text, Color bgColor, Color fgColor, ActionListener action) {
        setText(text);
        setBackground(bgColor);
        setForeground(fgColor);
        addActionListener(action);
    }
}
