package pos.presentation.facturacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DescuentoView {
    private JTextField descuento;
    private JButton OKButton;
    private JButton cancelarButton;
    private JPanel panel;

    DescuentoView(){
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    controller.save((Double) Double.parseDouble(descuento.getText()));
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
    }

    public JPanel getPanel(){
        return panel;
    }

    public JButton getOKButton(){
        return OKButton;
    }

    public JButton getCancelarButton(){
        return cancelarButton;
    }

    //MVC
    Model model;
    Controller controller;

    public void setModel(Model model){
        this.model = model;
    }

    public void setController(Controller controller){
        this.controller = controller;
    }
}
