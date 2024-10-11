package pos.data;

import pos.logic.Factura;
import pos.logic.Linea;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

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
}
