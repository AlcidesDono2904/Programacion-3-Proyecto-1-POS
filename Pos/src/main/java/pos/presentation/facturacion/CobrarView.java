package pos.presentation.facturacion;

import entidades.logic.Factura;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CobrarView implements PropertyChangeListener {
    private JButton cancelarButton;
    private JButton OKButton;
    private JTextField efectivo;
    private JTextField tarjete;
    private JTextField cheque;
    private JTextField sinpe;
    private JLabel importeTotal;
    private JPanel panel;

    CobrarView(){
        // Inicializar los JTextField con 0 por defecto
        efectivo.setText("0");
        tarjete.setText("0");
        cheque.setText("0");
        sinpe.setText("0");
        OKButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               try{
                   if(!validate())
                       throw new Exception("El monto cobrado no es");
               }catch(Exception ex){
                   JOptionPane.showMessageDialog(null, ex.getMessage());
               }
           }
        });

    }

    public JPanel getPanel(){
        return panel;
    }

    public JButton getCancelarButton(){
        return cancelarButton;
    }

    public JButton getOKButton(){
        return OKButton;
    }


    private boolean validate(){
        return (Double.parseDouble(efectivo.getText())+Double.parseDouble(sinpe.getText())+Double.parseDouble(tarjete.getText())+Double.parseDouble(cheque.getText()))==model.total();
    }

    //MVC
    Model model;
    Controller controller;

    public void setModel(Model model){
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    public void propertyChange(PropertyChangeEvent evt){
        switch (evt.getPropertyName()){
            case Model.LINEAS:
                importeTotal.setText(model.total()+"");
        }
    }

}
