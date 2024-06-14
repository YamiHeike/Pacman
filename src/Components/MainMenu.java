package Components;

import Events.CloseApp;
import Events.GameCreator;
import Events.HighScoresView;
import Utils.ColorScheme;
import Utils.ImageLibrary;
import Utils.ImageScaler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class MainMenu extends JPanel implements ComponentListener {
    /*
    * First JPanel the user sees while opening the game
    * Allows navigation through the App
    */

    private PacmanHeader mainHeader;
    private Panel hdr;
    public MainMenu(Color fgColor, Color bgColor) {

        //Image manipulation

        ImageIcon pacmanLogo = new ImageIcon(ImageLibrary.PACMAN_LOGO);
        ImageIcon scaledPacmanLogo = ImageScaler.adjustImg(pacmanLogo,85,60);

        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null,"The Graphics for this game are not supported by your operating system");
            e.printStackTrace();
        }


        //Components

        mainHeader = new PacmanHeader();
        mainHeader.setHorizontalTextPosition(JLabel.LEFT);

        Button newGame = new Button("NEW GAME", ColorScheme.BG_DARK,ColorScheme.ACCENT_YELLOW,new GameCreator());
        Button highScores = new Button("HIGH SCORES", ColorScheme.BG_DARK, ColorScheme.ACCENT_YELLOW, new HighScoresView());
        Button exit = new Button("EXIT", ColorScheme.BG_DARK, ColorScheme.ACCENT_YELLOW, new CloseApp());

        //Containers

        hdr = new Panel(ColorScheme.BG_DARK, ColorScheme.ACCENT_YELLOW);
        JPanel controls = new JPanel();
        hdr.setLayout(new FlowLayout(FlowLayout.CENTER));
        hdr.setBorder(new EmptyBorder(4,6,4,6));
        controls.setOpaque(true);
        controls.setLayout(new GridLayout(0, 1));
        controls.setBackground(bgColor);

        //General settings

        setBackground(bgColor);

        setLayout(new BorderLayout());

        //Frame Construction

        hdr.add(mainHeader, BorderLayout.PAGE_START);
        controls.add(newGame);
        controls.add(highScores);
        controls.add(exit);
        add(hdr, BorderLayout.PAGE_START);
        add(controls, BorderLayout.CENTER);
        addComponentListener(this);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        int parentHeight = hdr.getHeight();
        int height = getHeight();
        System.out.println(hdr.getHeight());
        mainHeader.changeIcon(ImageScaler.adjustImg(new ImageIcon(ImageLibrary.PACMAN_LOGO),(parentHeight * 2) / 3 + height/30,parentHeight/2 + height/30));
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
