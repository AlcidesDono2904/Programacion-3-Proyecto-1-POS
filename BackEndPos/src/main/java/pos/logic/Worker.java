package pos.logic;

import entidades.logic.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Worker {
    ObjectInputStream is;
    ObjectOutputStream os;
    Server srv;
    Socket socket;
    IService service;
    private boolean continuar;

    String sid;
    Socket as;
    ObjectInputStream ais;
    ObjectOutputStream aos;

    String nombre;
    Worker(Server srv, Socket s, ObjectOutputStream os,ObjectInputStream is,IService serv,String SID){
        this.srv = srv;
        this.socket = s;
        this.service = serv;
        this.continuar = true;
        this.sid = SID;
        this.is=is;
        this.os=os;
        this.nombre=" ";
    }

    public void setAs(Socket as,ObjectOutputStream os, ObjectInputStream is){
        this.as = as;
        this.aos = os;
        this.ais = is;
    }

    public void start(){
        try{
            System.out.println("Iniciando worker...");
            Thread t=new Thread(new Runnable(){
                public void run(){listen();}
            });
            continuar=true;
            t.start();
        }catch (Exception ex){ex.printStackTrace();}
    }

    public void stop(){
        continuar=false;
        try{
            System.out.println("Terminando worker...");
            os.close();
            is.close();
            socket.close();
            as.close();
            ais.close();
            aos.close();
            srv.remove(this);
        }catch (Exception ex){ex.printStackTrace();}
    }
    public void listen(){
        int method;
        while(continuar){
            try{
                method=is.readInt();
                System.out.println("Operacion = "+method);
                switch(method){
                    case Protocol.LOGIN:
                        List<String> usuario=(ArrayList<String>) is.readObject();
                        try{
                            if(service.login(usuario.getFirst(),usuario.getLast())){
                                os.writeInt(Protocol.ERROR_NO_ERROR);
                                nombre=usuario.getFirst();
                                srv.notificarLogeo(this,new Usuario(sid,nombre));
                            }else{
                                os.writeInt(Protocol.ERROR);
                            }
                        }catch(Exception ex){
                            os.writeInt(Protocol.ERROR);
                            ex.printStackTrace();
                        }
                        break;
                    case Protocol.LOGOUT:
                        continuar=false;
                        srv.notificarDeslogeo(this,new Usuario(sid,nombre));
                        stop();
                        break;
                    case Protocol.PRODUCTO_CREATE:
                        try{
                            Producto p=(Producto)is.readObject();
                            service.create(p);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        }catch(Exception ex){
                            os.writeInt(Protocol.ERROR);
                            ex.printStackTrace();
                        }
                        break;
                    case Protocol.PRODUCTO_READ:
                        try{
                            Producto p=(Producto)is.readObject();
                            Producto r=service.read(p);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(r);
                        }catch (Exception ex){
                            os.writeInt(Protocol.ERROR);
                            ex.printStackTrace();
                        }
                        break;
                    case Protocol.PRODUCTO_UPDATE:
                        try{
                            Producto p=(Producto)is.readObject();
                            service.update(p);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        }catch (Exception ex){
                            os.writeInt(Protocol.ERROR);
                            ex.printStackTrace();
                        }
                        break;
                    case Protocol.PRODUCTO_DELETE:
                        try{
                            Producto p=(Producto)is.readObject();
                            service.delete(p);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        }catch (Exception ex){
                            os.writeInt(Protocol.ERROR);
                            ex.printStackTrace();
                        }
                        break;
                    case Protocol.PRODUCTO_SEARCH:
                        try{
                            Producto p=(Producto)is.readObject();
                            List<Producto> r=service.search(p);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(r);
                        }catch (Exception ex){
                            os.writeInt(Protocol.ERROR);
                            ex.printStackTrace();
                        }
                        break;
                    case Protocol.CATEGORIA_SEARCH:
                        try{
                            Categoria c=(Categoria)is.readObject();
                            List<Categoria> r=service.search(c);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(r);
                        }catch (Exception ex){
                            os.writeInt(Protocol.ERROR);
                            ex.printStackTrace();
                        }
                        break;
                    case Protocol.CLIENTE_CREATE:
                        try{
                            Cliente c=(Cliente)is.readObject();
                            service.create(c);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        }catch (Exception ex){
                            os.writeInt(Protocol.ERROR);
                            ex.printStackTrace();
                        }
                        break;
                    case Protocol.CLIENTE_READ:
                        try{
                            Cliente c=(Cliente)is.readObject();
                            Cliente r=service.read(c);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(r);
                        }catch (Exception ex){
                            os.writeInt(Protocol.ERROR);
                        }
                        break;
                    case Protocol.CLIENTE_UPDATE:
                        try{
                            Cliente c=(Cliente)is.readObject();
                            service.update(c);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        }catch (Exception ex){
                            os.writeInt(Protocol.ERROR);
                        }
                        break;
                    case Protocol.CLIENTE_DELETE:
                        try{
                            Cliente c=(Cliente)is.readObject();
                            service.delete(c);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        }catch (Exception ex){
                            os.writeInt(Protocol.ERROR);
                        }
                        break;
                    case Protocol.CLIENTE_SEARCH:
                        try{
                            Cliente c=(Cliente)is.readObject();
                            List<Cliente> r=service.search(c);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(r);
                        }catch (Exception ex){
                            os.writeInt(Protocol.ERROR);
                        }
                        break;
                    case Protocol.CAJERO_CREATE:
                        try{
                            Cliente c=(Cliente)is.readObject();
                            service.create(c);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        }catch (Exception ex){
                            os.writeInt(Protocol.ERROR);
                        }
                        break;
                    case Protocol.CAJERO_READ:
                        try{
                            Cajero c=(Cajero)is.readObject();
                            Cajero r=service.read(c);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(r);
                        }catch (Exception ex){
                            os.writeInt(Protocol.ERROR);
                        }
                        break;
                    case Protocol.CAJERO_UPDATE:
                        try {
                            Cajero c=(Cajero)is.readObject();
                            service.update(c);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        }catch (Exception ex){
                            os.writeInt(Protocol.ERROR);
                        }
                        break;
                    case Protocol.CAJERO_DELETE:
                        try{
                            Cajero c=(Cajero)is.readObject();
                            service.delete(c);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        }catch (Exception ex){
                            os.writeInt(Protocol.ERROR);
                        }
                        break;
                    case Protocol.CAJERO_SEARCH:
                        try {
                            Cajero c=(Cajero)is.readObject();
                            List<Cajero> r=service.search(c);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(r);
                        }catch (Exception ex){
                            os.writeInt(Protocol.ERROR);
                        }
                        break;
                    case Protocol.FACTURA_CREATE:
                        try {
                            Factura f=(Factura)is.readObject();
                            service.create(f);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        }catch (Exception ex){
                            os.writeInt(Protocol.ERROR);
                            ex.printStackTrace();
                        }
                        break;
                    case Protocol.FACTURA_SEARCH:
                        try{
                            Factura f=(Factura)is.readObject();
                            List<Factura> r=service.searchFacturas(f);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(r);
                        }catch (Exception ex){
                            os.writeInt(Protocol.ERROR);
                            ex.printStackTrace();
                        }
                        break;
                    case Protocol.LINEA_SEARCH:
                        try{
                            Factura f=(Factura)is.readObject();
                            List<Linea> r=service.searchLineas(f);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(r);
                        }catch (Exception ex){
                            ex.printStackTrace();
                            os.writeInt(Protocol.ERROR);
                        }
                        break;
                    case Protocol.IMPORTE_FACTURA:
                        try{
                            Factura f=(Factura)is.readObject();
                            double importe=service.importeFactura(f);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeDouble(importe);
                        }catch (Exception ex){
                            os.writeInt(Protocol.ERROR);
                        }
                        break;
                    case Protocol.RANGO_CATEGORIA:
                        try{
                            List<Object> objs=(List)is.readObject();
                            Rango r=service.rangoCategoria((Categoria)objs.getFirst(),(Date)objs.get(1),(Date)objs.getLast());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(r);
                        }catch (Exception ex){
                            os.writeInt(Protocol.ERROR);
                            ex.printStackTrace();
                        }
                        break;
                    case Protocol.VALIDATE:
                        try{
                            List<Linea> lineas=(List)is.readObject();
                            boolean b= service.validate(lineas);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeBoolean(b);
                        }catch (Exception ex){
                            os.writeInt(Protocol.ERROR);
                        }
                        break;
                    case Protocol.USERS:
                        try{
                            List<Usuario> usuarios=srv.users(this);

                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(usuarios);
                        }catch (Exception ex){
                            os.writeInt(Protocol.ERROR);
                            ex.printStackTrace();
                        }
                        break;
                    case Protocol.SEND_FACTURA:
                        try{
                            MensajeFactura mf=(MensajeFactura)is.readObject();
                            srv.sendFactura(mf);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        }catch (Exception e){
                            os.writeInt(Protocol.ERROR);
                        }
                        break;
                }
                os.flush();
            }catch(Exception ex){
                stop();
                ex.printStackTrace();
            }
        }
    }

    public synchronized void enviarNotificacion(Usuario u)throws Exception{
        if(as!=null) {
            try{
                aos.writeInt(Protocol.LOGIN);
                aos.writeObject(u);
                aos.flush();
            }catch(Exception ex){ex.printStackTrace();}
        }
    }

    public synchronized void enviarNotificacionDeslogeo(Usuario u)throws Exception{
        if(as!=null) {
            try {
                aos.writeInt(Protocol.LOGOUT);
                aos.writeObject(u);
                aos.flush();
            }catch (Exception ex){ ex.printStackTrace(); }
        }
    }

    public synchronized void sendFactura(MensajeFactura mf)throws Exception{
        if(as!=null) {
            try{
                aos.writeInt(Protocol.SEND_FACTURA);
                aos.writeObject(mf);
                aos.flush();
            }catch(Exception ex){ex.printStackTrace();}
        }
    }

    public String getSid() {
        return sid;
    }
    public String getNombre(){
        return nombre;
    }
}