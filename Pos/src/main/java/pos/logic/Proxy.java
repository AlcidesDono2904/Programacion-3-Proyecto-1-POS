package pos.logic;

import java.util.List;

public class Proxy implements IService{//TODO

    @Override
    public void create(Cliente e) throws Exception {

    }

    @Override
    public Cliente read(Cliente e) throws Exception {
        return null;
    }

    @Override
    public void update(Cliente e) throws Exception {

    }

    @Override
    public void delete(Cliente e) throws Exception {

    }

    @Override
    public List<Cliente> search(Cliente e) {
        return List.of();
    }

    @Override
    public void create(Cajero e) throws Exception {

    }

    @Override
    public Cajero read(Cajero e) throws Exception {
        return null;
    }

    @Override
    public void update(Cajero e) throws Exception {

    }

    @Override
    public void delete(Cajero e) throws Exception {

    }

    @Override
    public List<Cajero> search(Cajero e) {
        return List.of();
    }

    @Override
    public void create(Producto e) throws Exception {

    }

    @Override
    public Producto read(Producto e) throws Exception {
        return null;
    }

    @Override
    public void update(Producto e) throws Exception {

    }

    @Override
    public void delete(Producto e) throws Exception {

    }

    @Override
    public List<Producto> search(Producto e) {
        return List.of();
    }

    @Override
    public List<Categoria> search(Categoria e) {
        return List.of();
    }

    @Override
    public void create(Factura e) throws Exception {

    }

    @Override
    public List<Factura> searchFacturas(Factura e) {
        return List.of();
    }

    @Override
    public List<Linea> searchLineas(Factura e) {
        return List.of();
    }

    @Override
    public double importeFactura(Factura e) throws Exception {
        return 0;
    }
}
