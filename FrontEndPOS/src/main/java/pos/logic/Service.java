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

    public Service(){
        try{
            socket = new Socket(Protocol.SERVER, Protocol.PORT);
            os=new ObjectOutputStream(socket.getOutputStream());
            is=new ObjectInputStream(socket.getInputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

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

    @Override
    public void create(Producto producto) throws Exception {

    }

    @Override
    public Producto read(Producto producto) throws Exception {
        return null;
    }

    @Override
    public void update(Producto producto) throws Exception {

    }

    @Override
    public void delete(Producto producto) throws Exception {

    }

    @Override
    public List<Producto> search(Producto producto) {
        return List.of();
    }

    @Override
    public List<Categoria> search(Categoria categoria) {
        return List.of();
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
        if(is.readInt()==Protocol.ERROR_NO_ERROR){
            return true;
        }else{
            return false;
        }
    }
}
