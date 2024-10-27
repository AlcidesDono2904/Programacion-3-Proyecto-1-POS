package pos.data;

import entidades.logic.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public List<Linea> search(Factura e)throws Exception{//busca una lista de lineas segun una factura
        List<Linea> lista=new ArrayList<Linea>();
        String sql="select * from "+
                "Linea t "+
                "inner join Producto p on t.producto=p.codigo "+
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

    public Rango searchRangoCategoria(Categoria c, String fechaInicio, String fechaFin)throws Exception{
        String sql="SELECT SUM(((p.precioUnitario*l.cantidad)-(p.precioUnitario*l.cantidad*(l.descuento/100)))*(1-(cl.descuento/100))) AS importe, DATE_FORMAT(f.fecha, '%Y-%m') AS fecha "+
        "FROM factura f "+
        "INNER JOIN linea l ON f.codigo = l.factura "+
        "INNER JOIN Producto p ON l.producto = p.codigo "+
        "INNER JOIN Categoria c ON p.categoria = c.id "+
        "INNER JOIN Cliente cl ON f.cliente = cl.id "+
        "WHERE DATE_FORMAT(f.fecha, '%Y-%m') BETWEEN ? AND ? AND c.id = ? "+
        "GROUP BY DATE_FORMAT(f.fecha, '%Y-%m') "+
        "ORDER BY DATE_FORMAT(f.fecha, '%Y-%m') ASC";
        PreparedStatement stm=db.prepareStatement(sql);

        stm.setString(1,fechaInicio);
        stm.setString(2,fechaFin);
        stm.setString(3,c.getId());

        ResultSet rs=db.executeQuery(stm);

        Rango rango = new Rango();

        while (rs.next()){
            rango.getImportes().add(rs.getDouble("importe"));
            rango.getFechas().add(rs.getString("fecha"));
            /*String fecha=rs.getString("fecha")+"-01";
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            rango.getFechas().add(formato.parse(fecha));*/
        }

        return rango;
    }



    public Linea from(ResultSet rs, String alias)throws Exception {
        Linea l = new Linea();
        l.setCodigo(rs.getString(alias+".codigo"));
        l.setCantidad(rs.getInt(alias+".cantidad"));
        l.setDescuento(rs.getDouble(alias+".descuento"));
        return l;
    }
}
