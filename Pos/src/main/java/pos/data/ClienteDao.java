package pos.data;

import pos.logic.Cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {
    private Database db;

    public ClienteDao() {db=Database.instance();}
    //CRUD
    public void create(Cliente c)throws Exception {
        String sql= "insert into "+
                "Cliente "+
                "(id,nombre,telefono,email,descuento)"+
                "values(?,?,?,?,?)";
        PreparedStatement stm= db.prepareStatement(sql);
        stm.setString(1, c.getId());
        stm.setString(2, c.getNombre());
        stm.setString(3, c.getTelefono());
        stm.setString(4, c.getEmail());
        stm.setDouble(5, c.getDescuento());
        db.executeUpdate(stm);
    }

    public Cliente read(String id)throws Exception {
        String sql= "select * from Cliente t where id=?";
        PreparedStatement stm= db.prepareStatement(sql);
        stm.setString(1, id);
        ResultSet rs=db.executeQuery(stm);
        if(rs.next()){
            Cliente c=from(rs,"t");
            return c;
        }else{
            throw new Exception("Cliente no existe");
        }
    }

    public void update(Cliente c)throws Exception {
        String sql="update "+
                "Cliente "+
                "set nombre=?,telefono=?,email=?,descuento=?"+
                "where id=?";
        PreparedStatement stm= db.prepareStatement(sql);
        stm.setString(1, c.getNombre());
        stm.setString(2, c.getTelefono());
        stm.setString(3, c.getEmail());
        stm.setDouble(4, c.getDescuento());
        stm.setString(5, c.getId());
        int count=db.executeUpdate(stm);
        if(count==0){
            throw new Exception("Cliente no existe");
        }
    }

    public void delete(Cliente c)throws Exception {
        String sql="delete"+
                "from Cliente"+
                "where id=?";
        PreparedStatement stm= db.prepareStatement(sql);
        stm.setString(1, c.getId());
        int count=db.executeUpdate(stm);
        if(count==0){
            throw new Exception("Cliente no existe");
        }
    }

    public List<Cliente> search(Cliente c) throws Exception {
        List<Cliente> lista=new ArrayList<Cliente>();
        String sql="select * "+
                "from "+
                "Cliente t "+
                "where t.nombre like ?";
        PreparedStatement stm= db.prepareStatement(sql);
        stm.setString(1, "%"+c.getNombre()+"%");
        ResultSet rs=db.executeQuery(stm);
        while(rs.next()){
            Cliente r=from(rs,"t");
            lista.add(r);
        }
        return lista;
    }

    public Cliente from(ResultSet rs,String alias) throws Exception{
        Cliente c=new Cliente();
        c.setId(rs.getString(alias+".id"));
        c.setNombre(rs.getString(alias+".nombre"));
        c.setTelefono(rs.getString(alias+".telefono"));
        c.setEmail(rs.getString(alias+".email"));
        c.setDescuento(rs.getFloat(alias+".descuento"));
        return c;
    }
}
