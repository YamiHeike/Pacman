package Components;

import Utils.ColorScheme;
import Utils.ImageLibrary;
import Utils.ImageScaler;

import javax.swing.*;
import java.awt.*;

public class PacmanHeader extends Header {

    public PacmanHeader() {
        super("PACMAN", ImageScaler.adjustImg(new ImageIcon(ImageLibrary.PACMAN_LOGO), 80, 60), new Font(Font.MONOSPACED, Font.BOLD, 40), ColorScheme.BG_DARK, ColorScheme.ACCENT_YELLOW);
    }
}
