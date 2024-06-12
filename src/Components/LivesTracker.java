package Components;

import Entities.Pacman;
import Utils.ColorScheme;
import Utils.ImageScaler;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class LivesTracker extends JPanel {
    private ImageIcon livesIcon = new ImageIcon("src/assets/heart_icon.png");
    private int panelNum;
    private int initialPanelNum;
    private boolean isTracking;
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

    //Changes in lives tracking thread

    public void checkForChanges() {
        Thread checkerThread = new Thread(() -> {
            try {
                while (isTracking) {
                    SwingUtilities.invokeLater(() -> {
                        synchronized (this) { // Synchronize on a meaningful object
                            int diff = calculateDiff();
                            //Tested, works
                            if (diff != 0) {
                                if (diff > 0) {
                                    //if (panelNum > 0 && panelNum < labels.size()) {
                                        remove(labels.remove(panelNum - 1));
                                        panelNum--;
                                    //}
                                } else {
                                    JLabel newLabel = new JLabel(ImageScaler.adjustImg(livesIcon, 20));
                                    labels.add(newLabel);
                                    panelNum++;
                                    add(newLabel);
                                }

                                revalidate();
                                repaint();
                                if(diff == initialPanelNum) Thread.currentThread().interrupt();
                            }
                        }
                    });


                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupt status
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
