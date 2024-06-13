package Utils;
import java.awt.*;

//TODO: think how to move the cholor model here
public final class ColorScheme {
    public static final Color BG_DARK = new Color(31,25,25);
    public static final Color ACCENT_YELLOW = new Color(255, 213, 0);
    public static final Color ACCENT_RED  = new Color(206, 13, 60);
    public static final Color LIGHT_GRAY = new Color(178, 178, 178);
    public static final Color ACCENT_BLUE = new Color(31, 22, 218);

    private ColorScheme() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

}
