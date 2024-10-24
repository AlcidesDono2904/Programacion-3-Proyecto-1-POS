package entidades.logic;

import java.util.Objects;

public class Producto {
    private String codigo;
    private String descripcion;
    private String unidadMedida;
    private double precioUnitario;
    private int existencias;
    private Categoria categoria; // Tipo correcto

    public Producto(String codigo, String descripcion, String unidadMedida, double precioUnitario, int existencias, Categoria categoria) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.precioUnitario = precioUnitario;
        this.existencias = existencias;
        this.categoria = categoria; // Tipo correcto
    }

    public Producto() {
        this.codigo = "";
        this.descripcion = "";
        this.unidadMedida = "";
        this.precioUnitario = 0.0;
        this.existencias = 0;
        this.categoria = null; // o new Categoria() si prefieres una instancia vacía
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public Categoria getCategoria() {
        return categoria; // Tipo correcto
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria; // Tipo correcto
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(codigo, producto.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
