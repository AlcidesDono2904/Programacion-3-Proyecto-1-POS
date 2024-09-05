package pos.logic;

import pos.data.Data;
import pos.data.XmlPersister;

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

    private Service(){
        try{
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
        Cliente result = data.getClientes().stream().filter(i->i.getId().equals(e.getId())).findFirst().orElse(null);
        if (result==null) data.getClientes().add(e);
        else throw new Exception("Cliente ya existe");
    }

    public Cliente read(Cliente e) throws Exception{
        Cliente result = data.getClientes().stream().filter(i->i.getId().equals(e.getId())).findFirst().orElse(null);
        if (result!=null) return result;
        else throw new Exception("Cliente no existe");
    }

    public void update(Cliente e) throws Exception{
        Cliente result;
        try{
            result = this.read(e);
            data.getClientes().remove(result);
            data.getClientes().add(e);
        }catch (Exception ex) {
            throw new Exception("Cliente no existe");
        }
    }

    public void delete(Cliente e) throws Exception{
        data.getClientes().remove(e);
    }

    public List<Cliente> search(Cliente e){
        return data.getClientes().stream()
                .filter(i->i.getNombre().contains(e.getNombre()))
                .sorted(Comparator.comparing(Cliente::getNombre))
                .collect(Collectors.toList());
    }

//================= CAJEROS ============
public void create(Cajero e) throws Exception{
    Cajero result = data.getCajero().stream().filter(i->i.getId().equals(e.getId())).findFirst().orElse(null);
    if (result==null) data.getCajero().add(e);
    else throw new Exception("Cliente ya existe");
}
    public Cajero read(Cajero e) throws Exception{
        Cajero result = data.getCajero().stream().filter(i->i.getId().equals(e.getId())).findFirst().orElse(null);
        if (result!=null) return result;
        else throw new Exception("Cliente no existe");
    }

    public void update(Cajero e) throws Exception{
        Cajero result;
        try{
            result = this.read(e);
            data.getCajero().remove(result);
            data.getCajero().add(e);
        }catch (Exception ex) {
            throw new Exception("Cajero no existe");
        }
    }

    public void delete(Cajero e) throws Exception{
        data.getCajero().remove(e);
    }

    public List<Cajero> search(Cajero e){
        return data.getCajero().stream()
                .filter(i->i.getNombre().contains(e.getNombre()))
                .sorted(Comparator.comparing(Cajero::getNombre))
                .collect(Collectors.toList());
    }
//Productos
    /*
public void create(Productos e) throws Exception{
    Cajero result = data.getCajero().stream().filter(i->i.getId().equals(e.getId())).findFirst().orElse(null);
    if (result==null) data.getCajero().add(e);
    else throw new Exception("Cliente ya existe");
}
    public Cajero read(Productos e) throws Exception{
        Cajero result = data.getCajero().stream().filter(i->i.getId().equals(e.getId())).findFirst().orElse(null);
        if (result!=null) return result;
        else throw new Exception("Cliente no existe");
    }

    public void update(Productos e) throws Exception{
        Cajero result;
        try{
            result = this.read(e);
            data.getCajero().remove(result);
            data.getCajero().add(e);
        }catch (Exception ex) {
            throw new Exception("Cajero no existe");
        }
    }

    public void delete(Productos e) throws Exception{
        data.getCajero().remove(e);
    }

    public List<Productos> search(Productos e){
        return data.getCajero().stream()
                .filter(i->i.getNombre().contains(e.getNombre()))
                .sorted(Comparator.comparing(Cajero::getNombre))
                .collect(Collectors.toList());
    }*/
 }