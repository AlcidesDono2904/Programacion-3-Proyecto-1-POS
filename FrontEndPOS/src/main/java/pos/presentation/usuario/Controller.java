package pos.presentation.usuario;

import entidades.logic.Factura;
import entidades.logic.Usuario;
import pos.logic.Service;
import pos.logic.ThreadListener;

import java.util.ArrayList;
import java.util.List;

public class Controller implements ThreadListener {
    View view;
    Model model;

    public Controller(View v,Model m){
        m.init(Service.instance().requestUsers());
        this.view = v;
        this.model = m;
        view.setController(this);
        view.setModel(model);
    }
}
