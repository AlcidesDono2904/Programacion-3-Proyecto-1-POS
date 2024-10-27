package entidades.logic;

import java.io.Serializable;
import java.util.Objects;

public class Categoria implements Serializable {
    private String id;
    private String nombre;

    public Categoria(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;

    }

    public Categoria() {
        this.id = "";
        this.nombre = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return id +"-"+ nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(id, categoria.id) && Objects.equals(nombre, categoria.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }
}