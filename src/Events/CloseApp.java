package Events;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseApp implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(() -> {
            int response = JOptionPane.showConfirmDialog(null, "Do you want to exit the game?", "Exiting the game", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

}
