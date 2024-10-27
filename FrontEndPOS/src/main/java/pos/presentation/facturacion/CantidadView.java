package pos.presentation.facturacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CantidadView {
    private JTextField cantidad;
    private JButton OKButton;
    private JButton cancelarButton;
    private JPanel panel;

    CantidadView(){
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(validate()){
                        controller.save(Integer.parseInt(cantidad.getText()));
                    }
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

    private boolean validate()throws Exception{
        if(Integer.parseInt(cantidad.getText()) >= 0){
            return true;
        }else{
            throw new Exception("El valor dado no puede ser inferior a cero");
        }
    }

    //MVC
    private Model model;
    private Controller controller;

    public void setModel(Model model){
        this.model = model;
    }

    public void setController(Controller controller){
        this.controller=controller;
    }
}
