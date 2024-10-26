package pos.presentation.login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class View {
    private JPanel panel;
    private JPasswordField clavePF;
    private JTextField usuarioTF;
    private JButton logInButton;

    public View(){
        logInButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(validate()){
                        List<String> credenciales= take();
                        controller.login(credenciales.getFirst(),credenciales.getLast());
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
    }

    public JPanel getPanel() {return panel;}

    private boolean validate(){
        if(usuarioTF.getText().equals("")){
            JOptionPane.showMessageDialog(panel, "Ingrese un usuario");
            return false;
        }else if(clavePF.getText().equals("")){
            JOptionPane.showMessageDialog(panel, "Ingrese una clave");
            return false;
        }else return true;
    }

    public List<String> take(){
        List<String> list = new ArrayList<>();
        list.add(usuarioTF.getText());
        list.add(clavePF.getText());
        return list;
    }

    //VC

    private Controller controller;
    public Controller getController() {return controller;}
    public void setController(Controller controller) {this.controller = controller;}

}
