package pos.presentation.historico;

import entidades.logic.Factura;
import entidades.logic.Linea;
import pos.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.ArrayList;

public class ModelHistorico extends AbstractModel {
    Factura filter;
    Factura currentFactura;
    List<Factura> listFactura;
    List<Linea> listLinea;
    int mode;
    public static final int MODE_CREATE = 0;

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LIST);
        firePropertyChange(FILTER);

    }
    public ModelHistorico(){
        this.filter = new Factura();
        this.listLinea = new ArrayList<>();
        this.listFactura = new ArrayList<Factura>();
        this.mode = MODE_CREATE;
        this.currentFactura = new Factura();
    }
    public void init(List<Factura> list1){
        this.listFactura = list1;
        this.filter = new Factura();
        this.mode = MODE_CREATE;
    }

    public List<Factura> getListFactura(){
        return listFactura;
    }
    public void setListFactura(List<Factura> list1){
        this.listFactura = list1;
        firePropertyChange(LIST);
    }
    public Factura getFilter(){
        return filter;
    }
    public void setFilter(Factura filter1){
        this.filter = filter1;
        firePropertyChange(FILTER);
    }
    public Factura getCurrentFactura(){//no se a usado
        return currentFactura;
    }
    public void setCurrentFactura(Factura current1){
        this.currentFactura = current1;
        firePropertyChange(CURRENT);
    }
    public int getMode(){
        return mode;
    }
    public void setMode(int mode1){
        this.mode = mode1;
    }
    public List<Linea> getLineasList() {
        return currentFactura.getLineas() != null ? listLinea : new ArrayList<Linea>();
    }
    public void setListLinea(List<Linea> list1){this.listLinea = list1;}

    public static final String FILTER="filter";
    public static final String CURRENT="current";
    public static final String LIST="list";
}
