package pos.presentation.historico;
import java.awt.event.*;
import java.io.FileNotFoundException;

import pos.logic.Cliente;
import pos.logic.Factura;
import pos.logic.Linea;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.awt.Desktop;
import java.io.File;


import java.util.List;

public class View  implements PropertyChangeListener {
    private JTextField buscliente;
    private JButton btnBuscar;
    private JButton btnReporte;
    private JTable list;
    private JTable lineas;
    private JPanel panel;
    private JLabel clienteLbl;
    private static final Border BORDER_ERROR = BorderFactory.createLineBorder(Color.RED, 2);
    private static final int MODE_EDIT = 1;
    public JPanel getPanel() {
        return panel;
    }
    public View() {

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(buscliente.getText().isEmpty()) {
                        clienteLbl.setBorder(BORDER_ERROR);
                    }else{
                        controller.eliminar();
                        Factura filter = new Factura();
                        Cliente cliente = new Cliente();
                        cliente.setNombre(buscliente.getText());
                        filter.setCliente(cliente);
                        controller.buscar(filter);
                    }
                    if(model.getListFactura().isEmpty()) {
                        JOptionPane.showMessageDialog(panel, "No se encontraron facturas","Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                    clienteLbl.setBorder(null);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = list.getSelectedRow();
                controller.editar(fila);
            }
        });
        btnReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.print();

                    if (Desktop.isDesktopSupported()) {
                        File myFile = new File("invoices.pdf");
                        Desktop.getDesktop().open(myFile);
                    }

                    JOptionPane.showMessageDialog(panel, "Report generated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                } catch (FileNotFoundException ex) {

                    JOptionPane.showMessageDialog(panel,
                            "Error: Unable to generate the PDF file. Make sure it is not being used by another program.",
                            "File in Use",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(panel,
                            "An unexpected error occurred while generating the report. Please try again.",
                            "Unexpected Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

//MVC
    ModelHistorico model;
    ControllerHistorico controller;
    public void setModel(ModelHistorico model) {
        this.model = model;
        model.addPropertyChangeListener(this);
        refrescarLineas();
    }
    public void setController(ControllerHistorico controllerHstorico) {
        this.controller = controllerHstorico;
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case ModelHistorico.LIST:
                int[] invoiceCols = {TableModelListado.CODIGO, TableModelListado.CLIENTE, TableModelListado.CASHIER, TableModelListado.DATE, TableModelListado.IMPORT};
                list.setModel(new TableModelListado(invoiceCols, model.getListFactura()));
                list.setRowHeight(30);
                TableColumnModel columnModel = list.getColumnModel();
                columnModel.getColumn(1).setPreferredWidth(150);
                columnModel.getColumn(3).setPreferredWidth(150);
                break;
            case ModelHistorico.CURRENT:
                refrescarLineas();
                break;
            case ModelHistorico.FILTER:
                if (model.getFilter().getCliente() != null) {
                    buscliente.setText(model.getFilter().getCliente().getNombre());
                } else {
                    buscliente.setText("");
                }

                break;
        }

        this.panel.revalidate();
        this.panel.repaint();
    }

    private void refrescarLineas() {
        List<Linea> lines = model.getLineasList();
        if (lines != null) {
            int[] lineCols = {TableModelLineas.CODIGO, TableModelLineas.ARTICULO, TableModelLineas.CATEGORIA, TableModelLineas.PRECIO, TableModelLineas.CANTIDAD, TableModelLineas.DESCUENTO, TableModelLineas.NETO, TableModelLineas.IMPORTE};
            TableModelLineas lineTableModel = new TableModelLineas(lineCols, lines);
            lineas.setModel(lineTableModel);
            lineas.setRowHeight(30);
            TableColumnModel lineColumnModel = lineas.getColumnModel();
            lineColumnModel.getColumn(1).setPreferredWidth(150);
            lineColumnModel.getColumn(3).setPreferredWidth(150);
        } else {
            JOptionPane.showMessageDialog(panel, "No lines available", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
