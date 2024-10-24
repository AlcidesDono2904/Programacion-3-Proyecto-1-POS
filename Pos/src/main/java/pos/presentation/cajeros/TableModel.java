package pos.presentation.cajeros;

import entidades.logic.Cajero;
import pos.presentation.AbstractTableModel;

import java.util.List;

public class TableModel extends AbstractTableModel<Cajero> implements javax.swing.table.TableModel{

    public TableModel(int[] cols, List<Cajero> rows) {
        super(cols, rows);
    }

    public static final int ID=0;
    public static final int NOMBRE=1;


    @Override
    protected Object getPropetyAt(Cajero e, int col) {
        switch (cols[col]){
            case ID: return e.getId();
            case NOMBRE: return e.getNombre();
            default: return "";
        }
    }

    @Override
    protected void initColNames(){
        colNames = new String[2];
        colNames[ID]= "Id";
        colNames[NOMBRE]= "Nombre";
    }
}
