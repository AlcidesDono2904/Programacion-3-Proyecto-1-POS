package pos.presentation.login;

public class Model {
    boolean logeado;
    String id;
    public Model (){
        logeado = false;
    }

    public boolean isLogeado() {
        return logeado;
    }

    public void setLogeado(boolean logeado) {
        this.logeado = logeado;
    }
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
}
