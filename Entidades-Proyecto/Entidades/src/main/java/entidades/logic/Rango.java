package entidades.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Rango {
    Categoria categoria;
    List<Double> importes;
    List<String> fechas;

    public Rango(Categoria categoria, List<Double> importes) {
        this.categoria = categoria;
        this.importes = importes;
    }

    public Rango(){
        this.categoria = new Categoria();
        this.importes = new ArrayList<Double>();
        this.fechas=new ArrayList<String>();
    }

    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public List<Double> getImportes() {
        return importes;
    }
    public void setImportes(List<Double> importes) {
        this.importes = importes;
    }
    public List<String> getFechas() {return fechas;}
    public void setFechas(List<String> fechas) {this.fechas = fechas;}


    @Override
    public String toString() {

        return "Categoria: " + categoria
                + "\nImportes: " + importes
                + "\nFechas: " + fechas;

    }
}
