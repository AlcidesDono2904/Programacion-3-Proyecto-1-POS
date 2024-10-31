package pos.presentation.usuario;

import entidades.logic.Usuario;
import pos.presentation.AbstractTableModel;

import java.util.List;

public class TableModel extends AbstractTableModel<Usuario> {
    public static final int ID = 0;
    public static final int FACTURAS = 1;

    public TableModel(int[] cols, List<Usuario> usuarios) {super(cols, usuarios);}

    @Override
    public Class<?> getColumnClass(int col) {
        return col == 1 ? Boolean.class : String.class;
    }

    @Override
    protected Object getPropetyAt(Usuario o, int col) {
        switch (col) {
            case ID: return o.getNombre();
            case FACTURAS: return !(o.getFacturas().isEmpty());
            default: return "";
        }
    }

    @Override
    protected void initColNames() {
        colNames = new String[2];
        colNames[ID] = "id";
        colNames[FACTURAS] = "facturas?";
    }
}
