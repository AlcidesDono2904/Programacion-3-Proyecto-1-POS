package pos.logic;

import entidades.logic.MensajeFactura;
import entidades.logic.Protocol;
import entidades.logic.Usuario;

import javax.swing.*;
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
        this.sid=sid;

        as=new Socket(Protocol.SERVER,Protocol.PORT);
        aos=new ObjectOutputStream(as.getOutputStream());
        ais=new ObjectInputStream(as.getInputStream());
        aos.writeInt(Protocol.ASYNC);
        aos.writeObject(sid);
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
                System.out.println("SocketListener: "+method);
                switch(method){
                    case Protocol.LOGIN:
                        try{
                            Usuario u=(Usuario)ais.readObject();
                            deliverLogin(u);
                        }catch (Exception e){e.printStackTrace();}
                        break;
                    case Protocol.LOGOUT:
                        try{
                            Usuario u=(Usuario)ais.readObject();
                            deliverLogout(u);
                        }catch (Exception e){e.printStackTrace();}
                        break;
                    case Protocol.SEND_FACTURA:
                        try{
                            MensajeFactura mf=(MensajeFactura)ais.readObject();
                            deliverFactura(mf);
                        }catch(Exception e){e.printStackTrace();}
                        break;
                }
            }catch(Exception e){
                e.printStackTrace();
                condition=false;
            }
        }
        try{
            as.shutdownOutput();
            as.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deliverLogin(final Usuario u){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                listener.agregarUsuario(u);
            }
        });
    }
    public void deliverLogout(final Usuario u){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                listener.removerUsuario(u);
            }
        });
    }
    public void deliverFactura(final MensajeFactura mf){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                listener.deliverFactura(mf);
            }
        });
    }
}
