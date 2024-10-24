package pos.presentation.historico;

import entidades.logic.Factura;
import pos.presentation.AbstractTableModel;

import java.util.List;

public class TableModelListado extends AbstractTableModel<Factura> implements javax.swing.table.TableModel{
    public TableModelListado(int[] cols, List<Factura> rows) {
        super(cols, rows);
    }



    public static final int CODIGO=0;
    public static final int CLIENTE=1;
    public static final int CASHIER=2;
    public static final int DATE=3;
    public static final int IMPORT=4;

    @Override
    protected Object getPropetyAt(Factura e, int col) {
        switch (cols[col]){
            case CODIGO: return e.getCodigo();
            case CLIENTE: return e.getCliente().getNombre();
            case CASHIER: return e.getCajero().getNombre();
            case DATE: return e.getFecha();
            case IMPORT: return e.importe();
            default: return "";
        }
    }

    @Override
    protected void initColNames(){
        colNames = new String[5];
        colNames[CODIGO]= "Numero";
        colNames[CLIENTE]= "Cliente";
        colNames[CASHIER]= "Cajero";
        colNames[DATE]= "Fecha";
        colNames[IMPORT]= "Importe";
    }

}
