package pos.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttachmentRef;
import jakarta.xml.bind.annotation.XmlID;

import java.util.List;
//@XmlAccessorType(XmlAccessType.FIELD)
public class Factura {
   // @XmlID
    private String codigo;
    private Cliente cliente;
    private Cajero cajero;
    //@XmlAttachmentRef
    private List<Linea> lineas;

    public Factura(String codigo, Cliente cliente, Cajero cajero, List<Linea> lineas) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.cajero = cajero;
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
    }

    @Override
    public String toString() {
        return "Factura{" +
                "cliente=" + cliente +
                ", cajero=" + cajero +
                ", lineas=" + lineas +
                '}';
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    //agregar algunos metodos como calcular subtotal, total, etc.


}