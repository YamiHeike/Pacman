package Components;

import javax.swing.*;
import java.io.Serializable;
import java.util.Vector;

public class HSListModel extends AbstractListModel<String> implements Serializable {
    //TODO: think about whether it's needed
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

    public void remove(int idx) {
        data.remove(idx);
        fireIntervalRemoved(this,idx, idx);
    }

    //Overflow
    public void add(String score, int idx) {
        data.add(idx,score);
        fireIntervalAdded(this, idx, idx);
    }

    public void add(String score) {
        add(score, getSize());
    }

    //TODO: research ListRenderer, ListCellRenderer

}
