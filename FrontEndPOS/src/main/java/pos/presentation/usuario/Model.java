package pos.presentation.usuario;

import entidades.logic.Usuario;
import pos.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.List;

public class Model extends AbstractModel {
    List<Usuario> usuarios;

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(TABLA);
    }

    public Model() {}

    public void init(List<Usuario> usuarios) {
        this.usuarios = usuarios;
        firePropertyChange(TABLA);
    }

    public List<Usuario> getList() {
        return usuarios;
    }

    public void setList(List<Usuario> list) {
        this.usuarios = list;
    }

    public void agregarUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
        firePropertyChange(TABLA);
    }

    public static final String TABLA="tabla";
}
