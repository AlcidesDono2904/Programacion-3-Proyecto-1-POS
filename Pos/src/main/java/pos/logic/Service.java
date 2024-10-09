package pos.logic;

import pos.data.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private static Service theInstance;

    public static Service instance(){
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }
    private Data data;
    private ProductoDao productoDao;
    private CategoriaDao categoriaDao;
    private ClienteDao clienteDao;
    private CajeroDao cajeroDao;

    private Service(){
        try{
            productoDao = new ProductoDao();
            categoriaDao = new CategoriaDao();
            clienteDao = new ClienteDao();
            cajeroDao = new CajeroDao();
            data= XmlPersister.instance().load();
        }
        catch(Exception e){
            data =  new Data();
        }
    }

    public void stop(){
        try {
            XmlPersister.instance().store(data);
        } catch (Exception e) {
            System.out.println(e);
        }
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
        Factura result = data.getFactura().stream().filter(i->i.getCodigo().equals(e.getCodigo())).findFirst().orElse(null);
        if (result==null) {
            e.setFecha(LocalDate.now());
            e.setCodigo("FAC-"+(data.getFactura().size()+1));
            data.getFactura().add(e);
        } else throw new Exception("Factura ya existe");
    }
    public List<Factura> searchFacturas(Factura e) {
        return data.getFactura().stream()
                .filter(i -> i.getCliente() != null && i.getCliente().getNombre() != null) // Validar que no sean nulos
                .filter(i -> e.getCliente() != null && e.getCliente().getNombre() != null) // Validar factura a buscar
                .filter(i -> i.getCliente().getNombre().contains(e.getCliente().getNombre())) // Filtro real
                .sorted(Comparator.comparing(Factura::getCodigo)) // Ordenar por código
                .collect(Collectors.toList());
    }


    //estadistica
    public List<LocalDate> buscarRangoFechas(){
        Factura filtro=new Factura();
        List<Factura> facturas=data.getFactura().stream()
                .filter(i->i.getCodigo().contains(filtro.getCodigo()))
                .sorted(Comparator.comparing(Factura::getCodigo))
                .collect(Collectors.toList());

        List<LocalDate> fechas=new ArrayList<>();
        for(Factura f:facturas){
            if(!fechas.contains(f.getFecha()))
                fechas.add(f.getFecha());
        }

        return fechas;
    }

    public List<Rango> buscarRango(){
        List<Rango> rangos=new ArrayList<>();
        List<Categoria> categorias= search(new Categoria());

        List<Factura> facturas=data.getFactura();

        for(Categoria c:categorias){//crear rangos de todas las categorias
            Rango r=new Rango();
            r.setCategoria(c);
            rangos.add(r);
        }

        for(LocalDate l:buscarRangoFechas()){
            List<Linea> lineas=new ArrayList<>();
            for(Factura f:facturas){
                if(f.getFecha().equals(l)){
                    lineas.addAll(f.getLineas().stream().toList());
                }
            }
            for(int i=0;i<categorias.size();i++){
                double importe=0;
                for(int j=0;j<lineas.size();j++){
                    if(lineas.get(j).getProducto().getCategoria().equals(categorias.get(i))){
                        importe+=lineas.get(j).importe();
                    }
                }
                rangos.get(i).getImportes().add(importe);
            }
        }

        return rangos;
    }
}