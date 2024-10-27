package entidades.logic;

import java.io.Serializable;
import java.util.Objects;

public class Linea implements Serializable {
    private String codigo;
    private Producto producto;
    private int cantidad;
    private double descuento;
    //private double neto;
    //private double importe;

    public Linea(String codigo, Producto producto, int cantidad, double descuento) {
        this.codigo = codigo;
        this.producto = producto;
        this.cantidad = cantidad;
        this.descuento = descuento;
        //this.neto = neto;
        //this.importe = importe;
    }

    public Linea(Producto p){
        this.producto = p;
    }

    public Linea() {this("",null,0,0);}


    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double neto(){
        return producto.getPrecioUnitario()*((100.0-descuento)/100.0);
    }

    public double importe(){
        return neto()*cantidad;
    }

    @Override
    public String toString() {
        return "Linea{" +
                "producto=" + producto +
                ", cantidad=" + cantidad +
                ", descuento=" + descuento +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Linea linea = (Linea) o;
        return cantidad == linea.cantidad && Double.compare(descuento, linea.descuento) == 0 && Objects.equals(producto, linea.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo,producto, cantidad, descuento);
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}