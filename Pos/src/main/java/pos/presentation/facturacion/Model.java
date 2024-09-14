package pos.presentation.facturacion;


import pos.Application;
import pos.logic.Cliente;
import pos.logic.Linea;
import pos.logic.Cajero;
import pos.logic.Producto;
import pos.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    int mode;

    List<Linea> lineas;
    Linea current;

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
        this.mode= Application.MODE_CREATE;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LINEAS);
        firePropertyChange(CURRENT);

    }

    public void agregarLinea(Producto p) {
        Linea l = new Linea(p);
        l.setCodigo("LIN-"+lineas.size());
        lineas.add(l);
        firePropertyChange(LINEAS);

    }

    public void setCurrent(Linea c) {
        this.current = c;
        firePropertyChange(CURRENT);
        firePropertyChange(LINEAS);
    }

    public Linea getCurrent() {
        return current;
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

    public void cobrar(){

    }

    public double total(){
        double sum =0;
        for (Linea l:this.lineas){
            sum +=l.importe();
        }
        return sum;
    }

    public static final String LINEAS= "lineas";
    public static final String PRODUCTOS= "productos";
    public static final String CAJEROS= "cajeros";
    public static final String CLIENTES= "clientes";
    public static final String CURRENT= "current";

}