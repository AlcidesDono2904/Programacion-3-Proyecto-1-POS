package pos.logic;

import entidades.logic.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Worker {//TODO
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

    Worker(Server srv, Socket s, IService serv){
        this.srv = srv;
        this.socket = s;
        this.service = serv;
        this.continuar = true;
        try{
            this.is=new ObjectInputStream(s.getInputStream());
            this.os=new ObjectOutputStream(s.getOutputStream());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setAs(Socket as,ObjectOutputStream os, ObjectInputStream is){
        this.as = as;
        this.os = os;
        this.is = is;
    }

    public void notifyFactura(){

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
                            }else{
                                os.writeInt(Protocol.ERROR);
                            }
                        }catch(Exception ex){
                            os.writeInt(Protocol.ERROR);
                            ex.printStackTrace();
                        }
                        break;
                    case Protocol.LOGOUT:
                        os.close();
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
                }
                os.flush();
            }catch(Exception ex){
                stop();
                ex.printStackTrace();
            }
        }
    }
}
