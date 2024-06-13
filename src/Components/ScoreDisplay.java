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
    private final Object lock = new Object();

    public ScoreDisplay() {
        scoreKeeper = ScoreKeeper.getInstance();

        initializeLabels();

        LivesTracker lives = new LivesTracker();
        add(scoreLabel);
        add(timeLabel);
        add(lives);

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(ColorScheme.BG_DARK);
        setForeground(ColorScheme.LIGHT_GRAY);
        setTracking(true);
        startUpdating();
    }

    private void initializeLabels() {
        scoreLabel = new JLabel("Score: " + scoreKeeper.getCurrScore());
        timeLabel = new JLabel("Time: " + scoreKeeper.getCurrTimeInSeconds());
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(ColorScheme.BG_DARK);
        scoreLabel.setForeground(ColorScheme.ACCENT_YELLOW);
        timeLabel.setOpaque(true);
        timeLabel.setBackground(ColorScheme.BG_DARK);
        timeLabel.setForeground(ColorScheme.ACCENT_YELLOW);
    }

    private void startUpdating() {
        labelUpdater = new Thread(() -> {
            try {
                while (true) {
                    synchronized (lock) {
                        while (!scoreKeeper.isTracking()) {
                            System.out.println("Wait...");
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

    public void resetDisplay() {
        initializeLabels(); // Reinitialize labels
        updateLabels(); // Update labels immediately after reset
        resumeTracking(); // Resume tracking after reset if necessary
    }

    public void resumeTracking() {
        synchronized (lock) {
            scoreKeeper.startTracking();
            lock.notify();
        }
    }

    public void pauseTracking() {
        synchronized (lock) {
            scoreKeeper.stop();
        }
    }

    public void setTracking(boolean tracking) {
        if (tracking) {
            resumeTracking();
        } else {
            pauseTracking();
        }
    }
}
