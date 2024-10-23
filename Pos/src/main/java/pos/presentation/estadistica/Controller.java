package pos.presentation.estadistica;

import pos.logic.Categoria;
import pos.logic.Service;
import pos.logic.Rango;

import java.util.Date;
import java.util.List;

public class Controller {
    View view;
    Model model;

    public Controller(View view,Model model) {
        this.model = model;
        this.model.init(Service.instance().search(new Categoria()));
        this.view = view;

        this.view.setController(this);
        this.view.setModel(model);
    }

    public void cargarCategorias(int i){
        Categoria c = model.getCategorias().get(i);
    }

    public void selectCategory(int i){
        model.setMode(Model.DELETE);
        model.setActual(model.getRangos().get(i));
    }

    public void addCategory(Categoria c,Date in,Date fin)throws Exception{
        Rango r=Service.instance().rangoCategoria(c,in,fin);
        model.removeCategory(c);
        model.agregarRango(r);
    }

    public void addAllCategories(Date in,Date fin)throws Exception{
        model.clear();
        List<Categoria> categories=model.getCategorias();
        for(Categoria c:categories){
            model.agregarRango(Service.instance().rangoCategoria(c,in,fin));
        }
    }

    public void removeCategory(){
        if(model.getMode() == Model.DELETE){
            model.removeCategory(model.getActual().getCategoria());
            model.setMode(Model.CREATE);
        }
    }

    public void clear(){
        model.setMode(Model.CREATE);
        model.clear();
    }

}
