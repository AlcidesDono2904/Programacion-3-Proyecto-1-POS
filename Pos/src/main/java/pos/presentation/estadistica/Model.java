package pos.presentation.estadistica;

import pos.logic.Categoria;
import pos.logic.Rango;
import pos.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    private List<Rango> rangos;
    private Rango actual;

    private List<Categoria> categorias;

    private int mode;

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        //firePropertyChange(TABLA);
        firePropertyChange(CB);
    }

    public Model() {
        rangos = new ArrayList<Rango>();
    }

    public void init(List<Categoria>categorias){
        this.categorias = categorias;
        this.mode=0;
    }

    public List<Rango> getRangos() {
        return rangos;
    }

    public void setRangos(List<Rango> rangos) {
        this.rangos = rangos;
        firePropertyChange(TABLA);
    }

    public Rango getActual() {
        return actual;
    }

    public void setActual(Rango actual) {
        this.actual = actual;

    }
    public List<Categoria> getCategorias() {
        return categorias;
    }
    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public int getMode() {return mode;}

    public void setMode(int mode) {this.mode = mode;}

    public void agregarRango(Rango rango) {
        rangos.add(rango);
        firePropertyChange(TABLA);
    }

    public void removeCategory(Categoria categoria) {
        for (Rango rango : rangos) {
            if (rango.getCategoria().equals(categoria)) {
                rangos.remove(rango);
                break;
            }
        }
        firePropertyChange(TABLA);
    }

    public void clear(){
        rangos.clear();
        firePropertyChange(TABLA);
    }

    public static final String TABLA="tabla";
    public static final String CB="cb";

    public static final int CREATE=0;
    public static final int DELETE=1;

}
