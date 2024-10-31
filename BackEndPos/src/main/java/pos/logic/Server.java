package pos.logic;

import entidades.logic.Protocol;
import entidades.logic.Usuario;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
    ServerSocket srv;
    List<Worker> workers;

    public Server(){
        try{
            srv=new ServerSocket(Protocol.PORT);
            workers = Collections.synchronizedList(new ArrayList<Worker>());
            System.out.println("Servidor iniciado");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void remove(Worker w){
        workers.remove(w);
    }

    public void run(){
        Service service = new Service();
        boolean continuar= !false;
        Socket s;
        Worker w;
        String sid;
        while(continuar){
            try{
                s=srv.accept();
                System.out.println("conexion establecida...");
                ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream iss=new ObjectInputStream(s.getInputStream());
                int type=iss.readInt();
                switch(type){
                    case Protocol.SYNC:
                        sid=s.getRemoteSocketAddress().toString();
                        System.out.println("SYNC: "+sid);
                        w=new Worker(this,s,oos,iss,service,sid);
                        workers.add(w);
                        System.out.println("Workers= "+workers.size());
                        w.start();
                        oos.writeObject(sid);
                        break;
                    case Protocol.ASYNC:
                        sid=(String) iss.readObject();
                        System.out.println("ASYNC: "+sid);
                        join(s,oos,iss,sid);
                        break;
                }
                oos.flush();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void join(Socket s,ObjectOutputStream oos,ObjectInputStream iss,String sid){
        for(Worker w:workers){
            if(w.sid.equals(sid)){
                w.setAs(s,oos,iss);
            }
        }
    }

    public List<Usuario> users(Worker from){
        List<Usuario> lista=new ArrayList<>();
        for(Worker i:workers){
            if(!from.sid.equals(i.sid)){
                lista.add(new Usuario(i.sid,i.nombre));
            }
        }
        return lista;
    }

    public void notificarLogeo(Worker w,Usuario u){
        for(Worker i:workers){
            if(!i.sid.equals(w.sid)){
                try {
                    i.enviarNotificacion(u,Protocol.LOGIN);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
