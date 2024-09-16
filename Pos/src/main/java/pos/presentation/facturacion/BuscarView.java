package pos.presentation.facturacion;

import pos.logic.Producto;
import pos.presentation.productos.TableModelListado;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BuscarView implements PropertyChangeListener {
    private JTextField descripcion;
    private JTable productos;
    private JButton OKButton;
    private JButton cancelarButton;
    private JPanel panel;

    public BuscarView(){
        descripcion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                try{
                    Producto p = new Producto();
                    p.setDescripcion(descripcion.getText());
                    controller.search(p);
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    controller.agregarProducto(model.getProductos().get(productos.getSelectedRow()));
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
    }

    public JButton getCancelarButton() {return cancelarButton;}

    public JButton getOKButton() {return OKButton;}

    public JPanel getPanel(){return panel;}

    //MVC
    Model model;
    Controller controller;

    public void setModel(pos.presentation.facturacion.Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
        try{
            controller.search(new Producto());
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public void setController(pos.presentation.facturacion.Controller controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case Model.PRODUCTOS:
                int[] cols = {TableModelListado.CODIGO, TableModelListado.DESCRIPCION, TableModelListado.UNIDAD_MEDIDA, TableModelListado.PRECIO_UNITARIO, TableModelListado.EXISTENCIAS, TableModelListado.CATEGORIA};

                productos.setModel(new TableModelListado(cols, model.getProductos()));
                productos.setRowHeight(30);

                TableColumnModel columnModel = productos.getColumnModel();
                columnModel.getColumn(0).setPreferredWidth(100);
                columnModel.getColumn(1).setPreferredWidth(150);
                columnModel.getColumn(3).setPreferredWidth(150);
                break;

        }
    }
}
