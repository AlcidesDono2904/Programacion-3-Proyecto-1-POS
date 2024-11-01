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

    String sid;
    private static Service theInstance;

    public static Service instance(){
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    public void stop(){
        try{
            os.writeInt(Protocol.LOGOUT);
            os.flush();
            socket.close();
            os.close();
            is.close();
        }catch(Exception e){
            e.printStackTrace();

        }
    }

    private Service(){
        try{
            socket = new Socket(Protocol.SERVER, Protocol.PORT);
            os=new ObjectOutputStream(socket.getOutputStream());
            is=new ObjectInputStream(socket.getInputStream());

            os.writeInt(Protocol.SYNC);
            os.flush();
            sid=(String)is.readObject();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public String getSid(){
        return sid;
    }
    //CLIENTE
    @Override
    public void create(Cliente cliente) throws Exception {
        os.writeInt(Protocol.CLIENTE_CREATE);
        os.writeObject(cliente);
        os.flush();
        if(is.readInt()==Protocol.ERROR){
            throw new Exception("Ya existe un cliente con ese mismo codigo");
        }
    }

    @Override
    public Cliente read(Cliente cliente) throws Exception {
        os.writeInt(Protocol.CLIENTE_READ);
        os.writeObject(cliente);
        os.flush();
        if(is.readInt()==Protocol.ERROR){
            throw new Exception("No existe un cliente con ese codigo");
        }
        return (Cliente) is.readObject();
    }

    @Override
    public void update(Cliente cliente) throws Exception {
        os.writeInt(Protocol.CLIENTE_UPDATE);
        os.writeObject(cliente);
        os.flush();
        if(is.readInt()==Protocol.ERROR){
            throw new Exception("Error al modificar");
        }
    }

    @Override
    public void delete(Cliente cliente) throws Exception {
        os.writeInt(Protocol.CLIENTE_DELETE);
        os.writeObject(cliente);
        os.flush();
        if(is.readInt()==Protocol.ERROR){
            throw new Exception("Error al eliminar");
        }
    }

    @Override
    public List<Cliente> search(Cliente cliente) {

        List<Cliente> r = new ArrayList<>();
        try{
            os.writeInt(Protocol.CLIENTE_SEARCH);
            os.writeObject(cliente);
            os.flush();
            if(is.readInt()==Protocol.ERROR_NO_ERROR){
                r= (List<Cliente>) is.readObject();
            }else {
                throw new Exception("Error al buscar");
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return r;
    }
    //CAJERO
    @Override
    public void create(Cajero cajero) throws Exception {
        os.writeInt(Protocol.CAJERO_CREATE);
        os.writeObject(cajero);
        os.flush();
        if(is.readInt()==Protocol.ERROR){
            throw new Exception("Error al crear");
        }
    }

    @Override
    public Cajero read(Cajero cajero) throws Exception {
        os.writeInt(Protocol.CAJERO_READ);
        os.writeObject(cajero);
        os.flush();
        if(is.readInt()==Protocol.ERROR){
            throw new Exception("Error al buscar");
        }
        return (Cajero) is.readObject();
    }

    @Override
    public void update(Cajero cajero) throws Exception {
        os.writeInt(Protocol.CAJERO_UPDATE);
        os.writeObject(cajero);
        os.flush();
        if(is.readInt()==Protocol.ERROR){
            throw new Exception("Error al modificar");
        }
    }

    @Override
    public void delete(Cajero cajero) throws Exception {
        os.writeInt(Protocol.CAJERO_DELETE);
        os.writeObject(cajero);
        os.flush();
        if(is.readInt()==Protocol.ERROR){
            throw new Exception("Error al eliminar");
        }
    }

    @Override
    public List<Cajero> search(Cajero cajero) {
        List<Cajero> r = new ArrayList<>();
        try{
            os.writeInt(Protocol.CAJERO_SEARCH);
            os.writeObject(cajero);
            os.flush();
            if(is.readInt()==Protocol.ERROR_NO_ERROR){
                r= (List<Cajero>) is.readObject();
            }else{
                throw new Exception("Error al buscar");
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return r;
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
            System.out.println(ex.getMessage());
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
        os.writeInt(Protocol.FACTURA_CREATE);
        os.writeObject(factura);
        os.flush();
        if(is.readInt()==Protocol.ERROR){
            throw new Exception("Error al crear");
        }
    }

    @Override
    public List<Factura> searchFacturas(Factura factura) {
        List<Factura> result = new ArrayList<>();
        try {
            os.writeInt(Protocol.FACTURA_SEARCH);
            os.writeObject(factura);
            os.flush();
            if(is.readInt()==Protocol.ERROR_NO_ERROR){
                result =(List<Factura>) is.readObject();
            }else {
                throw new Exception("Error lista facturas");
            }
        }catch (Exception ex){
            System.out.println(ex);
        }
        return result;
    }

    @Override
    public List<Linea> searchLineas(Factura factura) {
        List<Linea> result = new ArrayList<>();
        try{
            os.writeInt(Protocol.LINEA_SEARCH);
            os.writeObject(factura);
            os.flush();
            int n=is.readInt();
            if(n==Protocol.ERROR_NO_ERROR){
                result =(List<Linea>) is.readObject();
            }else{
                System.out.println(n);
                throw new Exception("Error EN lista lineas");
            }
        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public double importeFactura(Factura factura) throws Exception {
        os.writeInt(Protocol.IMPORTE_FACTURA);
        os.writeObject(factura);
        os.flush();
        if(is.readInt()==Protocol.ERROR){
            throw new Exception("Error al buscar importe de factura");
        }
        return is.readDouble();
    }

    @Override
    public Rango rangoCategoria(Categoria categoria, Date date, Date date1) throws Exception {
        List<Object> objs=new ArrayList<>();
        objs.add(categoria);
        objs.add(date);
        objs.add(date1);
        os.writeInt(Protocol.RANGO_CATEGORIA);
        os.writeObject(objs);
        os.flush();
        if(is.readInt()==Protocol.ERROR){
            throw new Exception("Error al buscar rango de categoria");
        }
        return (Rango) is.readObject();
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

    public boolean validate(List<Linea> lineas) throws Exception {
        os.writeInt(Protocol.VALIDATE);
        os.writeObject(lineas);
        os.flush();
        if(is.readInt()==Protocol.ERROR){
            throw new Exception("Error en el validacion");
        }
        return is.readBoolean();
    }

    public List<Usuario> requestUsers(){
        List<Usuario> result = new ArrayList<>();
        try {
            os.writeInt(Protocol.USERS);
            os.flush();
            if(is.readInt()==Protocol.ERROR_NO_ERROR){
                result=(List<Usuario>) is.readObject();
            }else{
                throw new Exception("Error buscar usuarios logeados");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    public void sendFactura(MensajeFactura mf) throws Exception {
        os.writeInt(Protocol.SEND_FACTURA);
        os.writeObject(mf);
        os.flush();
        if(is.readInt()==Protocol.ERROR){
            throw new Exception("Error en el send de factura");
        }

    }
}