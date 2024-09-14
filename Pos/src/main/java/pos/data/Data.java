package pos.data;

import pos.logic.*;
import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {

    @XmlElementWrapper(name = "clientes")
    @XmlElement(name = "cliente")
    private List<Cliente> clientes;
    @XmlElementWrapper(name = "cajeros")
    @XmlElement(name = "cajero")
    private List<Cajero> cajero;

    @XmlElementWrapper(name="categorias")
    @XmlElement(name="categoria")
    private List<Categoria> categorias;


    @XmlElementWrapper(name = "productos")
    @XmlElement(name = "producto")
    private List<Producto> producto;



    @XmlElementWrapper(name = "facturas")
    @XmlElement(name = "factura")
    private List<Factura> factura;
    


    public Data() {
        clientes = new ArrayList<>();
        cajero = new ArrayList<>();
        producto = new ArrayList<>();
        categorias = new ArrayList<>();

    }

    public List<Cliente> getClientes() {
        return clientes;
    }
    public List<Cajero> getCajero() {return cajero; }
    public List<Producto> getProducto() {return producto; }
    public List<Factura> getFactura() {return factura;}
    public List<Categoria> getCategorias() {return categorias; }

}
