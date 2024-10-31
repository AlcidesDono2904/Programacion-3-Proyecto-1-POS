package entidades.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {
    String sid;
    String nombre;
    List<Factura> facturas;
    public Usuario() {
        this.facturas = new ArrayList<Factura>();
        this.sid = "";
    }
    public Usuario(String id,String nombre) {
        this.sid = id;
        this.nombre = nombre;
        this.facturas=new ArrayList<>();
    }
    public String getSid() {
        return sid;
    }
    public void setSid(String id) {
        this.sid = id;
    }
    public List<Factura> getFacturas() {
        return facturas;
    }
    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
