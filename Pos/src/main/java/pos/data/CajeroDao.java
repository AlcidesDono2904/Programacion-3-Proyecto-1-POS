package pos.data;

import pos.logic.Cajero;
import pos.logic.Cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CajeroDao {
    private Database db;

    public CajeroDao() {
        db=Database.instance();
    }
    //CRUD S

    public void create(Cajero c)throws Exception {
        String sql="insert into Cajero (id,nombre)values(?,?)";
        PreparedStatement stm=db.prepareStatement(sql);
        stm.setString(1, c.getId());
        stm.setString(2, c.getNombre());
        db.executeUpdate(stm);
    }

    public Cajero read(String id)throws Exception {
        String sql= "select * from Cajero t where id=?";
        PreparedStatement stm= db.prepareStatement(sql);
        stm.setString(1, id);
        ResultSet rs=db.executeQuery(stm);
        if(rs.next()){
            Cajero c=from(rs,"t");
            return c;
        }else{
            throw new Exception("Cliente no existe");
        }
    }

    public void update(Cajero c)throws Exception {
        String sql="update "+
                "Cajero "+
                "set nombre=? "+
                "where id=?";
        PreparedStatement stm= db.prepareStatement(sql);
        stm.setString(1, c.getNombre());
        stm.setString(2, c.getId());
        int count=db.executeUpdate(stm);
        if(count==0){
            throw new Exception("Cajero no existe");
        }
    }

    public void delete(Cajero c)throws Exception {
        String sql="delete"+
                "from Cajero"+
                "where id=?";
        PreparedStatement stm= db.prepareStatement(sql);
        stm.setString(1, c.getId());
        int count=db.executeUpdate(stm);
        if(count==0){
            throw new Exception("Cajero no existe");
        }
    }

    public List<Cajero> search(Cajero c) throws Exception {
        List<Cajero> lista=new ArrayList<Cajero>();
        String sql="select * "+
                "from "+
                "Cajero t "+
                "where t.nombre like ?";
        PreparedStatement stm= db.prepareStatement(sql);
        stm.setString(1, "%"+c.getNombre()+"%");
        ResultSet rs=db.executeQuery(stm);
        while(rs.next()){
            Cajero r=from(rs,"t");
            lista.add(r);
        }
        return lista;
    }

    public Cajero from(ResultSet rs,String alias) throws Exception{
        Cajero c=new Cajero();
        c.setId(rs.getString(alias+".id"));
        c.setNombre(rs.getString(alias+".nombre"));
        return c;
    }

}
