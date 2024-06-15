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
        timeLabel = new JLabel("Time: 0:00");
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
            String minutes = String.valueOf(currentTime / 60);
            if((currentTime / 60) < 10) minutes = "0" + String.valueOf(minutes);
            String seconds = String.valueOf(currentTime % 60);
            if(currentTime % 60 < 10) seconds = "0" + String.valueOf(seconds);
            timeLabel.setText(minutes + ":" + seconds);
        });
    }

    public void resetDisplay() {
        initializeLabels();
        updateLabels();
        resumeTracking();
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
