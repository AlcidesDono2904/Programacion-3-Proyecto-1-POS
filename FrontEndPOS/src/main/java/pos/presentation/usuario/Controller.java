package pos.presentation.usuario;

import entidades.logic.Factura;
import entidades.logic.MensajeFactura;
import entidades.logic.Usuario;
import pos.Application;
import pos.logic.Service;
import pos.logic.SocketListener;
import pos.logic.ThreadListener;

import java.util.ArrayList;
import java.util.List;

public class Controller implements ThreadListener {
    View view;
    Model model;

    SocketListener socketListener;

    public Controller(View v,Model m){
        m.init(Service.instance().requestUsers());
        this.view = v;
        this.model = m;
        view.setController(this);
        view.setModel(model);

        try{
            socketListener=new SocketListener(this,Service.instance().getSid());
            socketListener.start();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public SocketListener getSocketListener(){ return socketListener; }

    public void agregarUsuario(Usuario u){
        model.agregarUsuario(u);
    }

    public void removerUsuario(Usuario u){
        model.removerUsuario(u);
    }

    public void current(int n){
        Usuario u=model.getList().get(n);
        model.setCurrent(u);
        model.setMode(Model.EDIT);
    }

    public void popFactura()throws Exception{
        Factura f =model.popFactura();
        Application.facturacionController.getModel().setFactura(f);
    }

    public void enviar(){
        try{
            Service.instance().sendFactura(
                    new MensajeFactura(model.getCurrent(),
                        Application.facturacionController.getView().take(),
                        new Usuario(Service.instance().getSid(), Application.loginController.getModel().getId()))
                    );
            Application.facturacionController.clear();
            System.out.println("Facturada enviada");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deliverFactura(MensajeFactura mf) {
        model.agregarFactura(mf.destinario.getNombre(),mf.factura);
    }
}
