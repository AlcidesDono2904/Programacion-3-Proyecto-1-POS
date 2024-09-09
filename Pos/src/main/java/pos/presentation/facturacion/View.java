package pos.presentation.facturacion;

import javax.swing.*;
import java.awt.*;
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
    private JTable table1;
    private JTextField textField1;
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
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
