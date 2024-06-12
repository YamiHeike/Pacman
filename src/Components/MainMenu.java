package Components;

import Events.CloseApp;
import Events.GameCreator;
import Events.HighScoresView;
import Utils.ImageScaler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;

public class MainMenu extends JPanel {

    public MainMenu(Color fgColor, Color bgColor) {

        //Image manipulation

        ImageIcon pacmanLogo = new ImageIcon("src/assets/Pacman.png");
        ImageIcon scaledPacmanLogo = ImageScaler.adjustImg(pacmanLogo,85,60);

        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null,"The Graphics for this game are not supported by your operating system");
            e.printStackTrace();
        }


        //Components

        JLabel mainHeader = new JLabel("PACMAN", scaledPacmanLogo, JLabel.CENTER);
        mainHeader.setIconTextGap(15);
        mainHeader.setHorizontalTextPosition(JLabel.LEFT);
        mainHeader.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
        mainHeader.setForeground(fgColor);
        JButton newGame = new JButton("NEW GAME");
        JButton highScores = new JButton("HIGH SCORES");
        JButton exit = new JButton("EXIT");

        //Containers

        JPanel hdr = new JPanel();
        JPanel controls = new JPanel();
        hdr.setLayout(new FlowLayout(FlowLayout.CENTER));
        hdr.setBackground(bgColor);
        hdr.setBorder(new EmptyBorder(4,6,4,6));
        controls.setOpaque(true);
        controls.setLayout(new GridLayout(0, 1));
        controls.setBackground(bgColor);


        //General settings

        setBackground(bgColor);

        setLayout(new BorderLayout());

        //Frame Construction

        hdr.add(mainHeader);
        controls.add(newGame);
        controls.add(highScores);
        controls.add(exit);
        add(hdr, BorderLayout.PAGE_START);
        add(controls, BorderLayout.CENTER);

        //Event delegation

        newGame.addActionListener(new GameCreator());
        exit.addActionListener(new CloseApp());
        highScores.addActionListener(new HighScoresView());

        //Detailed styling



        for(Component btn : controls.getComponents()) {
            if(btn instanceof JButton) {
                JButton control = (JButton) btn;
                control.setOpaque(true);
                control.setBackground(bgColor);
                control.setForeground(fgColor);
                control.setBorder(BorderFactory.createLineBorder(new Color(75, 0, 0),1));
                control.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }



    }
}
