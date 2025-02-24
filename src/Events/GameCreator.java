package Events;

import Windows.Game;
import Windows.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameCreator implements ActionListener {

     public void actionPerformed(ActionEvent e) {
         try {
             JButton src = (JButton) e.getSource();
             int size = Integer.parseInt(JOptionPane.showInputDialog(null,"Choose a size for your gameboard.\nEnter a number from 6 to 11", "Board Size Prompt",JOptionPane.PLAIN_MESSAGE));
             if(size < 6 || size >= 12) {
                 throw new NumberFormatException();
             }

             int confirm = JOptionPane.showConfirmDialog(null, "The game will start now. Press ESC to quit.\nPress Enter to Pause\nPacman moves on Arrow Keys and WSAD. Are you ready?","Instructions" ,JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

             if(confirm == JOptionPane.NO_OPTION) {
                 throw new RuntimeException("The user chose the 'No' option");
             }
             SwingUtilities.invokeLater(() -> GamePanel.getInstance(size));
             Component mainMenu = src.getParent().getParent();
             Game.closeMenu();

         } catch (NumberFormatException exc) {
             JOptionPane.showMessageDialog(null,"Incorrect input format. Enter a number from 6 to 11");
         } catch(RuntimeException ex) {
             JOptionPane.showMessageDialog(null,"You have chosen the 'No' option.");
         }
         catch(Exception exc) {
             JOptionPane.showMessageDialog(null,"Game creation cancelled");
         }
     }



}
