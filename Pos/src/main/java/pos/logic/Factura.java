package pos.logic;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;
import java.util.List;
@XmlAccessorType(XmlAccessType.FIELD)
public class Factura {
    @XmlID
    private String codigo;
    @XmlIDREF
    private Cliente cliente;
    @XmlIDREF
    private Cajero cajero;
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    LocalDate fecha;
    @XmlElementWrapper(name="lineas")
    @XmlElement(name="linea")
    private List<Linea> lineas;

    public Factura(String codigo, Cliente cliente, Cajero cajero, List<Linea> lineas) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.cajero = cajero;
        this.fecha = LocalDate.now(); // Establece la fecha actual
        this.lineas = lineas;

    }

    public Factura() {this("",null,null,null);}

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cajero getCajero() {
        return cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }

    public List<Linea> getLineas() {
        return lineas;
    }

    public void setLineas(List<Linea> lineas) {
        this.lineas = lineas;
        int i=1;
        for (Linea l:lineas){
            l.setCodigo("LIN-"+(i++));
        }
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "codigo='" + codigo + '\'' +
                ", cliente=" + cliente +
                ", cajero=" + cajero +
                ", fecha=" + fecha +
                ", lineas=" + lineas +
                '}';
    }
    public double importe(){
        double total = 0;
        for (Linea l:lineas){
            total+=l.importe();
        }
        return total;
    }

}