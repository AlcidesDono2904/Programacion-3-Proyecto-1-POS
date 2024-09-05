package pos.logic;

import java.util.Objects;

public class Linea {
    private Producto producto;
    private int cantidad;
    private double descuento;
    private double neto;
    private double importe;

    public Linea(Producto producto, int cantidad, double descuento,double neto ,double importe) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.descuento = descuento;
        this.neto = neto;
        this.importe = importe;
    }

    public Linea(){
        this(null,0,0.0,0.0,0.0);
    }


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

    public double getNeto() {
        return neto;
    }

    public void setNeto(double neto) {
        this.neto = neto;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    @Override
    public String toString() {
        return "Linea{" +
                "producto=" + producto +
                ", cantidad=" + cantidad +
                ", descuento=" + descuento +
                ", neto=" + neto +
                ", importe=" + importe +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Linea linea = (Linea) o;
        return cantidad == linea.cantidad && Double.compare(descuento, linea.descuento) == 0 && Double.compare(neto, linea.neto) == 0 && Double.compare(importe, linea.importe) == 0 && Objects.equals(producto, linea.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producto, cantidad, descuento, neto, importe);
    }


}
