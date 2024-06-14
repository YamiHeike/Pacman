package Entities;

import Utils.ImageLibrary;
import Utils.ImageScaler;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.awt.Component;

public class Enemy extends Character {

    public static boolean isRunning;
    public static boolean isGeneratingUpgrades;
    private Thread movementThread;
    private Thread upgradeCreator;
    private Ghost ghostColor;

    public static List<Enemy> allGhostsList = new ArrayList<>();

    public Enemy(int initialSpeed, Cell[][] grid, Ghost ghostColor) {
        super(initialSpeed, grid);
        allGhostsList.add(this);
        this.ghostColor = ghostColor;
        //isRunning = true;
        //isGeneratingUpgrades = true;
        try {
            switch (ghostColor) {
                case RED -> setIcon(ImageScaler.adjustImg(new ImageIcon(ImageLibrary.GHOST_RED), 27));
                case PURPLE -> setIcon(ImageScaler.adjustImg(new ImageIcon(ImageLibrary.GHOST_PURPLE), 27));
                case BLUE -> setIcon(ImageScaler.adjustImg(new ImageIcon(ImageLibrary.GHOST_BLUE), 27));
                default -> throw new IllegalArgumentException("Incorrect value");
            }
            moveGhost();
            generateUpgrades();
        } catch(IllegalArgumentException exc) {
            exc.printStackTrace();
        }
    }

    public void moveGhost() {
        movementThread = new Thread(() -> {
            while (isRunning) {

                int currX = getXPos();
                int currY = getYPos();
                interract();
                int[] news = findDiff();
                int newX = currX + news[0];
                int newY = currY + news[1];

                int[] coords = Character.edgeMove(newX, newY, grid);
                newX = coords[0];
                newY = coords[1];

                if (Character.isValidMove(newX, newY, grid)) {
                    move(newX, newY);
                    SwingUtilities.invokeLater(() -> updatePosition(coords[0], coords[1]));
                }
                try {
                    Thread.sleep(700 - ((getSpeed() - getInitialSpeed()) * 100));
                } catch (InterruptedException exc) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        movementThread.start();
    }



    public void generateUpgrades() {
        upgradeCreator = new Thread(() -> {
            while (isGeneratingUpgrades) {
                try {
                    Thread.sleep(5000);
                    Random rand = new Random();
                    int idx = rand.nextInt(4);
                    if (idx == 1) {
                        Cell parent = grid[getXPos()][getYPos()];
                        addUpgrade(parent, Upgrade.typeLottery());
                    }
                } catch (InterruptedException exc) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        upgradeCreator.start();
    }

    public void stopGeneratingUpgrades() {
        isGeneratingUpgrades = false;
        if (upgradeCreator != null) {
            upgradeCreator.interrupt();
        }
    }

    public void stopMovement() {
        isRunning = false;
        if (movementThread != null) {
            movementThread.interrupt();
        }
    }

    public void resumeActivity() {
        if (isRunning && (movementThread == null || !movementThread.isAlive())) {
            moveGhost();
        }
        if (isGeneratingUpgrades && (upgradeCreator == null || !upgradeCreator.isAlive())) {
            generateUpgrades();
        }
    }

    private void addUpgrade(Cell target, Upgrade.UpgradeType pickedType) {
        Cell parent = grid[getXPos()][getYPos()];
        pickedType = Upgrade.typeLottery();
        parent.add(new Upgrade(pickedType));
    }

    public int[] findDiff() {
        int dx = 0;
        int dy = 0;
        Random rand = new Random();
        int picked = rand.nextInt(4);
        switch(picked) {
            case 0:
                dx = -1;
                break;
            case 1:
                dx = 1;
                break;
            case 2:
                dy = -1;
                break;
            case 3:
                dy = 1;
                break;
        }
        return new int[] {dx, dy};
    }

    public static void setIsRunning(boolean isRunning) {
        Enemy.isRunning = isRunning;
    }

    public static void setIsGeneratingUpgrades(boolean upgrades) {
        Enemy.isGeneratingUpgrades = upgrades;
    }

    @Override
    public void getUpgraded(Upgrade upgrade) {
        upgrade.upgrade(this, upgrade.getType());
    }

    public static List<Enemy> getAllGhostList() {
        return allGhostsList;
    }

    public static void setAllGhostList(List<Enemy> newList) {
        allGhostsList = newList;
    }

    public Ghost getGhostColor() {
        return ghostColor;
    }

    @Override
    public void interract() {
        Cell parent = grid[getXPos()][getYPos()];
        for(Component c: parent.getComponents()) {
            if(c instanceof Upgrade) {
                Upgrade upgrade = (Upgrade) c;
                if(upgrade.getAgeInSec() >= 10) {
                    getUpgraded(upgrade);
                    parent.remove(c);
                    repaint();
                    revalidate();
                }
            }
        }

    }

    public enum Ghost {
        RED,
        PURPLE,
        BLUE
    }
}
