package pos.presentation.estadistica;

import entidades.logic.Categoria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
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
        initFechas();
        categoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(categoria.getSelectedIndex() >= 0){
                    controller.cargarCategorias(categoria.getSelectedIndex());
                    tabla.clearSelection();
                }
            }
        });

        agregarCategoriaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)  {
                try{
                    validarFechas();
                    if(categoria.getSelectedIndex() >= 0){
                        Categoria c=new Categoria();
                        controller.addCategory((Categoria)categoria.getSelectedItem(),fechaInicial(),fechaFinal());

                    }else{
                        JOptionPane.showMessageDialog(null, "Debe seleccionar una categoria");
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        agregarTodoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try{
                    validarFechas();
                    controller.addAllCategories(fechaInicial(),fechaFinal());
                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        desdeAnio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    validarFechas();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        desdeMes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    validarFechas();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        hastaAnio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    validarFechas();

                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        hastaMes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    validarFechas();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(tabla.getRowCount() > 0){
                    controller.selectCategory(tabla.getSelectedRow());
                }
            }
        });

        quitarSeleccionadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.removeCategory();
            }
        });

        quitarTodosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
            }
        });


    }

    public JPanel getPanel() {
        return panel;
    }

    private void validarFechas()throws Exception{
        Date inicio = fechaInicial();
        Date fin = fechaFinal();
        if(inicio != null && fin != null){
            if(inicio.after(fin)){
                throw new Exception("La fecha final no puede ser antes de la fecha inicio");
            }
        }else {
            throw new Exception("Se deben seleccionar las fechas a consultar");
        }
    }
    public Date fechaFinal(){
        Date fecha=new Date();
        if(hastaAnio.getSelectedIndex() >= 0 && hastaMes.getSelectedIndex() >= 0){
            int anio = Integer.parseInt(hastaAnio.getSelectedItem().toString())-1900;
            int mes = Integer.parseInt(hastaMes.getSelectedItem().toString().split("-")[0])-1;
            fecha.setYear(anio);
            fecha.setMonth(mes);
            fecha.setDate(1);
        }
        return fecha;
    }

    public Date fechaInicial()throws Exception{
        Date fecha=new Date();
        if(desdeAnio.getSelectedIndex() >= 0 && desdeMes.getSelectedIndex() >= 0){
                int anio = Integer.parseInt(desdeAnio.getSelectedItem().toString())-1900;
                int mes = Integer.parseInt(desdeMes.getSelectedItem().toString().split("-")[0])-1;
                fecha.setYear(anio);
                fecha.setMonth(mes);
                fecha.setDate(1);
        }
        return fecha;
    }

    public void initFechas(){
        Date today = new Date();
        DefaultComboBoxModel<String> cModel = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<String> cModel2 = new DefaultComboBoxModel<>();
        for(int i=today.getYear()-5; i<= today.getYear(); i++){
            cModel.addElement((i+1900)+"");
            cModel2.addElement((i+1900)+"");
        }
        desdeAnio.setModel(cModel);
        hastaAnio.setModel(cModel2);
        hastaMes.addItem("1-Enero");
        desdeMes.addItem("1-Enero");
        hastaMes.addItem("2-Febrero");
        desdeMes.addItem("2-Febrero");
        hastaMes.addItem("3-Marzo");
        desdeMes.addItem("3-Marzo");
        hastaMes.addItem("4-Abril");
        desdeMes.addItem("4-Abril");
        hastaMes.addItem("5-Mayo");
        desdeMes.addItem("5-Mayo");
        hastaMes.addItem("6-Junio");
        desdeMes.addItem("6-Junio");
        hastaMes.addItem("7-Julio");
        desdeMes.addItem("7-Julio");
        hastaMes.addItem("8-Agosto");
        desdeMes.addItem("8-Agosto");
        hastaMes.addItem("9-Septiembre");
        desdeMes.addItem("9-Septiembre");
        hastaMes.addItem("10-Octubre");
        desdeMes.addItem("10-Octubre");
        hastaMes.addItem("11-Noviembre");
        desdeMes.addItem("11-Noviembre");
        hastaMes.addItem("12-Diciembre");
        desdeMes.addItem("12-Diciembre");
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
                int desA=Integer.parseInt((String)desdeAnio.getSelectedItem());
                int desM=Integer.parseInt(desdeMes.getSelectedItem().toString().split("-")[0]);
                int hasA=Integer.parseInt((String)hastaAnio.getSelectedItem());
                int hasM=Integer.parseInt(hastaMes.getSelectedItem().toString().split("-")[0]);

                int n=(hasA-desA)*12+(hasM-desM)+1;

                int[] cols=new int[n];
                cols[0]=TableModel.CATEGORIA;
                for (int i=1;i<n;i++){
                    cols[i]=i;
                }

                List<String> fechas=new ArrayList<>();
                int[] meses=new int[12];
                for(int i=0;i<12;i++){
                    meses[i]=i;
                }

                LocalDate fechaInicio = LocalDate.of(desA,desM,1);
                LocalDate fechaFin = LocalDate.of(hasA,hasM,1);

                while (!fechaInicio.isAfter(fechaFin)) {

                    String fechaAM=fechaInicio.toString().split("-")[0]+"-"+fechaInicio.toString().split("-")[1];
                    fechas.add(fechaAM);
                    fechaInicio = fechaInicio.plus(1, ChronoUnit.MONTHS);
                }

                TableModel tablaModelo = new TableModel(cols, model.getRangos(),fechas);

                tabla.setModel(tablaModelo);
                tabla.setRowHeight(20);

                DefaultCategoryDataset dataset = new DefaultCategoryDataset();

                for (int row = 0; row < model.getRangos().size(); row++) {
                    for (int col = 0; col < fechas.size(); col++) {

                        String categoria = model.getRangos().get(row).getCategoria().toString();
                        double valor=0;
                        int index = model.getRangos().get(row).getFechas().indexOf(fechas.get(col));
                        if (index != -1) {
                            valor=model.getRangos().get(row).getImportes().get(index);
                        }
                        String fecha = fechas.get(col);

                        dataset.addValue(valor, categoria, fecha);
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
                categoria.removeAll();
                for(Categoria c: model.getCategorias()){
                    categoria.addItem(c);
                }
                break;
        }
    }
}
