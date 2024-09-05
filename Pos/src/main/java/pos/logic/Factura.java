package pos.logic;

import java.util.List;

public class Factura {
    private Cliente cliente;
    private Cajero cajero;
    private List<Linea> lineas;


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
    }

    @Override
    public String toString() {
        return "Factura{" +
                "cliente=" + cliente +
                ", cajero=" + cajero +
                ", lineas=" + lineas +
                '}';
    }

    //agregar algunos metodos como calcular subtotal, total, etc.


}
