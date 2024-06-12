package Entities;

import Utils.ImageScaler;
import Utils.ScoreKeeper;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Pacman extends Character {
    //TODO: lives and default lives will need to be made static
    private int x;
    private int y;
    private int speed;
    private int initialSpeed;
    private boolean gameOver = false;
    public boolean canKill;

    public static int lives;
    private int iconSize;
    private boolean isRunning;
    private Thread movementThread;
    private int dx = 0;
    private int dy = 0;

    static List<ImageIcon> pacmanImages = Arrays.asList(
            new ImageIcon("src/assets/pacman_closed.png"),
            new ImageIcon("src/assets/pacman_open.png")
    );

    public Pacman(int speed, int lives, int iconSize, Cell[][] grid) {
        super(speed, grid);
        this.initialSpeed = speed;
        this.speed = speed;
        this.lives = lives;
        this.iconSize = iconSize;
        isRunning = true;

        for (int i = 0; i < pacmanImages.size(); i++) {
            pacmanImages.set(i, ImageScaler.adjustImg(pacmanImages.get(i), iconSize));
        }
        canKill = false;
        setIcon(ImageScaler.adjustImg(pacmanImages.get(0), iconSize));
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
                    Thread.sleep(300);
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

}
