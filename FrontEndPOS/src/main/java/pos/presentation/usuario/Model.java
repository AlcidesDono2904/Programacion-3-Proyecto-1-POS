package pos.presentation.usuario;

import entidades.logic.Factura;
import entidades.logic.Usuario;
import pos.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.List;

public class Model extends AbstractModel {
    List<Usuario> usuarios;
    Usuario current;
    int mode;

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(TABLA);
        firePropertyChange(MODE);
    }

    public Model() {
        mode = CREATE;
    }

    public void init(List<Usuario> usuarios) {
        mode = CREATE;
        this.usuarios = usuarios;
        this.current = new Usuario();
        firePropertyChange(TABLA);
        firePropertyChange(MODE);
    }

    public List<Usuario> getList() {
        return usuarios;
    }

    public void setList(List<Usuario> list) {
        this.usuarios = list;
    }

    public int getMode(){ return mode; }
    public void setMode(int mode){
        this.mode = mode;
        firePropertyChange(TABLA);
    }
    public Usuario getCurrent() {
        return current;
    }
    public void setCurrent(Usuario current) {
        this.current = current;
        firePropertyChange(MODE);
    }

    public void agregarUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
        firePropertyChange(TABLA);
    }
    public void removerUsuario(Usuario usuario) {
        this.usuarios.removeIf(i->i.getNombre().equals(usuario.getNombre()));
        this.current=new Usuario();
        mode=CREATE;
        firePropertyChange(TABLA);
        firePropertyChange(MODE);
    }

    public void agregarFactura(String name,Factura factura) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equals(name)) {
                usuario.getFacturas().add(factura);
                firePropertyChange(TABLA);
                break;
            }
        }
    }

    public Factura popFactura()throws Exception {
        if(current.getFacturas().isEmpty()) {throw new Exception("No hay facturas del usuario seleccionado");}
        Factura r=current.getFacturas().removeLast();
        firePropertyChange(TABLA);
        return r;
    }

    public static final String TABLA="tabla";
    public static final String MODE="mode";

    public static final int CREATE=0;
    public static final int EDIT=1;
}
