package entidades.logic;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Factura implements Serializable {

    private String codigo;
    private Cliente cliente;
    private Cajero cajero;
    LocalDate fecha;
    private List<Linea> lineas;

    public Factura(String codigo, Cliente cliente, Cajero cajero, List<Linea> lineas) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.cajero = cajero;
        this.fecha = LocalDate.now(); // Establece la fecha actual
        this.lineas = lineas;

    }

    public Factura() {this("",new Cliente(),new Cajero(),new ArrayList<>());}

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
    public double importe(){//precio con descuento de linea y descuento de cliente
        double total = 0;
        for (Linea l:lineas){
            total+=l.importe();
        }
        total=(total*(100-cliente.getDescuento())/100);
        return total;
    }

    public int articulos(){//cantidad articulos
        int sum=0;
        for(Linea l : lineas){
            sum+=l.getCantidad();
        }
        return sum;
    }

    public double subTotal(){//precio multiplicado por cantidad, no considera descuentos
        double sum=0;
        for(Linea l : lineas){
            sum+=l.getProducto().getPrecioUnitario()*l.getCantidad();
        }
        return sum;
    }

    public double descuento(){//el precio que ha sido descontando (subtotal-(subtotal*descuento))
        return subTotal()-importe();
    }
}