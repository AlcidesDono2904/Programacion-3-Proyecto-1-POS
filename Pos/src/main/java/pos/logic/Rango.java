package pos.logic;

import java.util.ArrayList;
import java.util.List;

public class Rango {
    Categoria categoria;
    List<Double> importes;

    public Rango(Categoria categoria, List<Double> importes) {
        this.categoria = categoria;
        this.importes = importes;
    }

    public Rango(){
        this.categoria = new Categoria();
        this.importes = new ArrayList<Double>();
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


    @Override
    public String toString() {
        return "rango";
    }
}
