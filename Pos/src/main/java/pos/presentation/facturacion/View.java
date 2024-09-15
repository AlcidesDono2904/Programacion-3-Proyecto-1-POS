package pos.presentation.facturacion;


import pos.Application;
import pos.logic.Factura;
import pos.logic.Producto;

import pos.logic.Cliente;
import pos.logic.Cajero;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class View implements PropertyChangeListener {
    private JPanel panelTotales;
    private JPanel Funciones;
    private JPanel Producto;
    private JComboBox<Cliente> catcliente;
    private JPanel Cliente;
    private JComboBox<Cajero> catcajero;
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

    public View(pos.presentation.clientes.Controller clienteController, pos.presentation.cajeros.Controller cajeroController) {
        this.clienteController = clienteController;
        this.cajeroController = cajeroController;

        lineas.setRowSelectionAllowed(true);
        lineas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        lineas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row=lineas.getSelectedRow();
                if(row!=-1){
                    controller.edit(row);
                }
            }
        });

        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Producto p = new Producto();
                p.setCodigo(productoTextField.getText());
                try {
                    controller.agregarProducto(p);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuscarView buscarView = new BuscarView();
                buscarView.setController(getController());
                buscarView.setModel(getModel());

                JDialog popup = new JDialog((JFrame) null, "Buscar Producto", true);

                popup.add(buscarView.getPanel());

                //Tomar el boton y añadirle la accion de cerrar el popup
                JButton cancelar = buscarView.getCancelarButton();
                cancelar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        popup.dispose();
                    }
                });

                buscarView.getOKButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        popup.dispose();
                    }
                });

                popup.pack();
                popup.setLocationRelativeTo(null);
                popup.setVisible(true);
            }
        });
        // Cargar clientes y cajeros desde el controlador
        try {
            cargarClientes();  // Llenar el JComboBox de clientes
            cargarCajeros();   // Llenar el JComboBox de cajeros
        } catch (Exception e) {
            e.printStackTrace();
        }

        cantidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CantidadView cantidadView = new CantidadView();
                cantidadView.setController(getController());
                cantidadView.setModel(getModel());

                JDialog popup = new JDialog((JFrame) null, "Buscar Producto", true);

                popup.add(cantidadView.getPanel());

                //Tomar el boton y añadirle la accion de cerrar el popup
                JButton cancelar = cantidadView.getCancelarButton();

                cancelar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        popup.dispose();
                    }
                });

                cantidadView.getOKButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        popup.dispose();
                    }
                });

                popup.pack();
                popup.setLocationRelativeTo(null);
                popup.setVisible(true);
            }
        });

        descuento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DescuentoView descuentoView = new DescuentoView();
                descuentoView.setController(getController());
                descuentoView.setModel(getModel());

                JDialog popup = new JDialog((JFrame) null, "Buscar Producto", true);

                popup.add(descuentoView.getPanel());

                //Tomar el boton y añadirle la accion de cerrar el popup
                JButton cancelar = descuentoView.getCancelarButton();

                cancelar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        popup.dispose();
                    }
                });

                descuentoView.getOKButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        popup.dispose();
                    }
                });

                popup.pack();
                popup.setLocationRelativeTo(null);
                popup.setVisible(true);
            }
        });

        quitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.quitar();
            }
        });

        cobrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CobrarView cobrarView = new CobrarView();
                cobrarView.setController(getController());
                cobrarView.setModel(getModel());

                JDialog popup = new JDialog((JFrame) null, "Buscar Producto", true);

                popup.add(cobrarView.getPanel());

                //Tomar el boton y añadirle la accion de cerrar el popup
                JButton cancelar = cobrarView.getCancelarButton();

                cancelar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        popup.dispose();
                    }
                });

                cobrarView.getOKButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        popup.dispose();
                        //cobrar y hacer la factura al final
                        try{
                            controller.print();
                            String currentDirectory = System.getProperty("user.dir");

                            // Crea un objeto File con la ruta del archivo PDF
                            File pdfFile = new File(currentDirectory + File.separator + "pdfs/factura.pdf");

                            if (pdfFile.exists()) {
                                try {
                                    Desktop desktop = Desktop.getDesktop();

                                    desktop.open(pdfFile);
                                } catch (IOException ex) {
                                    System.out.println("No se pudo abrir el archivo PDF.");
                                    ex.printStackTrace();
                                }
                            } else {
                                System.out.println("El archivo PDF no existe en el directorio.");
                            }

                            controller.cobrar(take());

                        }catch(Exception ex){
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                    }
                });

                popup.pack();
                popup.setLocationRelativeTo(null);
                popup.setVisible(true);
            }
        });

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
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                controller.show();
            }
        });
    }
    public void actualizarClientes(List<Cliente> clientes) {
        catcliente.removeAllItems();
        for (Cliente cliente : clientes) {
            catcliente.addItem(cliente);
        }
    }

    public void actualizarCajeros(List<Cajero> cajeros) {
        catcajero.removeAllItems();
        for (Cajero cajero : cajeros) {
            catcajero.addItem(cajero);
        }
    }

    private void cargarClientes() throws Exception {
        if (clienteController == null) {
            throw new IllegalStateException("El controlador de clientes no está inicializado");
        }
        clienteController.search(new Cliente());
        List<Cliente> clientes = clienteController.model.getList();

        catcliente.removeAllItems(); // Limpiar el JComboBox
        for (Cliente cliente : clientes) {
            catcliente.addItem(cliente); // Añadir el nombre del cliente al JComboBox
        }
    }

    private void cargarCajeros() throws Exception {
        if (cajeroController == null) {
            throw new IllegalStateException("El controlador de cajeros no está inicializado");
        }
        cajeroController.search(new Cajero());
        List<Cajero> cajeros = cajeroController.model.getList();

        catcajero.removeAllItems(); // Limpiar el JComboBox
        for (Cajero cajero : cajeros) {
            catcajero.addItem(cajero); // Añadir el nombre del cajero al JComboBox
        }
    }

    public JComboBox<Cliente> getCliente() {
        return catcliente;
    }

    public JComboBox<Cajero> getCajero() {
        return catcajero;
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

    public Factura take(){
        Factura factura = new Factura();
        factura.setCajero((Cajero)catcajero.getSelectedItem());
        factura.setCliente((Cliente)catcliente.getSelectedItem());
        factura.setLineas(model.getLineas());

        return factura;
    }
    //MVC

    Model model;
    pos.presentation.facturacion.Controller controller;

    public void setModel(pos.presentation.facturacion.Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public Model getModel() {
        return this.model;
    }

    public void setController(pos.presentation.facturacion.Controller controller) {
        this.controller = controller;
    }

    public pos.presentation.facturacion.Controller getController() {
        return controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LINEAS:

                int[] cols = {TableModel.CODIGO, TableModel.ARTICULO, TableModel.CATEGORIA,TableModel.CANTIDAD, TableModel.PRECIO, TableModel.DESCUENTO, TableModel.NETO, TableModel.IMPORTE};
                lineas.setModel(new TableModel(cols, model.getLineas()));
                lineas.setRowHeight(30);

                lblArticulos.setText("Articulos: "+model.articulos());
                lblSubtotal.setText("Subtotal: "+model.subTotal());
                lblDescuentos.setText("Descuento: "+model.descuento());
                lblTotal.setText("Total: "+model.total());

                break;

            case Model.CURRENT:
                if (model.getMode()== Application.MODE_EDIT){
                    cantidad.setEnabled(true);
                    descuento.setEnabled(true);
                    quitar.setEnabled(true);
                }else{
                    cantidad.setEnabled(false);
                    descuento.setEnabled(false);
                    quitar.setEnabled(false);
                }
        }
    }
}