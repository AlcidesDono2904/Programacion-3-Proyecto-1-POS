package pos.logic;

import entidades.logic.Protocol;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketListener {
    ThreadListener listener;
    String sid;
    Socket as;
    ObjectOutputStream aos;
    ObjectInputStream ais;
    public SocketListener(ThreadListener listener, String sid)throws Exception{
        this.listener = listener;
        as=new Socket(Protocol.SERVER,Protocol.PORT);
        this.sid=sid;
        aos=new ObjectOutputStream(as.getOutputStream());
        ais=new ObjectInputStream(as.getInputStream());
        aos.writeInt(Protocol.ASYNC);
        aos.writeObject(this);
        aos.flush();
    }

    boolean condition = true;
    private Thread t;
    public void start(){
        t=new Thread(new Runnable() {
            public void run() {listen();}
        });
        condition=true;
        t.start();
    }

    public void stop(){
        condition=false;
    }

    public void listen(){
        int method;
        while(condition){
            try{
                method=ais.readInt();
                switch(method){
                    case Protocol.LOGIN:

                }
            }catch(Exception e){
                e.printStackTrace();
                condition=false;
            }
        }
    }
}
