package pos.presentation.estadistica;

import pos.logic.Categoria;
import pos.logic.Rango;
import pos.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    private List<LocalDate> fechas;
    private List<Rango> rangos;
    private Rango actual;

    private List<Categoria> categorias;
    private List<LocalDate> fechasCB;


    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        //firePropertyChange(TABLA);
        firePropertyChange(CB);
    }

    public Model() {
        fechas = new ArrayList<LocalDate>();
        rangos = new ArrayList<Rango>();
    }

    public void init(List<Rango>rangos,List<LocalDate>fechas,List<LocalDate>fechasCB,List<Categoria>categorias){
        this.rangos = rangos;
        this.fechas = fechas;
        this.fechasCB = fechasCB;
        this.categorias = categorias;
        //firePropertyChange(CB);
    }

    public List<Rango> getRangos() {
        return rangos;
    }

    public void setRangos(List<Rango> rangos) {
        this.rangos = rangos;
    }

    public List<LocalDate> getFechas() {
        return fechas;
    }

    public void setFechas(List<LocalDate> fechas) {
        this.fechas = fechas;
    }

    public Rango getActual() {
        return actual;
    }
    public void setActual(Rango actual) {
        this.actual = actual;

    }
    public List<Categoria> getCategorias() {
        return categorias;
    }
    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
    public List<LocalDate> getFechasCB() {
        return fechasCB;
    }
    public void setFechasCB(List<LocalDate> fechasCB) {
        this.fechasCB = fechasCB;
    }

    public void datos(List<Rango> rangos, List<LocalDate> localDates) {
        this.rangos = rangos;
        this.fechas = localDates;
        firePropertyChange(TABLA);
    }

    public static final String TABLA="tabla";
    public static final String CB="cb";



}
