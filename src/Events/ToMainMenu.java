package Events;

import Components.MainMenu;
import Utils.ColorScheme;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToMainMenu implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        Component src = (Component) e.getSource();
        Container panel = src.getParent().getParent();
        for(Component c : panel.getComponents()) {
            panel.remove(c);
        }
        panel.add(new MainMenu(ColorScheme.ACCENT_YELLOW, ColorScheme.BG_DARK));
        panel.revalidate();
        panel.repaint();
    }
}
