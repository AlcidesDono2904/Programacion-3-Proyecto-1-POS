package pos.presentation.facturacion;


import pos.Application;
import pos.logic.*;
import pos.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    int mode;

    Factura factura;
    //List<Linea> lineas; no trabaja con una lista de lineas sino con la lista de lineas de factura
    Linea current;

    //buscarView
    List<Producto> productos;
    List<Cliente> clientes;
    List<Cajero> cajeros;
    public Model() {

    }

    public void init(List<Producto> productos, List<Cliente> clientes, List<Cajero> cajeros) {
        factura = new Factura();
        factura.setLineas(new ArrayList<Linea>());
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
        factura.getLineas().add(l);
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

    public Factura getFactura(){return factura;}

    public void setMode(int mode) {
        this.mode = mode;
    }

    public List<Linea> getLineas() {
        return factura.getLineas();
    }

    public void setLineas(List<Linea> lineas) {
        this.factura.setLineas(lineas);
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

    public int articulos(){//cantida articulos
        return factura.articulos();
    }

    public double subTotal(){
        return factura.subTotal();
    }

    public double descuento(){
        return factura.descuento();
    }

    public double total(){
        return factura.importe();
    }

    public void clear(){
        //todo
    }
    public void refrescar(){
        firePropertyChange(LINEAS);
    }
    public static final String LINEAS= "lineas";
    public static final String PRODUCTOS= "productos";
    public static final String CAJEROS= "cajeros";
    public static final String CLIENTES= "clientes";
    public static final String CURRENT= "current";

}