package pos.logic;

import entidades.logic.*;
import pos.data.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Service implements IService{
    private static Service theInstance;

    public static Service instance(){
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    private ProductoDao productoDao;
    private CategoriaDao categoriaDao;
    private ClienteDao clienteDao;
    private CajeroDao cajeroDao;
    private LineaDao lineaDao;
    private FacturaDao facturaDao;

    private Service(){
        productoDao = new ProductoDao();
        categoriaDao = new CategoriaDao();
        clienteDao = new ClienteDao();
        cajeroDao = new CajeroDao();
        lineaDao = new LineaDao();
        facturaDao = new FacturaDao();
    }

//================= CLIENTES ============

    public void create(Cliente e) throws Exception{
        clienteDao.create(e);
    }

    public Cliente read(Cliente e) throws Exception{
        return clienteDao.read(e.getId());
    }

    public void update(Cliente e) throws Exception{
        clienteDao.update(e);
    }

    public void delete(Cliente e) throws Exception{
        clienteDao.delete(e);
    }

    public List<Cliente> search(Cliente e){
        try{
            return clienteDao.search(e);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    //================= CAJEROS ============
    public void create(Cajero e) throws Exception{
        cajeroDao.create(e);
    }
    public Cajero read(Cajero e) throws Exception{
        return cajeroDao.read(e.getId());
    }

    public void update(Cajero e) throws Exception{
        cajeroDao.update(e);
    }

    public void delete(Cajero e) throws Exception{
        cajeroDao.delete(e);
    }

    public List<Cajero> search(Cajero e){
        try{
            return cajeroDao.search(e);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }

    }
    //Categoria

    public List<Categoria> search(Categoria e) {
        try {
            return categoriaDao.search(e);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    //Producto
    public void create(Producto e) throws Exception {
        productoDao.create(e);
    }

    public Producto read(Producto e) throws Exception {
        return productoDao.read(e.getCodigo());
    }

    public void update(Producto e) throws Exception {
        productoDao.update(e);
    }

    public void delete(Producto e) throws Exception {
        productoDao.delete(e);
    }

    public List<Producto> search(Producto e) {
        try {
            return productoDao.search(e);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    //Factura

    public void create(Factura e) throws Exception{
        facturaDao.Create(e);
    }
    public List<Factura> searchFacturas(Factura e) {
        try{
            return facturaDao.search(e);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    //Linea

    public List<Linea> searchLineas(Factura f){
        try{
            return lineaDao.search(f);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }

    }

    //Auxiliares
    public double importeFactura(Factura e) throws Exception {
        return facturaDao.importeFactura(e);
    }


    //estadistica

    public Rango rangoCategoria(Categoria c, Date inicio, Date fin)throws Exception{
        Rango r=lineaDao.searchRangoCategoria(c,((inicio.getYear()+1900)+"-"+(inicio.getMonth()+1)),((fin.getYear()+1900)+"-"+(fin.getMonth()+1)));
        r.setCategoria(categoriaDao.read(c.getId()));
        return r;
    }
}