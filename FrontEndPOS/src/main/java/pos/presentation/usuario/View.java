package pos.presentation.usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel panel;
    private JTable usuarios;
    private JButton enviarButton;
    private JButton recibirButton;

    public View() {
        usuarios.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseReleased(MouseEvent e) {
               controller.current(usuarios.getSelectedRow());
           }
        });
        enviarButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               controller.enviar();
           }
        });
        recibirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    controller.popFactura();
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    //MVC
    Model model;
    Controller controller;

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.TABLA:
                int[] cols={TableModel.ID,TableModel.FACTURAS};

                usuarios.setModel(new TableModel(cols,model.getList()));
                break;
            case Model.MODE:
                if(model.getMode()==Model.CREATE){
                    enviarButton.setEnabled(false);
                    recibirButton.setEnabled(false);
                }else{
                    enviarButton.setEnabled(true);
                    recibirButton.setEnabled(true);
                }
        }
    }
}
