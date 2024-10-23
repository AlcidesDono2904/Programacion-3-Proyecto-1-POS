package pos.presentation.estadistica;

import pos.logic.Categoria;
import pos.logic.Rango;
import pos.presentation.AbstractTableModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TableModel extends AbstractTableModel<Rango> {
    private List<String> fechas;

    public TableModel(int cols[], List<Rango> rango,List<String> fechas) {
        this.cols=cols;
        this.rows=rango;
        this.fechas=fechas;
        initColNames();
    }

    @Override
    protected Object getPropetyAt(Rango rango, int col) {
        if (col == 0)
            return rango.getCategoria();
        else{
            int index = rango.getFechas().indexOf(fechas.get(col - 1));
            if (index != -1) {
                return rango.getImportes().get(index);
            }
            return 0;
        }
    }

    @Override
    protected void initColNames() {
       /* if(rows.isEmpty()){
            colNames=new String[1];
        }else{

        }*/
        colNames = new String[fechas.size()+1];
        colNames[0]="CATEGORIA";
        for(int i=1;i<fechas.size() ;i++) {
            colNames[i] = fechas.get(i-1);
        }
    }

    public static final int CATEGORIA = 0;
}
