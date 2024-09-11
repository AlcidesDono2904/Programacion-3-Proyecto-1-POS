package pos.presentation.facturacion;


import pos.logic.Producto;
/*
import pos.logic.Cliente;
import pos.logic.Cajero;
import pos.presentation.clientes.Controller;
*/


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.List;

public class View implements PropertyChangeListener {
    private JPanel panelTotales;
    private JPanel Funciones;
    private JPanel Producto;
    private JComboBox<String> catcliente;
    private JPanel Cliente;
    private JComboBox<String> catcajero;
    private JPanel Lineas;
    private JTable lineas;
    private JTextField productoTextField;
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

    // Controladores para clientes y cajeros
    // Controladores para clientes y cajeros
    private pos.presentation.clientes.Controller clienteController;
    private pos.presentation.cajeros.Controller cajeroController;

    // Metodo para obtener el panel (no cambia)
    public JPanel getPanel() {
        return panel;
    }

    public View() {

        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Producto p = new Producto();
                p.setCodigo(productoTextField.getText());
                try{
                    controller.agregarProducto(p);
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuscarView buscarView = new BuscarView();
                buscarView.setModel(getModel());
                buscarView.setController(getController());

                // Crear el popup (JDialog)
                JDialog popup = new JDialog((JFrame) null, "Buscar Producto", true); // null si no tienes un JFrame padre

                // Añadir el JPanel de buscarView al popup
                popup.add(buscarView.getPanel());

                // Ajustar el tamaño del popup de acuerdo al contenido
                popup.pack(); // Ajusta el tamaño automáticamente según los componentes
                popup.setLocationRelativeTo(null); // Centrar en la pantalla
                popup.setVisible(true); // Mostrar el popup
            }
        });
    }

    //MVC

    Model model;
    Controller controller;

    public void setModel(pos.presentation.facturacion.Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public Model getModel(){
        return this.model;
    }

    public void setController(pos.presentation.facturacion.Controller controller) {
        this.controller = controller;
    }

    public Controller getController(){return controller;}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case Model.LINEAS:
                int[] cols = {TableModel.CODIGO,TableModel.ARTICULO,TableModel.CATEGORIA,TableModel.PRECIO,TableModel.DESCUENTO,TableModel.NETO,TableModel.IMPORTE};
                lineas.setModel(new TableModel(cols, model.getLineas()));
                lineas.setRowHeight(30);

                /*TableColumnModel columnModel = lineas.getColumnModel();
                columnModel.getColumn(1).setPreferredWidth(150);
                columnModel.getColumn(3).setPreferredWidth(150);*/
                break;

        }
    }


/*
    // Constructor que recibe los controladores
    public View(Controller clienteController, pos.presentation.cajeros.Controller cajeroController) {
        this.clienteController = clienteController;
        this.cajeroController = cajeroController;

        // Cargar clientes y cajeros desde el controlador
        try {
            cargarClientes();  // Llenar el JComboBox de clientes
            cargarCajeros();   // Llenar el JComboBox de cajeros
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Listeners para cuando se seleccionan cliente y cajero
        catcliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarCliente(); // Lógica al seleccionar cliente
            }
        });

        catcajero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarCajero(); // Lógica al seleccionar cajero
            }
        });
    }

    // Metodo para cargar los clientes en el JComboBox catcliente desde el controlador
    private void cargarClientes() throws Exception {
        // Usamos el metodo search para obtener la lista de clientes
        clienteController.search(new Cliente());
        List<Cliente> clientes = clienteController.model.getList();

        catcliente.removeAllItems(); // Limpiar el JComboBox
        for (Cliente cliente : clientes) {
            catcliente.addItem(cliente.getNombre()); // Añadir el nombre del cliente al JComboBox
        }
    }

    // Metodo para cargar los cajeros en el JComboBox catcajero desde el controlador
    private void cargarCajeros() throws Exception {
        // Usamos el metodo search para obtener la lista de cajeros
        cajeroController.search(new Cajero());
        List<Cajero> cajeros = cajeroController.model.getList();

        catcajero.removeAllItems(); // Limpiar el JComboBox
        for (Cajero cajero : cajeros) {
            catcajero.addItem(cajero.getNombre()); // Añadir el nombre del cajero al JComboBox
        }
    }

    // Metodo que se ejecuta cuando se selecciona un cliente
    private void seleccionarCliente() {
        String clienteSeleccionado = (String) catcliente.getSelectedItem();
        System.out.println("Cliente seleccionado: " + clienteSeleccionado);

    }

    // Metodo que se ejecuta cuando se selecciona un cajero
    private void seleccionarCajero() {
        String cajeroSeleccionado = (String) catcajero.getSelectedItem();
        System.out.println("Cajero seleccionado: " + cajeroSeleccionado);

    }
//MVC
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //  cambio en el modelo

    }*/
}