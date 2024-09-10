package pos.presentation.facturacion;

import pos.logic.Producto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class View implements PropertyChangeListener {
    private JPanel panelTotales;
    private JPanel Funciones;
    private JPanel Producto;
    private JComboBox comboBox1;
    private JPanel Cliente;
    private JComboBox comboBox2;
    private JPanel Lineas;
    private JTable lineas;
    private JTextField productoTextField;
    private JButton agregar;
    private JButton cobrar;
    private JButton buscar;
    private JButton cantidad;
    private JButton quitar;
    private JButton descuento;
    private JButton cancelar;
    private JLabel lblArticulos;
    private JLabel lblSubtotal;
    private JLabel lblDescuentos;
    private JLabel lblTotal;
    private JPanel panel;

    public JPanel getPanel() {
        return panel;
    }
    public View() {

        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Producto p = new Producto();
                p.setCodigo(productoTextField.getText());
                try{
                    controller.agregarProducto(p);
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
    }

    //MVC

    Model model;
    Controller controller;

    public void setModel(pos.presentation.facturacion.Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(pos.presentation.facturacion.Controller controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case Model.LINEAS:
                int[] cols = {TableModel.CODIGO,TableModel.ARTICULO,TableModel.CATEGORIA,TableModel.PRECIO,TableModel.DESCUENTO,TableModel.NETO,TableModel.IMPORTE};
                lineas.setModel(new TableModel(cols, model.getLineas()));
                lineas.setRowHeight(30);

                /*TableColumnModel columnModel = lineas.getColumnModel();
                columnModel.getColumn(1).setPreferredWidth(150);
                columnModel.getColumn(3).setPreferredWidth(150);*/
                break;

        }


    }
}
