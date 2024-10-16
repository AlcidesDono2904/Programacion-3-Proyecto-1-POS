package pos.logic;

import java.util.List;

public interface IService {
    //Cliente
    void create(Cliente e) throws Exception;
    Cliente read(Cliente e) throws Exception;
    void update(Cliente e) throws Exception;
    void delete(Cliente e) throws Exception;
    List<Cliente> search(Cliente e);

    //Cajero
    void create(Cajero e) throws Exception;
    Cajero read(Cajero e) throws Exception;
    void update(Cajero e) throws Exception;
    void delete(Cajero e) throws Exception;
    List<Cajero> search(Cajero e);

    //Producto
    void create(Producto e) throws Exception;
    Producto read(Producto e) throws Exception;
    void update(Producto e) throws Exception;
    void delete(Producto e) throws Exception;
    List<Producto> search(Producto e);

    //Categoría
    List<Categoria> search(Categoria e);

    //Factura
    void create(Factura e) throws Exception;
    List<Factura> searchFacturas(Factura e);

    //Linea
    List<Linea> searchLineas(Factura e);

    //Auxiliares
    double importeFactura(Factura e)throws Exception;

}
