package pos.logic;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;

import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Categoria {
    @XmlID
    private String codigo;
    private String nombreCategoria;

    public Categoria(String codigo, String nombreCategoria) {
        this.codigo = codigo;
        this.nombreCategoria = nombreCategoria;

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    @Override
    public String toString() {
        return codigo+"-"+nombreCategoria;
    }
}