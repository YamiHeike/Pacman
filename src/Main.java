import javax.swing.*;
import java.io.*;
import Windows.Game;


public class Main {
    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(() -> Game.getInstance());
    }
}