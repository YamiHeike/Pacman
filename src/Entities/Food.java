package Entities;
import Utils.ImageLibrary;


import javax.swing.*;


public class Food extends JLabel  {
    private int bonusPoints;
    private String foodIcon;
    public FoodSize size;

    public Food(FoodSize size){

        try {
            this.size = size;
            switch (size) {
                case SMALL:
                    this.foodIcon = ImageLibrary.APPLE;
                    bonusPoints = 3;
                    break;
                case MEDIUM:
                    this.foodIcon = ImageLibrary.BANANA;
                    bonusPoints = 5;
                    break;
                case LARGE:
                    this.foodIcon = ImageLibrary.ORANGE;
                    bonusPoints = 10;
                    break;
            }
            setIcon(new ImageIcon(foodIcon));
        } catch(RuntimeException exc) {
            JOptionPane.showMessageDialog(null,"Incorrect food type");
            System.out.println("Food type must be one of FoodType enum values");
        }
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
    }

    //Score value accessor

    public int getBonusPoints() {
        return bonusPoints;
    }
    public FoodSize getFoodSize() { return size; }
    //Inner enum

    public enum FoodSize {
        SMALL,
        MEDIUM,
        LARGE,
    }
}
