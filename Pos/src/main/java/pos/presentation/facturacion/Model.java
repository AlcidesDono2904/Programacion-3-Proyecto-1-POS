package pos.presentation.facturacion;


import pos.logic.Cliente;
import pos.logic.Linea;
import pos.logic.Cajero;
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
    List<Cliente> clientes;
    List<Cajero> cajeros;
    public Model() {

    }

    public void init(List<Producto> productos, List<Cliente> clientes, List<Cajero> cajeros) {
        lineas = new ArrayList<Linea>();
        this.productos = productos;
        this.clientes =clientes ;
        this.cajeros=cajeros ;
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
    public List<Cajero> getCajeros() {return cajeros;}
    public List<Cliente> getClientes() {return clientes;}
    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
        firePropertyChange(CLIENTES);
    }

    public void setCajeros(List<Cajero> cajeros) {
        this.cajeros = cajeros;
        firePropertyChange(CAJEROS);
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
        firePropertyChange(PRODUCTOS);
    }

    public static final String LINEAS= "lineas";

    public static final String PRODUCTOS= "productos";
    public static final String CAJEROS= "cajeros";
    public static final String CLIENTES= "clientes";
}
