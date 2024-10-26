package pos.presentation.login;

import pos.logic.Service;

import javax.swing.*;
import java.util.List;

public class Controller {
    View view;

    public Controller(View v){
        this.view = v;
        this.view.setController(this);
    }

    public void login(String usuario, String clave)throws Exception{
        if(Service.instance().login(usuario,clave)){
            JOptionPane.showMessageDialog(null,"login exitoso");
        }else{
            JOptionPane.showMessageDialog(null,"credenciales incorrectos");
        }
    }
}
