package pos.presentation.clientes;

import pos.logic.Cliente;
import pos.presentation.AbstractTableModel;

import java.util.List;

public class TableModel extends AbstractTableModel<Cliente> implements javax.swing.table.TableModel {

    public TableModel(int[] cols, List<Cliente> rows) {
        super(cols, rows);
    }

    public static final int ID=0;
    public static final int NOMBRE=1;
    public static final int TELEFONO=2;
    public static final int EMAIL=3;
    public static final int DESCUENTO=4;

    @Override
    protected Object getPropetyAt(Cliente e, int col) {
        switch (cols[col]){
            case ID: return e.getId();
            case NOMBRE: return e.getNombre();
            case TELEFONO: return e.getTelefono();
            case EMAIL: return e.getEmail();
            case DESCUENTO: return e.getDescuento();
            default: return "";
        }
    }

    @Override
    protected void initColNames(){
        colNames = new String[5];
        colNames[ID]= "Id";
        colNames[NOMBRE]= "Nombre";
        colNames[TELEFONO]= "Telefono";
        colNames[EMAIL]= "Email";
        colNames[DESCUENTO]= "Descuento";
    }

}
