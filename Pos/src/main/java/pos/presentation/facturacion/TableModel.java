package pos.presentation.facturacion;

import pos.logic.Linea;
import pos.presentation.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel<Linea> implements javax.swing.table.TableModel {

    public TableModel(int[] cols, List<Linea> rows) {
        super(cols, rows);
    }

    public static final int CODIGO=0;
    public static final int ARTICULO=1;
    public static final int CATEGORIA=2;
    public static final int PRECIO=3;
    public static final int DESCUENTO=4;
    public static final int NETO = 5;
    public static final int IMPORTE=6;

    @Override
    protected Object getPropetyAt(Linea e, int col) {
        switch (cols[col]){
            case CODIGO: return e.getCodigo();
            case ARTICULO: return e.getProducto().getDescripcion();
            case CATEGORIA: return e.getProducto().getCategoria();
            case PRECIO: return e.getProducto().getPrecioUnitario();
            case DESCUENTO: return e.getDescuento();
            case NETO: return e.neto();
            case IMPORTE: return e.importe();
            default: return "";
        }
    }

    @Override
    protected void initColNames(){
        colNames = new String[7];
        colNames[CODIGO]= "Codigo";
        colNames[ARTICULO]= "Articulo";
        colNames[CATEGORIA]= "Categoria";
        colNames[PRECIO]= "Precio";
        colNames[DESCUENTO]= "Descuento";
        colNames[NETO]= "Neto";
        colNames[IMPORTE]= "Importe";
    }
}
