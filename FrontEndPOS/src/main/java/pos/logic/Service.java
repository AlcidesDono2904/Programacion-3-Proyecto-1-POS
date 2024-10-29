package pos.logic;

import entidades.logic.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Service implements IService {
    Socket socket;
    ObjectOutputStream os;
    ObjectInputStream is;

    private static Service theInstance;

    public static Service instance(){
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    public void stop(){
        //TODO
    }

    public Service(){
        try{
            socket = new Socket(Protocol.SERVER, Protocol.PORT);
            os=new ObjectOutputStream(socket.getOutputStream());
            is=new ObjectInputStream(socket.getInputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //CLIENTE
    @Override
    public void create(Cliente cliente) throws Exception {

    }

    @Override
    public Cliente read(Cliente cliente) throws Exception {
        return null;
    }

    @Override
    public void update(Cliente cliente) throws Exception {

    }

    @Override
    public void delete(Cliente cliente) throws Exception {

    }

    @Override
    public List<Cliente> search(Cliente cliente) {
        return List.of();
    }
    //CAJERO
    @Override
    public void create(Cajero cajero) throws Exception {

    }

    @Override
    public Cajero read(Cajero cajero) throws Exception {
        return null;
    }

    @Override
    public void update(Cajero cajero) throws Exception {

    }

    @Override
    public void delete(Cajero cajero) throws Exception {

    }

    @Override
    public List<Cajero> search(Cajero cajero) {
        return List.of();
    }
    //PRODUCTO
    @Override
    public void create(Producto producto) throws Exception {
        os.writeInt(Protocol.PRODUCTO_CREATE);
        os.writeObject(producto);
        os.flush();
        if(is.readInt()==Protocol.ERROR){
            throw new Exception("Error al crear el producto/Producto con ese codigo ya existe");
        }
    }

    @Override
    public Producto read(Producto producto) throws Exception {
        os.writeInt(Protocol.PRODUCTO_READ);
        os.writeObject(producto);
        os.flush();
        if(is.readInt()==Protocol.ERROR_NO_ERROR){
            return (Producto) is.readObject();
        }else{
            throw new Exception("No se encontro el producto con codigo "+producto.getCodigo());
        }
    }

    @Override
    public void update(Producto producto) throws Exception {
        os.writeInt(Protocol.PRODUCTO_UPDATE);
        os.writeObject(producto);
        os.flush();
        if(is.readInt()==Protocol.ERROR){
            throw new Exception("No se encontro el producto con codigo "+producto.getCodigo());
        }
    }

    @Override
    public void delete(Producto producto) throws Exception {
        os.writeInt(Protocol.PRODUCTO_DELETE);
        os.writeObject(producto);
        os.flush();
        if(is.readInt()==Protocol.ERROR){
            throw new Exception("No se encontro el producto con codigo "+producto.getCodigo());
        }
    }

    @Override
    public List<Producto> search(Producto producto) {
        List<Producto> result = new ArrayList<>();
        try{
            os.writeInt(Protocol.PRODUCTO_SEARCH);
            os.writeObject(producto);
            os.flush();
            if(is.readInt()==Protocol.ERROR_NO_ERROR){
                result =(List<Producto>) is.readObject();
            }else{
                throw new Exception("Error lista productos");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
    //categoria
    @Override
    public List<Categoria> search(Categoria categoria) {
        List<Categoria> result = new ArrayList<>();
        try{
            os.writeInt(Protocol.CATEGORIA_SEARCH);
            os.writeObject(categoria);
            os.flush();
            if(is.readInt()==Protocol.ERROR_NO_ERROR){
                result =(List<Categoria>) is.readObject();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public void create(Factura factura) throws Exception {

    }

    @Override
    public List<Factura> searchFacturas(Factura factura) {
        return List.of();
    }

    @Override
    public List<Linea> searchLineas(Factura factura) {
        return List.of();
    }

    @Override
    public double importeFactura(Factura factura) throws Exception {
        return 0;
    }

    @Override
    public Rango rangoCategoria(Categoria categoria, Date date, Date date1) throws Exception {
        return null;
    }

    @Override
    public boolean login(String usuario, String clave) throws Exception {
        List<String> credenciales= new ArrayList<String>();
        credenciales.add(usuario);
        credenciales.add(clave);
        os.writeInt(Protocol.LOGIN);
        os.writeObject(credenciales);
        os.flush();
        return is.readInt() == Protocol.ERROR_NO_ERROR;
    }
}
