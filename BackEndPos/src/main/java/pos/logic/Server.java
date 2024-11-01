package pos.logic;

import entidades.logic.MensajeFactura;
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
        System.out.println("Worker removido, canidad workers: "+workers.size());
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
                        oos.flush();
                        break;
                    case Protocol.ASYNC:
                        sid=(String) iss.readObject();
                        System.out.println("ASYNC: "+sid);
                        join(s,oos,iss,sid);
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void join(Socket s,ObjectOutputStream oos,ObjectInputStream iss,String sid){
        for (Worker w : workers) {
            if (w.getSid().equals(sid)) {
                w.setAs(s, oos, iss);
                return;
            }
        }
        System.out.println("Worker no encontrado para el SID: " + sid);
    }

    public List<Usuario> users(Worker from){
        List<Usuario> lista = new ArrayList<>();
        synchronized (workers) {
            for (Worker i : workers) {
                if (!from.getSid().equals(i.getSid())) {
                    lista.add(new Usuario(i.getSid(), i.getNombre()));
                }
            }
        }
        return lista;
    }

    public void notificarLogeo(Worker w,Usuario u){
        synchronized (workers) {
            for (Worker i : workers) {
                if (i != w) {
                    try {
                        i.enviarNotificacion(u);
                    } catch (Exception e) {
                        System.out.println("Error al notificar logeo: " + e.getMessage());
                    }
                }
            }
        }
    }

    public void notificarDeslogeo(Worker w,Usuario u){
        synchronized (workers) {
            for (Worker i : workers) {
                if(i != w) {
                    try{
                        i.enviarNotificacionDeslogeo(u);
                    }catch (Exception e){
                        System.out.println("Error al notificar deslogeo: " + e.getMessage());
                    }
                }
            }
        }
    }

    public void sendFactura(MensajeFactura mf){
        synchronized (workers) {
            for (Worker i : workers) {
                if(i.sid.equals(mf.usuario.getSid())) {
                    try{
                        i.sendFactura(mf);
                        break;
                    }catch (Exception e){
                        System.out.println("Error al enviar factura: " + e.getMessage());
                        break;
                    }
                }
            }
        }
    }
}
