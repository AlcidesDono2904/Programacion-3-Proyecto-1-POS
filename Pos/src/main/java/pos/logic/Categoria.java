package pos.logic;

public class Categoria {
    private String codigo;
    private String nombreCategoria;

    public Categoria(String codigo, String nombreCategoria) {
        this.codigo = codigo;
        this.nombreCategoria = nombreCategoria;

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
}
