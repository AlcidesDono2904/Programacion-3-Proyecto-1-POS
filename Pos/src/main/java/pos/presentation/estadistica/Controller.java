package pos.presentation.estadistica;

import pos.logic.Categoria;
import pos.logic.Service;
import pos.logic.Rango;

import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model){
        model.init(new ArrayList<Rango>(),
                new ArrayList<LocalDate>(),
                Service.instance().buscarRangoFechas(),
                Service.instance().search(new Categoria())
        );
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void agregarTodo(){
        model.datos(Service.instance().buscarRango(),Service.instance().buscarRangoFechas());
    }
}
