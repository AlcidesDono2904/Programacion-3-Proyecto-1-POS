package pos.presentation.estadistica;

import pos.logic.Categoria;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class View implements PropertyChangeListener {
    private JPanel panel;
    private JComboBox<String> desdeMes;
    private JComboBox<String> desdeAnio;
    private JComboBox<String> hastaAnio;
    private JComboBox<String> hastaMes;
    private JComboBox<Categoria> categoria;
    private JButton agregarCategoriaButton;
    private JButton agregarTodoButton;
    private JTable tabla;
    private JButton quitarSeleccionadoButton;
    private JButton quitarTodosButton;
    private JPanel graficoPanel;

    public View(){
        agregarTodoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.agregarTodo();
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    //MVC
    Model model;
    Controller controller;
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.TABLA:
                int[] cols=new int[model.getFechas().size()+1];
                cols[0]=TableModel.CATEGORIA;

                for(int i=1;i<model.getFechas().size()+1;i++){
                    cols[i]=i;
                }

                TableModel tablaModelo = new TableModel(cols, model.getRangos(),model.getFechas());

                tabla.setModel(tablaModelo);
                tabla.setRowHeight(15);
                break;
            case Model.CB:

                desdeMes.removeAll();
                for(LocalDate l:model.getFechasCB()){
                    desdeMes.addItem(l.getMonthValue()+"-"+l.getMonth().toString());
                }

                hastaMes.removeAll();
                for(LocalDate l:model.getFechasCB()){
                    hastaMes.addItem(l.getMonthValue()+"-"+l.getMonth().toString());
                }

                desdeAnio.removeAll();
                for(LocalDate l:model.getFechasCB()){
                    desdeAnio.addItem(l.getYear()+"");

                }

                hastaAnio.removeAll();
                for(LocalDate l:model.getFechasCB()){
                    hastaAnio.addItem(l.getYear()+"");
                }

                categoria.removeAll();
                for(Categoria c: model.getCategorias()){
                    categoria.addItem(c);
                }

                break;
        }
    }
}
