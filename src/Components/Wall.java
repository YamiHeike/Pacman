package Components;

import Utils.ImageLibrary;

import javax.swing.*;


public class Wall extends JLabel {
    /*
    * A special Cell Characters cannot enter
    * */


    //TODO: add WallColor here
    //Think about possible modifications
    private final String wallIcon = ImageLibrary.WALL;
    public Wall(){
        setIcon(new ImageIcon(wallIcon));

    }

}
