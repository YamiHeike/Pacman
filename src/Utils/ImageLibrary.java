package Utils;

public final class ImageLibrary {
    //PACMAN
    public static final String PACMAN_LOGO = "src/assets/Pacman.png";
    public static final String PACMAN_CLOSED = "src/assets/pacman_closed.png";
    public static final String PACMAN_OPEN = "src/assets/pacman_open.png";

    //ENEMIES
    public static final String GHOST_BLUE = "src/assets/ghost_blue.png";
    public static final String GHOST_PURPLE = "src/assets/ghost_purple.png";
    public static final String GHOST_RED = "src/assets/ghost_red.png";
    //FOOD
    public static final String APPLE = "src/assets/apple_icon.png";
    public static final String BANANA = "src/assets/banana_icon.png";
    public static final String ORANGE = "src/assets/orange_icon.png";
    //UPGRADES
    public static final String DOUBLE = "src/assets/double_icon.png";
    public static final String HEART = "src/assets/heart_icon.png";
    public static final String INVINCIBLE = "src/assets/invincible_icon.png";
    public static final String KILLER = "src/assets/killer_icon.png";
    public static final String SPEED = "src/assets/speed.png";

    private ImageLibrary() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
