package pos.presentation.cajeros;

import pos.Application;
import pos.logic.Cajero;
import pos.presentation.cajeros.Controller;
import pos.presentation.cajeros.Model;
import pos.presentation.cajeros.TableModel;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

public class View implements PropertyChangeListener {
    private JPanel panel;
    private JTextField searchNombre;
    private JButton search;
    private JTable list;
    private JLabel idLbl;
    private JTextField id;
    private JLabel nombreLbl;
    private JTextField nombre;
    private JButton save;
    private JButton delete;
    private JButton clear;
    private JLabel searchNombreLbl;
    private JButton report;

    public JPanel getPanel() {
        return panel;
    }

    public View() {
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Cajero filter = new Cajero();
                    filter.setNombre(searchNombre.getText());
                    controller.search(filter);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validate()) {
                    Cajero n = take();
                    try {
                        controller.save(n);
                        JOptionPane.showMessageDialog(panel, "REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = list.getSelectedRow();
                controller.edit(row);
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.delete();
                    JOptionPane.showMessageDialog(panel, "REGISTRO BORRADO", "", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
            }
        });

       report.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    controller.print();
                    String currentDirectory = System.getProperty("user.dir");

                    // Crea un objeto File con la ruta del archivo PDF
                    File pdfFile = new File(currentDirectory + File.separator + "pdfs/cajeros.pdf");

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
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean validate() {
        boolean valid = true;
        if (id.getText().isEmpty()) {
            valid = false;
            idLbl.setBorder(Application.BORDER_ERROR);
            idLbl.setToolTipText("Codigo requerido");
        } else {
            idLbl.setBorder(null);
            idLbl.setToolTipText(null);
        }

        if (nombre.getText().isEmpty()) {
            valid = false;
            nombreLbl.setBorder(Application.BORDER_ERROR);
            nombreLbl.setToolTipText("Nombre requerido");
        } else {
            nombreLbl.setBorder(null);
            nombreLbl.setToolTipText(null);
        }

        return valid;
    }

    public Cajero take() {
        Cajero e = new Cajero();
        e.setId(id.getText());
        e.setNombre(nombre.getText());

        return e;
    }

    // MVC
    pos.presentation.cajeros.Model model;
    pos.presentation.cajeros.Controller controller;

    public void setModel(pos.presentation.cajeros.Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case pos.presentation.cajeros.Model.LIST:
                int[] cols = {pos.presentation.cajeros.TableModel.ID, pos.presentation.cajeros.TableModel.NOMBRE};
                list.setModel(new TableModel(cols, model.getList()));
                list.setRowHeight(30);
                TableColumnModel columnModel = list.getColumnModel();
                columnModel.getColumn(0).setPreferredWidth(150);
                columnModel.getColumn(1).setPreferredWidth(150);
                break;
            case pos.presentation.clientes.Model.CURRENT:
                id.setText(model.getCurrent().getId());
                nombre.setText(model.getCurrent().getNombre());


                if (model.getMode() == Application.MODE_EDIT) {
                    id.setEnabled(false);
                    delete.setEnabled(true);
                } else {
                    id.setEnabled(true);
                    delete.setEnabled(false);
                }

                idLbl.setBorder(null);
                idLbl.setToolTipText(null);
                nombreLbl.setBorder(null);
                nombreLbl.setToolTipText(null);

                break;
            case Model.FILTER:
                searchNombre.setText(model.getFilter().getNombre());
                break;
        }

        this.panel.revalidate();
    }

}
