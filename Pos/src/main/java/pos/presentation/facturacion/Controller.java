package pos.presentation.facturacion;

import pos.Application;
import pos.logic.*;

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

    public void edit(int row){
        Linea l = model.getLineas().get(row);
        try {
            model.setMode(Application.MODE_EDIT);
            model.setCurrent(l);
        } catch (Exception ex) {}
    }

    public void save(int cant)throws Exception{
        Linea l=model.getCurrent();

        int total=0;
        for(Linea i : model.getLineas()){
            if(i.getProducto()==l.getProducto()){
                total+=i.getCantidad();
                if(total<l.getCantidad()) {
                    throw new Exception("La cantidad de productos a facturar no puede ser mayor a las existencias");
                }
            }
        }

        l.setCantidad(cant);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(null);
    }

    public void save(double desc)throws Exception{
        if(desc>100.0 || desc<0.0){
            throw new Exception();
        }

        Linea l=model.getCurrent();

        l.setDescuento(desc);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(null);
    }

    public void quitar(){
        model.getLineas().remove(model.getCurrent());
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(null);
    }

    public void cobrar(Factura f){
        Service.instance().create(f);

    }
}
