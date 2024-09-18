package pos.presentation.estadistica;

import org.jfree.chart.ChartUtilities;
import pos.logic.Categoria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

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

                DefaultCategoryDataset dataset = new DefaultCategoryDataset();

                for (int row = 0; row < model.getRangos().size(); row++) {
                    for (int col = 0; col < model.getFechas().size(); col++) {

                        String categoria = model.getRangos().get(row).getCategoria().toString();
                        double valor = model.getRangos().get(row).getImportes().get(col);
                        String fecha = model.getFechas().get(col).toString();

                        dataset.addValue(valor, categoria, fecha);
                        /*Number value,
                        Comparable rowKey,
                            Comparable columnKey )*/
                    }
                }


                JFreeChart chart = ChartFactory.createLineChart(
                        "Mi Gráfico",
                        "Fechas",
                        "Valores",
                        dataset
                );

                ChartPanel chartPanel = new ChartPanel(chart);


                chartPanel.setPreferredSize(new java.awt.Dimension(560, 370));

                graficoPanel.removeAll();
                graficoPanel.setLayout(new BorderLayout());
                graficoPanel.add(chartPanel, BorderLayout.CENTER);

                graficoPanel.revalidate();
                graficoPanel.repaint();

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
