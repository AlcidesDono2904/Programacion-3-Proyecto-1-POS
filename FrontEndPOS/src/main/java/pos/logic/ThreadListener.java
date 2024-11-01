package pos.logic;

import entidades.logic.MensajeFactura;
import entidades.logic.Usuario;

public interface ThreadListener {
    void agregarUsuario(Usuario usuario);
    void removerUsuario(Usuario usuario);
    void deliverFactura(MensajeFactura mf);
}
