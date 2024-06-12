package Entities;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;


public class Cell extends JPanel {
    int x;
    int y;
    JLabel interiorType;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        interiorType = new JLabel();
        add(interiorType);
        setPreferredSize(new Dimension(50, 50));
    }

    public JLabel getInteriorType() {
        return interiorType;
    }

    public <T extends JLabel> void setInteriorType(final T interiorType) {
        if (SwingUtilities.isEventDispatchThread()) {
            updateInteriorType(interiorType);
        } else {
            SwingUtilities.invokeLater(() -> updateInteriorType(interiorType));
        }
    }

    //Ensuring deletion of previous content
    private <T extends JLabel> void updateInteriorType(T interiorType) {
        this.interiorType = interiorType;
        if (getComponentCount() > 0) {
            remove(0);
        }
        add(interiorType);
        revalidate();
        repaint();
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
}