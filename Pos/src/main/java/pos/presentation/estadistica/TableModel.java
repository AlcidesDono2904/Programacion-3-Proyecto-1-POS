package pos.presentation.estadistica;

import pos.logic.Categoria;
import pos.logic.Rango;
import pos.presentation.AbstractTableModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TableModel extends AbstractTableModel<Rango> {
    private List<LocalDate> fechas;

    public TableModel(int cols[], List<Rango> rango,List<LocalDate> fechas) {
        this.cols=cols;
        this.rows=rango;
        this.fechas=fechas;
        initColNames();
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
        if(rows.isEmpty()){
            colNames=new String[1];
        }else{
            colNames = new String[rows.getFirst().getImportes().size()+1];
        }

        colNames[0] = "Categoria";
        for(int i=0;i<fechas.size();i++) {
            colNames[i+1] = fechas.get(i).toString();
        }
    }

    public static final int CATEGORIA = 0;
}
