package pos.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDao {
    private Database db;
    public UsuarioDao() {
        db = Database.instance();
    }

    public boolean validarUsuario(String usuario, String password)throws Exception {
        String sql = "select * " +
                "from usuario " +
                "where codigo = ? and clave = ?";

        PreparedStatement stm=db.prepareStatement(sql);
        stm.setString(1, usuario);
        stm.setString(2, password);

        ResultSet rs =db.executeQuery(stm);

        return rs.next();
    }
}
