package pos.data;

import pos.logic.*;//Importa todas las clases de logic
import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {
    //Atributos

    @XmlElementWrapper(name = "facturas")
    @XmlElement(name = "factura")
    private List<Factura> factura;

    public Data() {
        factura = new ArrayList<>();

    }

    public List<Factura> getFactura() {return factura;}
}

