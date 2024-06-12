package Components;

import Utils.ColorScheme;
import Utils.ScoreKeeper;

import javax.swing.*;
import java.awt.*;

public class ScoreDisplay extends JPanel {
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private ScoreKeeper scoreKeeper;
    private Thread labelUpdater;
    int timeInSeconds;

    private final Object lock = new Object();

    public ScoreDisplay() {

        //Building the frame
        scoreKeeper = ScoreKeeper.getInstance();
        scoreLabel = new JLabel("Score: " + scoreKeeper.getCurrScore());
        timeLabel = new JLabel("Time: " + scoreKeeper.getCurrTimeInSeconds());
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(ColorScheme.BG_DARK);
        scoreLabel.setForeground(ColorScheme.ACCENT_YELLOW);
        timeLabel.setOpaque(true);
        timeLabel.setBackground(ColorScheme.BG_DARK);
        timeLabel.setForeground(ColorScheme.ACCENT_YELLOW);
        LivesTracker lives = new LivesTracker();

        add(scoreLabel);
        add(timeLabel);
        add(lives);

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(ColorScheme.BG_DARK);
        setForeground(ColorScheme.LIGHT_GRAY);


        startUpdating();

    }

    private void startUpdating() {
        labelUpdater = new Thread(() -> {
            try {
                while (true) {
                    synchronized (lock) {
                        while (!scoreKeeper.isTracking()) {
                            lock.wait();
                        }
                    }
                    updateLabels();

                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        });
        labelUpdater.start();

    }

    private void updateLabels() {
        SwingUtilities.invokeLater(() -> {
            int currentScore = scoreKeeper.getCurrScore();
            int currentTime = scoreKeeper.getCurrTimeInSeconds();

            scoreLabel.setText("Score: " + currentScore);
            timeLabel.setText("Time: " + currentTime);
        });
    }

    public void resumeTracking() {
        synchronized (lock) {
            scoreKeeper.start();
            lock.notify();
        }
    }

    public void pauseTracking() {
        synchronized (lock) {
            scoreKeeper.stop();
        }
    }

    // Example state change methods; these should be called based on your application logic
    public void setTracking(boolean tracking) {
        if (tracking) {
            resumeTracking();
        } else {
            pauseTracking();
        }
    }
}
