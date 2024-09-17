package pos.presentation.estadistica;

import pos.logic.Categoria;
import pos.logic.Rango;
import pos.presentation.AbstractTableModel;

import java.util.List;

public class TableModel extends AbstractTableModel<Rango> {

    public TableModel(int col[], List<Rango> rango) {
        super(col,rango);
    }

    @Override
    protected Object getPropetyAt(Rango rango, int col) {
        if (col == 0)
            return rango.getCategoria();
        else
            return rango.getImportes().get(col-1);
    }

    @Override
    protected void initColNames() {
        colNames = new String[rows.get(0).getImportes().size()+1];
        colNames[0] = "Categoria";
    }
}
