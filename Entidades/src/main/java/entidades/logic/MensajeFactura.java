package entidades.logic;

import java.io.Serializable;

public class MensajeFactura implements Serializable {
    public Usuario usuario;
    public Factura factura;
    public Usuario destinario;
    public MensajeFactura(Usuario usuario, Factura factura, Usuario destinario) {
        this.usuario = usuario;
        this.factura = factura;
        this.destinario = destinario;
    }
    public MensajeFactura() {
        this.usuario = new Usuario();
        this.factura = new Factura();
        this.destinario = new Usuario();
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Factura getFactura() {
        return factura;
    }
    public void setFactura(Factura factura) {
        this.factura = factura;
    }
}
