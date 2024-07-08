package Entities;

import Components.Wall;
import Utils.ImageLibrary;
import Utils.ImageScaler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Objects;


public class Cell extends JPanel implements ComponentListener {
    /*
    * A class representing a grid cell
    * It ensures scalability of images
    *
    * */

    int x;
    int y;
    //JLabel interiorType;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        setPreferredSize(new Dimension(50, 50));
        addComponentListener(this);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Cell other = (Cell) obj;
        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public String toString() {
        return "X coordinate: " + x + ", Y coordinate: " + y;
    }

    public int getXPos() {
        return x;
    }


    public int getYPos() {
        return y;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        Component[] children = getComponents();
        int width = getWidth();
        int height = getHeight();
        int twoThreesX = width / 2;
        int twoThreesY = height / 2;

        if (children.length == 0) return;

        for (Component child : children) {
            if (child instanceof Pacman) continue;
            if (child instanceof Food) {
                Food.FoodSize size = ((Food) child).getFoodSize();
                Food food = (Food) child;
                switch (size) {
                    case SMALL -> adjustInstance(food, ImageLibrary.APPLE, twoThreesX, twoThreesY);
                    case MEDIUM -> adjustInstance(food, ImageLibrary.BANANA, twoThreesX, twoThreesY);
                    case LARGE -> adjustInstance(food, ImageLibrary.ORANGE, twoThreesX, twoThreesY);
                }
                continue;
            }
            if (child instanceof Wall) {
                Wall wall = (Wall) child;
                adjustInstance(wall, ImageLibrary.WALL, width, height);
                continue;
            }
            if (child instanceof Upgrade) {
                Upgrade.UpgradeType type = ((Upgrade) child).getType();
                Upgrade upgrade = (Upgrade) child;
                switch(type) {
                    case LIVES -> adjustInstance(upgrade, ImageLibrary.HEART, twoThreesX, twoThreesY);
                    case SPEED -> adjustInstance(upgrade, ImageLibrary.SPEED, twoThreesX, twoThreesY);
                    case PREDATOR -> adjustInstance(upgrade, ImageLibrary.KILLER, twoThreesX, twoThreesY);
                    case INVINCIBLE -> adjustInstance(upgrade, ImageLibrary.INVINCIBLE, twoThreesX, twoThreesY);
                    case SCORE_SPEEDER -> adjustInstance(upgrade, ImageLibrary.DOUBLE, twoThreesX, twoThreesY);
                }

            }
            if (child instanceof Enemy) {
                Enemy enemy = (Enemy) child;
                Enemy.Ghost ghostColor = enemy.getGhostColor();

                switch (ghostColor) {
                    case RED -> adjustInstance(enemy, ImageLibrary.GHOST_RED, width - 1, height - 1);
                    case BLUE -> adjustInstance(enemy, ImageLibrary.GHOST_BLUE, width - 1, height - 1);
                    case PURPLE -> adjustInstance(enemy, ImageLibrary.GHOST_PURPLE, width - 1, height - 1);
                }
            }
        }
    }

    private<T extends JLabel> void adjustInstance(T c, String path, int x, int y) {
        ((T) c).setIcon(ImageScaler.adjustImg(new ImageIcon(path),x, y));
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