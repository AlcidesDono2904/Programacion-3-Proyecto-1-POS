package pos.presentation.login;

import pos.Application;
import pos.logic.Service;

import javax.swing.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Controller {
    View view;
    Model model;
    public Controller(View v, Model m){
        this.view = v;
        this.model = m;
        this.view.setController(this);
    }

    public void login(String usuario, String clave)throws Exception{
        if(Service.instance().login(usuario,clave)){
            JOptionPane.showMessageDialog(null,"login exitoso");
            model.setLogeado(true);
            model.setId(usuario);
            Application.loginWindow.dispose();
        }else{
            JOptionPane.showMessageDialog(null,"credenciales incorrectos");
        }
    }
}
