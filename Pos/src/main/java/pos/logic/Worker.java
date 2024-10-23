package pos.logic;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;


public class Worker {//TODO
    ObjectInputStream is;
    ObjectOutputStream os;
    private boolean continuar;

    Worker(Server srv){

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
            os.close();
            is.close();
        }catch (Exception ex){ex.printStackTrace();}
    }
    public void listen(){

    }
}
