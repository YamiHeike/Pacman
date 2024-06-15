package Components;

import Entities.Pacman;
import Utils.ColorScheme;
import Utils.ImageLibrary;
import Utils.ImageScaler;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class LivesTracker extends JPanel {
    /*
    * The main purpose of this class is to track state of Pacman's lives
    * It uses a Thread and a livesLock monitor to ensure synchronization
    * It also serves as a tool to visulise lives: number of JLabels = number of Pacman's lives
    */

    private final ImageIcon livesIcon = new ImageIcon(ImageLibrary.HEART);
    private int panelNum;
    private int initialPanelNum;
    private boolean isTracking;
    private Object livesLock = new Object();
    private List<JLabel> labels;



    public LivesTracker() {
        isTracking = true;
        labels = new LinkedList<>();
        int currLives = Pacman.getLives();
        for(int i = 0; i < currLives; i++){
            labels.add(new JLabel(ImageScaler.adjustImg(livesIcon, 20)));
            add(labels.get(i));
        }
        panelNum = labels.size();
        initialPanelNum = panelNum;
        setBackground(ColorScheme.BG_DARK);
        setForeground(ColorScheme.ACCENT_YELLOW);
        checkForChanges();
    }

    public void checkForChanges() {
        Thread checkerThread = new Thread(() -> {
            try {
                while (isTracking) {
                    SwingUtilities.invokeLater(() -> {
                        synchronized (livesLock) {
                            int diff = calculateDiff();
                            if (diff != 0) {
                                if (diff > 0) {
                                    remove(labels.remove(panelNum - 1));
                                    panelNum--;
                                } else {
                                    JLabel newLabel = new JLabel(ImageScaler.adjustImg(livesIcon, 20));
                                    labels.add(newLabel);
                                    panelNum++;
                                    add(newLabel);
                                }
                                revalidate();
                                repaint();
                                if(Pacman.getLives() == 0) Thread.currentThread().interrupt();

                            }
                        }
                    });

                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        });
        checkerThread.start();
    }


    public int calculateDiff() {
        int pacLives = Pacman.getLives();
        int diff = panelNum - pacLives;
        return diff;
    }
}
