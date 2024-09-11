package pos.presentation.facturacion;

import pos.Application;
import pos.logic.Producto;
import pos.logic.Service;

public class Controller {
    pos.presentation.facturacion.View view;
    pos.presentation.facturacion.Model model;

    public Controller(View facturacionView, Model facturacionModel) {
        this.view = facturacionView;
        this.model = facturacionModel;
        view.setController(this);
        view.setModel(model);
    }

    public void agregarProducto(Producto p) throws Exception{
        model.setMode(Application.MODE_CREATE);
        model.agregarLinea(Service.instance().read(p));
    }

    public void search(Producto filter) throws Exception {
        model.setProductos(Service.instance().search(filter));
    }
}
