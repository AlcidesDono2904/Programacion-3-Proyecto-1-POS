package pos.presentation.estadistica;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel panel;
    private JComboBox desdeMes;
    private JComboBox desdeAnio;
    private JComboBox hastaAnio;
    private JComboBox hastaMes;
    private JComboBox categoria;
    private JButton agregarCategoriaButton;
    private JButton agregarTodoButton;
    private JTable tabla;
    private JButton quitarSeleccionadoButton;
    private JButton quitarTodosButton;
    private JPanel graficoPanel;

    //MVC
    //Model model;
    //Controller controller


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            //case Model.TABLA:


        }
    }
}
