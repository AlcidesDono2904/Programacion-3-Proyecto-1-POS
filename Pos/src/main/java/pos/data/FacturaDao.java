package pos.data;

import entidades.logic.Factura;
import entidades.logic.Linea;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FacturaDao {
    Database db;
    public FacturaDao() {db=Database.instance();}

    //CRUD S
   public void Create(Factura f)throws Exception {
        String sql="insert into "+
                "Factura "+
                "(fecha, cliente, cajero) "+
                "values(?,?,?)";
        PreparedStatement stm=db.prepareStatement(sql);
        stm.setDate(1, Date.valueOf(LocalDate.now()));
        stm.setString(2,f.getCliente().getId());
        stm.setString(3,f.getCajero().getId());
        int idfactura=db.executeUpdateWithKeys(stm);
        //agregar lineas de la factura
        LineaDao ldao=new LineaDao();
        for(Linea l:f.getLineas()){
            ldao.create(l,idfactura);
        }
   }

   public Factura read(int idfactura)throws Exception {
        String sql="select * "+
                "from Factura f " +
                "inner join Cliente cl on f.cliente=cl.id " +
                "inner join Cajero ca on f.cajero=ca.id " +
                "where f.codigo=?";
        PreparedStatement stm=db.prepareStatement(sql);
        stm.setInt(1, idfactura);
        ResultSet rs=db.executeQuery(stm);
        ClienteDao clienteDao=new ClienteDao();
        CajeroDao cajeroDao=new CajeroDao();
        if(rs.next()){
            Factura f=from(rs,"f");
            f.setCliente(clienteDao.from(rs,"cl"));
            f.setCajero(cajeroDao.from(rs,"ca"));
            return f;
        }else{
            throw new Exception("No se encontro la factura con codigo: "+idfactura);
        }
   }

   public List<Factura> search(Factura f)throws Exception {
        List<Factura> facturas=new ArrayList<Factura>();
       String sql="select * "+
               "from Factura f " +
               "inner join Cliente cl on f.cliente=cl.id " +
               "inner join Cajero ca on f.cajero=ca.id " +
               "where cl.nombre like ?";
       PreparedStatement stm=db.prepareStatement(sql);
       stm.setString(1,"%"+f.getCliente().getNombre()+"%");
       ResultSet rs=db.executeQuery(stm);
       ClienteDao clienteDao=new ClienteDao();
       CajeroDao cajeroDao=new CajeroDao();
       while(rs.next()){
           Factura result=from(rs,"f");
           result.setCliente(clienteDao.from(rs,"cl"));
           result.setCajero(cajeroDao.from(rs,"ca"));
           facturas.add(result);
       }
       return facturas;
   }

   public double importeFactura(Factura f)throws Exception {
        //selecciona de listas, las que coincidan con el codigo de la factura, toma los productos, toma el precio unitario, lo multiplica por la canitdad de lineas, y hace el descuento de lineas y al final hace el descuento del cliente
        String sql="select " +
                "sum(((p.precioUnitario*l.cantidad)-(p.precioUnitario*l.cantidad*(l.descuento/100)))*(1-(c.descuento/100)) as importe "+
                "from Linea l " +
                "inner join Factura f on f.codigo=l.factura " +
                "inner join Producto p on p.codigo=l.producto " +
                "inner join Cliente c on f.cliente=c.id " +
                "where f.codigo=?";
        PreparedStatement stm=db.prepareStatement(sql);
        stm.setInt(1,Integer.parseInt(f.getCodigo()));
        ResultSet rs=db.executeQuery(stm);
        return rs.getDouble(1);
   }

   public Factura from(ResultSet rs,String alias)throws Exception{
        Factura f=new Factura();
        f.setCodigo(rs.getString(alias+".codigo"));
        f.setFecha(rs.getDate(alias+".fecha").toLocalDate());
        return f;
   }
}
