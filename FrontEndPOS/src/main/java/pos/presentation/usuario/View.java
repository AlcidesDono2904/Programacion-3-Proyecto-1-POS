package pos.presentation.usuario;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel panel;
    private JTable usuarios;
    private JButton enviarButton;
    private JButton recibirButton;

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
                ;
        }
    }
}
