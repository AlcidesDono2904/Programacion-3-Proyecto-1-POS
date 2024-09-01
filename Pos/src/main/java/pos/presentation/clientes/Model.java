package pos.presentation.clientes;

import pos.Application;
import pos.logic.Cliente;
import pos.presentation.AbstractModel;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    Cliente filter;
    List<Cliente> list;
    Cliente current;
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

    public void init(List<Cliente> list){
        this.list = list;
        this.current = new Cliente();
        this.filter = new Cliente();
        this.mode= Application.MODE_CREATE;
    }

    public List<Cliente> getList() {
        return list;
    }

    public void setList(List<Cliente> list){
        this.list = list;
        firePropertyChange(LIST);
    }

    public Cliente getCurrent() {
        return current;
    }

    public void setCurrent(Cliente current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public Cliente getFilter() {
        return filter;
    }

    public void setFilter(Cliente filter) {
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
