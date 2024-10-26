package pos.logic;

import entidades.logic.Protocol;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {//TODO
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

    public void run(){
        Service service = new Service();
        boolean continuar= !false;
        Socket s;
        Worker w;
        while(continuar){
            try{
                s=srv.accept();
                System.out.println("conexion establecida...");
                w=new Worker(this,s,service);
                workers.add(w);
                System.out.println("Workers= "+workers.size());
                w.start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
