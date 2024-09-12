package pos.presentation.facturacion;


import pos.logic.Linea;
import pos.logic.Producto;
import pos.logic.Service;
import pos.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    int mode;

    List<Linea> lineas;

    //buscarView
    List<Producto> productos;

    public Model() {
        lineas = new ArrayList<Linea>();
        productos = Service.instance().search(new Producto());
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LINEAS);

    }

    public void agregarLinea(Producto p) {
        Linea l = new Linea(p);
        l.setCodigo("LIN-"+lineas.size());
        lineas.add(l);
        firePropertyChange(LINEAS);
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public List<Linea> getLineas() {
        return lineas;
    }

    public void setLineas(List<Linea> lineas) {
        this.lineas = lineas;
    }

    public List<Producto> getProductos() {return productos;}

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
        firePropertyChange(PRODUCTOS);
    }

    public static final String LINEAS= "lineas";

    public static final String PRODUCTOS= "productos";
}
