package Events;

import Components.HighScoresList;
import Components.Maze;
import Components.ScoreDisplay;
import Entities.Enemy;
import Utils.ColorScheme;
import Utils.ScoreKeeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameCreator implements ActionListener {

     public void actionPerformed(ActionEvent e) {
         try {
             JButton src = (JButton) e.getSource();
             int size = Integer.parseInt(JOptionPane.showInputDialog("Choose a size for your gameboard.\nEnter a number from 6 to 11"));
             if(size < 6 || size >= 12) {
                 throw new NumberFormatException();
             }

             int confirm = JOptionPane.showConfirmDialog(null, "The game will start now. Press ESC to quit.\nPress Enter to Pause\nPacman moves on Arrow Keys and WSAD. Are you ready?","Instructions" ,JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

             if(confirm == JOptionPane.NO_OPTION) {
                throw new RuntimeException("The user chose the 'No' option");
             }
             Enemy.setIsRunning(true);
             Enemy.setIsGeneratingUpgrades(true);

             Container parent = src.getParent().getParent();
             parent.remove(1);

             parent.add(new Maze(size, 3, ColorScheme.BG_DARK,ColorScheme.ACCENT_BLUE));
             parent.add(new ScoreDisplay(), BorderLayout.PAGE_END);
             parent.setBackground(ColorScheme.BG_DARK);
             ScoreKeeper.getInstance().startTracking();
             parent.revalidate();
             parent.repaint();


         } catch (NumberFormatException exc) {
             JOptionPane.showMessageDialog(null,"Incorrect input format. Enter a number from 6 to 11");
         }
         catch(Exception exc) {
             JOptionPane.showMessageDialog(null,"Game creation cancelled");
         }
     }



}
