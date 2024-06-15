package Utils;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class ImageScaler {

    private ImageScaler() {
        throw new UnsupportedOperationException("This is an utility class and cannot be instantiated");
    }

    public static ImageIcon adjustImg(ImageIcon img, int squareSide) {
        if (img == null || squareSide <= 0) {
            throw new IllegalArgumentException("Image or dimensions cannot be null or non-positive.");
        }

        Image originalImage = img.getImage();
        if (originalImage == null) {
            throw new IllegalArgumentException("The ImageIcon does not contain a valid image.");
        }

        Image scaledImage = originalImage.getScaledInstance(squareSide, squareSide, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public static ImageIcon adjustImg(ImageIcon img, int x, int y) {
        if (img == null || x <= 0 || y <= 0) {
            throw new IllegalArgumentException("Image or dimensions cannot be null or non-positive.");
        }

        Image originalImage = img.getImage();
        if (originalImage == null) {
            throw new IllegalArgumentException("The ImageIcon does not contain a valid image.");
        }

        Image scaledImage = originalImage.getScaledInstance(x, y, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }


    public static List<ImageIcon> adjustImageArr(List<String> imgs, int x) {
        if(imgs.size() == 0) return new ArrayList<>();
        List<ImageIcon> scaledImgs = new ArrayList();
        for(String img: imgs) {
            int idx = imgs.indexOf(img);
            ImageIcon scaledInstance = adjustImg(new ImageIcon(img), x);
            scaledImgs.add(idx, scaledInstance);
        }
        return scaledImgs;
    }
}
