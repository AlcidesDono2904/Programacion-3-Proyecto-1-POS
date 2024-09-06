package pos.presentation.productos;

import javax.swing.*;

public class View extends JFrame {
    private JPanel panel;
    private JTextField codigo;
    private JTextField descripcion;
    private JTextField unidad;
    private JTextField precio;
    private JTextField existencias;
    private JButton save;
    private JButton clear;
    private JButton delete;
    private JTextField searchNombre;
    private JButton search;
    private JButton report;
    private JComboBox categorias;
    private JTable table1;
    private JLabel searchNombreLbl;
    private JLabel codLbl;
    private JLabel descripLbl;
    private JLabel unidadLbl;
    private JLabel precioLbl;
    private JLabel stockLbl;
    private JLabel catLbl;
    public JPanel getPanel(){
        return panel;
    }
}
