package pos.presentation.productos;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) {
        model.init(Service.instance().search(new Productos()));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    // Método de búsqueda con filtro de código o descripción
    public void search(Productos filter) throws Exception {
        model.setFilter(filter);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Productos());
        model.setList(Service.instance().search(
                filter.getCodigo() != null ? filter.getCodigo() : "",
                filter.getDescripcion() != null ? filter.getDescripcion() : ""
        ));
    }

    // Método para guardar un producto (crear o actualizar)
    public void save(Productos e) throws Exception {
        switch (model.getMode()) {
            case Application.MODE_CREATE:
                Service.instance().create(e);
                break;
            case Application.MODE_EDIT:
                Service.instance().update(e);
                break;
        }
        model.setFilter(new Productos());
        search(model.getFilter());
    }

    // Método para editar un producto basado en la fila seleccionada
    public void edit(int row) {
        Productos e = model.getList().get(row);
        try {
            model.setMode(Application.MODE_EDIT);
            model.setCurrent(Service.instance().read(e));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Método para eliminar un producto
    public void delete() throws Exception {
        Service.instance().delete(model.getCurrent());
        search(model.getFilter());
    }

    // Método para limpiar la selección y preparar la creación de un nuevo producto
    public void clear() {
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Productos());
    }
}
