package pos.presentation.productos;
import pos.Application;

import pos.logic.Producto;
import pos.logic.Service;
import java.util.List;
public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) {
        model.init(Service.instance().search(new Producto()));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    //Metodo de búsqueda con filtro de código o descripción
    public void search(Producto filter) throws Exception {
        model.setFilter(filter);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Producto());
        model.setList(Service.instance().search(model.getFilter()));
    }

    // Metodo para guardar un producto (crear o actualizar)
    public void save(Producto e) throws Exception {
        switch (model.getMode()) {
            case Application.MODE_CREATE:
                Service.instance().create(e);
                break;
            case Application.MODE_EDIT:
                Service.instance().update(e);
                break;
        }
        model.setFilter(new Producto());
        search(model.getFilter());
    }

    // Metodo para editar un producto basado en la fila seleccionada
    public void edit(int row) {
        Producto e = model.getList().get(row);
        try {
            model.setMode(Application.MODE_EDIT);
            model.setCurrent(Service.instance().read(e));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Metodo para eliminar un producto
    public void delete() throws Exception {
        Service.instance().delete(model.getCurrent());
        search(model.getFilter());
    }

    // Metodo para limpiar la selección y preparar la creación de un nuevo producto
    public void clear() {
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Producto());
    }
}
