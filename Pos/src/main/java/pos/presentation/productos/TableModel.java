package pos.presentation.productos;

import pos.logic.Cliente;
import pos.logic.Productos;
import pos.presentation.AbstractTableModel;

import java.util.List;
public class TableModel extends AbstractTableModel<Productos> implements javax.swing.table.TableModel {

    public TableModel(int[] cols, List<Productos> rows) {
        super(cols, rows);
    }

    // Definir las constantes para las columnas correspondientes a productos
    public static final int CODIGO = 0;
    public static final int DESCRIPCION = 1;
    public static final int UNIDAD_MEDIDA = 2;
    public static final int PRECIO_UNITARIO = 3;
    public static final int EXISTENCIAS = 4;
    public static final int CATEGORIA = 5;

    @Override
    protected Object getPropetyAt(Productos e, int col) {
        // Usar un switch para retornar el valor correcto de acuerdo a la columna seleccionada
        switch (cols[col]) {
            case CODIGO: return e.getCodigo();
            case DESCRIPCION: return e.getDescripcion();
            case UNIDAD_MEDIDA: return e.getUnidadMedida();
            case PRECIO_UNITARIO: return e.getPrecioUnitario();
            case EXISTENCIAS: return e.getExistencias();
            case CATEGORIA: return e.getCategoria();
            default: return "";
        }
    }

    @Override
    protected void initColNames(){
        // Inicializar los nombres de las columnas de acuerdo a las propiedades del producto
        colNames = new String[6];
        colNames[CODIGO] = "Código";
        colNames[DESCRIPCION] = "Descripción";
        colNames[UNIDAD_MEDIDA] = "Unidad de Medida";
        colNames[PRECIO_UNITARIO] = "Precio Unitario";
        colNames[EXISTENCIAS] = "Existencias";
        colNames[CATEGORIA] = "Categoría";
    }
}
