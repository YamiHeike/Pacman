package Entities;

import Utils.ColorScheme;
import Utils.ScoreKeeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.EventObject;

public abstract class Character extends JLabel {

    //TODO: think about static and non-static fields
    int initialSpeed;
    //ImageIcon design;
    boolean isInvincible;
    Cell[][] grid;
    int speed;
    int x;
    int y;
    int dx;
    int dy;


    //TODO: KeyEvent to move --made an abstract method

    public Character(int initialSpeed, Cell[][] grid) {
        this.initialSpeed = initialSpeed;
        this.speed = initialSpeed;
        this.grid = grid;
        isInvincible = false;
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
    }

    //Methods every character must implement


    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override

    public void move(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getXPos() {
        return x;
    }

    public int getYPos() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getInitialSpeed() {
        return initialSpeed;
    }

    public static int[] edgeMove(int newX, int newY, Cell[][] grid) {
        int endX = newX;
        int endY = newY;

        if (newX < 0 || newX >= grid.length || newY < 0 || newY >= grid[0].length) {
            if (newX < 0) {
                endX = grid.length - 1;
            } else if (newX >= grid.length) {
                endX = 0;
            }

            if (newY < 0) {
                endY = grid[0].length - 1;
            } else if (newY >= grid[0].length) {
                endY = 0;
            }
        }
        return new int[]{endX, endY};
    }

    public static boolean isValidMove(int newX, int newY, Cell[][] grid) {
        if (newX < 0 || newX >= grid.length || newY < 0 || newY >= grid[0].length) {
            return false;
        }

        if (grid[newX][newY].getBackground().equals(ColorScheme.ACCENT_BLUE)) {
            return false;
        }
        return !grid[newX][newY].getBackground().equals(ColorScheme.ACCENT_BLUE);
    }

    public synchronized void updatePosition(int newX, int newY) {
        // Check if newX and newY are within the bounds of the grid array

        JPanel currPos = grid[getXPos()][getYPos()];
        JPanel newPos = grid[newX][newY];
        SwingUtilities.invokeLater(() -> {
            synchronized (currPos) {
                currPos.remove(this);
                currPos.repaint();
                currPos.revalidate();
            }
        });
        setLocation(newX, newY); // Use the setLocation method of Character
        SwingUtilities.invokeLater(() -> {
            synchronized (newPos) {
                newPos.add(this, BorderLayout.CENTER);
                newPos.revalidate();
                newPos.repaint();
            }
        });
    }

    public Cell[][] getGrid() {
        return grid;
    }

    //This is abstract because upgrade() of Upgrade class has more options for Pacman than for any other possible character
    public abstract void getUpgraded(Upgrade upgrade);
    public abstract void interract();

}
