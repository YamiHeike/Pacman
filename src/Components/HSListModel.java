package Components;

import javax.swing.*;
import java.util.Vector;

public class HSListModel extends AbstractListModel<String> {
    /*
    *A model class for my highscores list
    */
    Vector<String> data;
    public HSListModel(Vector<String> data){
        this.data = data;
    }
    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public String getElementAt(int index) {
        return data.get(index);
    }

}
