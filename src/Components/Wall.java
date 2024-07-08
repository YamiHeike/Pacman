package Components;

import Utils.ImageLibrary;

import javax.swing.*;


public class Wall extends JLabel {
    /*
    * A special Cell Characters cannot enter
    * */

    private final String wallIcon = ImageLibrary.WALL;
    public Wall(){
        setIcon(new ImageIcon(wallIcon));

    }

}
