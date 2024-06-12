package Utils;
import javax.swing.*;
import java.awt.*;

public class ImageScaler {

    //TODO: eventually add resizing
    //TODO: rotate image
    //Image Scaling methods

    public static ImageIcon adjustImg(ImageIcon img, int squareSide) {
        if (img == null || squareSide <= 0) {
            throw new IllegalArgumentException("Image or dimensions cannot be null/zero.");
        }

        Image pacmanImg = img.getImage();
        Image adjustedImg = pacmanImg.getScaledInstance(squareSide, squareSide, Image.SCALE_SMOOTH);
        return new ImageIcon(adjustedImg);
    }

    public static ImageIcon adjustImg(ImageIcon img, int x, int y) {
        if (img == null || x <= 0 || y <= 0) {
            throw new IllegalArgumentException("Image or dimensions cannot be null/zero.");
        }
        Image pacmanImg = img.getImage();
        Image adjustedImg = pacmanImg.getScaledInstance(x, y, Image.SCALE_SMOOTH);
        return new ImageIcon(adjustedImg);
    }
}
