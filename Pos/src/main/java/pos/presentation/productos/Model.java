package pos.presentation.productos;

import pos.Application;
import pos.logic.Cajero;
import pos.logic.Productos;
import pos.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.List;


public class Model extends AbstractModel {

    Productos filter;
    List<Productos> list;
    Productos current;
    int mode;

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LIST);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);
    }

    public Model() {
    }

    public void init(List<Productos> list){
        this.list = list;
        this.current = new Productos();
        this.filter = new Productos();
        this.mode= Application.MODE_CREATE;
    }

    public List<Productos> getList() {
        return list;
    }

    public void setList(List<Productos> list){
        this.list = list;
        firePropertyChange(LIST);
    }

    public Productos getCurrent() {
        return current;
    }

    public void setCurrent(Productos current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public Productos getFilter() {
        return filter;
    }

    public void setFilter(Productos filter) {
        this.filter = filter;
        firePropertyChange(FILTER);
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public static final String LIST="list";
    public static final String CURRENT="current";
    public static final String FILTER="filter";
}