package Entities;
import javax.swing.*;

public class Food extends JLabel{
    private int bonusPoints;
    private FoodSize foodType;
    private ImageIcon foodIcon;

    public Food(FoodSize size){
        try {
            this.foodType = size;
            switch (size) {
                //TODO: find food icons
                case SMALL:
                    this.foodIcon = new ImageIcon("src/assets/apple_icon.png");
                    bonusPoints = 3;
                    break;
                case MEDIUM:
                    this.foodIcon = new ImageIcon("src/assets/banana_icon.png");
                    bonusPoints = 5;
                    break;
                case LARGE:
                    this.foodIcon = new ImageIcon("src/assets/orange_icon.png");
                    bonusPoints = 10;
                    break;
            }
            setIcon(foodIcon);
        } catch(RuntimeException exc) {
            JOptionPane.showMessageDialog(null,"Incorrect food type");
            System.out.println("Food type must be");
        }
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
    }

    //Score value accessor

    public int getBonusPoints() {
        return bonusPoints;
    }

    //Inner enum

    public enum FoodSize {
        SMALL,
        MEDIUM,
        LARGE,
    }
}
