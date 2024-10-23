package pos.data;

import pos.logic.Categoria;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDao {
    Database db;

    public CategoriaDao() {
        db = Database.instance();
    }

    public Categoria read(String codigo)throws Exception{
        String sql = "SELECT * FROM categoria c WHERE id = ?";
        PreparedStatement stm=db.prepareStatement(sql);
        stm.setString(1, codigo);
        ResultSet rs=stm.executeQuery();
        if(rs.next()){
            return from(rs,"c");
        }else{
            throw new Exception("No se encontro el categoria");
        }
    }

    public List<Categoria> search(Categoria e) throws Exception {
        List<Categoria> resultado = new ArrayList<Categoria>();
        String sql = "select * " +
                "from " +
                "Categoria t " +
                "where t.nombre like ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, "%" + e.getNombre() + "%");
        ResultSet rs = db.executeQuery(stm);
        while (rs.next()) {
            Categoria r= from(rs, "t");
            resultado.add(r);
        }
        return resultado;
    }

    public Categoria from(ResultSet rs, String alias) throws Exception {
        Categoria e = new Categoria();
        e.setId(rs.getString(alias + ".id"));
        e.setNombre(rs.getString(alias + ".nombre"));
        return e;
    }

}
