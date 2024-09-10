package pos.presentation.cajeros;

import pos.Application;
import pos.logic.Cajero;
import pos.logic.Service;

public class Controller {
    View view;
    public Model model;

    public Controller(View view, Model model) {
        try {
            model.init(Service.instance().search(new Cajero()));
        } catch (Exception e) {
            e.printStackTrace();
            // Manejo del error o logging
        }
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void search(Cajero filter) throws  Exception{
        model.setFilter(filter);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Cajero());
        model.setList(Service.instance().search(model.getFilter()));
    }

    public void save(Cajero e) throws  Exception{
        switch (model.getMode()) {
            case Application.MODE_CREATE:
                Service.instance().create(e);
                break;
            case Application.MODE_EDIT:
                Service.instance().update(e);
                break;
        }
        model.setFilter(new Cajero());
        search(model.getFilter());
    }

    public void edit(int row){
        Cajero e = model.getList().get(row);
        try {
            model.setMode(Application.MODE_EDIT);
            model.setCurrent(Service.instance().read(e));
        } catch (Exception ex) {}
    }

    public void delete() throws Exception {
        Service.instance().delete(model.getCurrent());
        search(model.getFilter());
    }

    public void clear() {
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Cajero());
    }

}
