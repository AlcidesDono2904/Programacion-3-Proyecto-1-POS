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
    private List<Cajero> cajero;

    public Data() {
        clientes = new ArrayList<>();
        cajero = new ArrayList<>();
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
    public List<Cajero> getCajero() {
        return cajero;
    }

}