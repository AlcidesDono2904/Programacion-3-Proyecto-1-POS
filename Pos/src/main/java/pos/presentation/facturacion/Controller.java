package pos.presentation.facturacion;

import pos.Application;
import pos.logic.Producto;
import pos.logic.Cliente;
import pos.logic.Cajero;
import pos.logic.Service;

public class Controller {
    pos.presentation.facturacion.View view;
    pos.presentation.facturacion.Model model;

    public Controller(View facturacionView, Model facturacionModel) {
        facturacionModel.init(Service.instance().search(new Producto()),Service.instance().search(new Cliente()),Service.instance().search(new Cajero()));
        this.view = facturacionView;
        this.model = facturacionModel;
        view.setController(this);
        view.setModel(model);
    }
    public void agregarCliente(Cliente c) throws Exception {
        Service.instance().create(c);
        model.setClientes(Service.instance().search(new Cliente()));
        view.actualizarClientes(model.getClientes());
    }

    public void agregarCajero(Cajero c) throws Exception {
        Service.instance().create(c);
        model.setCajeros(Service.instance().search(new Cajero()));
        view.actualizarCajeros(model.getCajeros());
    }

    public void agregarProducto(Producto p) throws Exception{
        model.setMode(Application.MODE_CREATE);
        model.agregarLinea(Service.instance().read(p));
    }

    public void search(Producto filter) throws Exception {
        model.setProductos(Service.instance().search(filter));
    }
    public void show(){
        model.setProductos(Service.instance().search(new Producto()));
        model.setCajeros(Service.instance().search(new Cajero()));
        model.setClientes(Service.instance().search(new Cliente()));
    }
}
