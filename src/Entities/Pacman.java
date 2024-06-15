package Entities;

import Utils.ImageLibrary;
import Utils.ImageScaler;
import Utils.ScoreKeeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Arrays;
import java.util.List;

public class Pacman extends Character implements ComponentListener {
    private int speed;
    private int initialSpeed;
    private boolean gameOver = false;
    public boolean canKill;
    public static int lives;
    private int iconSize;
    private boolean isRunning;

    private int dx = 0;
    private int dy = 0;

    public static List<ImageIcon> pacmanImages = Arrays.asList(
            new ImageIcon(ImageLibrary.PACMAN_CLOSED),
            new ImageIcon(ImageLibrary.PACMAN_OPEN)
    );

    public List<String> paths = Arrays.asList(ImageLibrary.PACMAN_CLOSED, ImageLibrary.PACMAN_OPEN);

    public Pacman(int speed, int lives, int iconSize, Cell[][] grid) {
        super(speed, grid);

        if(speed < 6) {
            throw new IllegalArgumentException("The initial speed cannot be lower than 6");
        }

        this.initialSpeed = speed;
        this.speed = speed;
        this.lives = lives;
        this.iconSize = iconSize;
        isRunning = true;

        canKill = false;
        setIcon(ImageScaler.adjustImg(pacmanImages.get(0), iconSize));
        addComponentListener(this);
    }


    @Override

    public void interract() {
        if(gameOver) return;
        if(getLives() == 0) {
            gameOver = true;
            return;
        }
        Cell parent = grid[getXPos()][getYPos()];
        if(parent.getComponents().length > 1) {
            for (Component c : parent.getComponents()) {
                if (c.equals(this)) continue;
                if (c instanceof Enemy) {
                    Enemy ghost = (Enemy) c;
                    if (canKill && !ghost.isInvincible) {
                        ScoreKeeper.stop();
                        ScoreKeeper.setCurrScore(1000);
                        ScoreKeeper.start();
                        parent.remove(ghost);
                        continue;
                    }
                    if (isInvincible){
                        continue;
                    } else {
                        addLives(-1);
                    }
                }
                if (c instanceof Food) {
                    Food food = (Food) c;
                    ScoreKeeper.stop();
                    if (!ScoreKeeper.isDouble) {
                        ScoreKeeper.setCurrScore(food.getBonusPoints());
                    } else {
                        ScoreKeeper.setCurrScore(2 * food.getBonusPoints());
                    }
                    ScoreKeeper.start();
                    parent.remove(c);
                    repaint();
                    revalidate();
                }
                if (c instanceof Upgrade) {
                    Upgrade upgrade = (Upgrade) c;
                    getUpgraded(upgrade);
                    parent.remove(upgrade);
                    repaint();
                    revalidate();
                }
            }
        }
    }

    public void startEating() {
        isRunning = true;
        Thread eatingAnimation = new Thread(() -> {
            try {
                while (isRunning) {
                    Thread.sleep(400 - ((getSpeed() - getInitialSpeed()) * 50));
                    SwingUtilities.invokeLater(() -> {
                        synchronized (this.getPacmanImages()) {
                            ImageIcon currentImg = (ImageIcon) this.getIcon();
                            int currIdx = pacmanImages.indexOf(currentImg);
                            int nextIdx = (currIdx != -1 && currIdx != pacmanImages.size() - 1) ? currIdx + 1 : 0;
                            this.setIcon(pacmanImages.get(nextIdx));
                            interract();
                        }
                    });
                }
            } catch (InterruptedException exc) {
                System.out.println("Thread interrupted: " + exc.getMessage());
            }
        });
        eatingAnimation.start();
    }

    public synchronized void stopEating() {
        isRunning = false;
    }



    @Override
    public void getUpgraded(Upgrade upgrade) {
        upgrade.upgrade(this, upgrade.getType());
    }



    public void addLives(int incr) {
        if(Integer.MAX_VALUE - (this.lives + incr) > 0) {
            this.lives += incr;
        }
    };

    //Getters
    public static int getLives() {
        return lives;
    }

    public int getSpeed() {
        return speed;
    }

    public int getInitialSpeed() {
        return initialSpeed;
    }

    public List<ImageIcon> getPacmanImages() {
        return pacmanImages;
    }

    // Setters

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    @Override
    public void componentResized(ComponentEvent e) {
        Component parent = getParent();
        int width = parent.getWidth();
        pacmanImages = ImageScaler.adjustImageArr(paths, width / 2 + width / 3);
        setIcon(pacmanImages.get(0));
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
