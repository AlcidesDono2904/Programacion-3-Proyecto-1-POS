package pos.presentation.productos;

import pos.Application;
import pos.logic.Categoria;
import pos.logic.Producto;
import pos.presentation.AbstractModel;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;


public class Model extends AbstractModel {

    Producto filter;
    List<Producto> list;
    Producto current;
    int mode;

    List<Categoria> categorias;


    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LIST);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);
        firePropertyChange(COMBOBOX);
    }

    public Model() {
    }

    public void init(List<Producto> list,List<Categoria> categorias){
        this.list = list;
        this.current = new Producto();
        this.filter = new Producto();
        this.mode= Application.MODE_CREATE;
        this.categorias = categorias;
    }

    public List<Producto> getList() {
        return list;
    }

    public void setList(List<Producto> list){
        this.list = list;
        firePropertyChange(LIST);
    }

    public Producto getCurrent() {
        return current;
    }

    public void setCurrent(Producto current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public Producto getFilter() {
        return filter;
    }

    public void setFilter(Producto filter) {
        this.filter = filter;
        firePropertyChange(FILTER);
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
        firePropertyChange(COMBOBOX);
    }

    public static final String LIST="list";
    public static final String CURRENT="current";
    public static final String FILTER="filter";
    public static final String COMBOBOX="combobox";
}