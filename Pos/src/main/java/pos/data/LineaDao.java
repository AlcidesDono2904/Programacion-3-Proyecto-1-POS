package pos.data;

import pos.logic.Factura;
import pos.logic.Linea;
import pos.logic.Producto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LineaDao {
    private Database db;
    public LineaDao() {db=Database.instance();}

    //CRUDS
    public void create(Linea l, int codigo)throws Exception{//crea una linea, recibe la linea y el codigo de la factura correspondiente
        String sql="insert into "+
                "Factura f "+
                "producto,cantidad,descuento,factura "+
                "values(?,?,?,?)";
        PreparedStatement stm=db.prepareStatement(sql);
        stm.setString(1,l.getProducto().getCodigo());
        stm.setInt(2,l.getCantidad());
        stm.setDouble(3,l.getDescuento());
        stm.setInt(4,codigo);
        db.executeUpdate(stm);
    }

    public Linea read(String codigo)throws Exception{
        String sql="select * from "+
                "Linea t "+
                "inner join Producto p on t.producto=p.codigo"+
                "where t.codigo=?";
        PreparedStatement stm=db.prepareStatement(sql);
        stm.setInt(1,Integer.parseInt(codigo));
        ResultSet rs=stm.executeQuery();
        ProductoDao pdao=new ProductoDao();
        if(rs.next()){
            Linea l=from(rs,"t");
            l.setProducto(pdao.from(rs,"p"));
            return l;
        }else {
            throw new Exception("No se encontro el linea con el codigo: "+codigo);
        }
    }

    public List<Linea> search(Factura e)throws Exception{//busca una lista de lineas seguna una factura
        List<Linea> lista=new ArrayList<Linea>();
        String sql="select * from "+
                "Linea t "+
                "inner join Producto p on t.producto=p.codigo"+
                "where t.factura=?";
        PreparedStatement stm=db.prepareStatement(sql);
        stm.setInt(1,Integer.parseInt(e.getCodigo()));
        ResultSet rs=stm.executeQuery();
        ProductoDao pdao=new ProductoDao();
        while(rs.next()){
            Linea l=from(rs,"t");
            l.setProducto(pdao.from(rs,"p"));
            lista.add(l);
        }
        return lista;
    }


    public Linea from(ResultSet rs, String alias)throws Exception {
        Linea l = new Linea();
        l.setCodigo(rs.getString(alias+".codigo"));
        l.setCantidad(rs.getInt(alias+".cantidad"));
        l.setDescuento(rs.getDouble(alias+".descuento"));
        return l;
    }
}
