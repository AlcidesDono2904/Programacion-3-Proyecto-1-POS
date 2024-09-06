package pos.logic;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;


/*4- Catálogo de productos
Debe permitir la búsqueda de productos por código o descripción, la inclusión, consulta,
modificación y borrado de productos. De cada producto se requiere su código, descripción, unidad
de medida, precio unitario y existencias; además de su categoría (ej. “Dulces”, “Bebidas”, etc.)*/

import java.util.Objects;
@XmlAccessorType(XmlAccessType.FIELD)
public class Productos {
    @XmlID
    private String codigo;
    private String descripcion;
    private String unidadMedida;
    private double precioUnitario;
    private int existencias;
    private String categoria;

    public Productos(String codigo, String descripcion, String unidadMedida, double precioUnitario, int existencias, String categoria) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.precioUnitario = precioUnitario;
        this.existencias = existencias;
        this.categoria = categoria;
    }

    public Productos() {
        this.codigo = "";
        this.descripcion = "";
        this.unidadMedida = "";
        this.precioUnitario = 0.0;
        this.existencias = 0;
        this.categoria = "";
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Productos producto = (Productos) o;
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
