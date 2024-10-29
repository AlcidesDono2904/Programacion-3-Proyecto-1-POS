package pos.presentation.login;

public class Model {
    boolean logeado;
    public Model (){
        logeado = false;
    }

    public boolean isLogeado() {
        return logeado;
    }

    public void setLogeado(boolean logeado) {
        this.logeado = logeado;
    }
}
