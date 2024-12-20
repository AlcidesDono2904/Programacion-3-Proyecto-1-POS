package pos.presentation.productos;

import pos.Application;

import entidades.logic.Categoria;
import entidades.logic.Producto;

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
    private JTextField codigo;
    private JTextField descripcion;
    private JTextField unidad;
    private JTextField precio;
    private JTextField existencias;
    private JButton save;
    private JButton clear;
    private JButton delete;
    private JTextField searchNombre;
    private JButton search;
    private JButton report;
    private JComboBox categorias;
    private JTable list;
    private JLabel searchNombreLbl;
    private JLabel codLbl;
    private JLabel descripLbl;
    private JLabel unidadLbl;
    private JLabel precioLbl;
    private JLabel stockLbl;
    private JLabel catLbl;
    private JComboBox<Categoria> categoriasComboBox;
    public JPanel getPanel() {
        return panel;
    }

    public View() {
        // Castear el ComboBox a JComboBox<Categoria>
        JComboBox<Categoria> comboCategorias = (JComboBox<Categoria>) categorias;

        // Cargar categorías en el ComboBox
       // cargarCategorias(comboCategorias);

        // Manejar la selección de una categoría
        comboCategorias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // Acción para buscar productos
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Producto filter = new Producto();
                    filter.setDescripcion(searchNombre.getText()); // Búsqueda por descripción
                    controller.search(filter); // Realiza la búsqueda con el filtro
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Acción para guardar productos (crear o actualizar)
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validate()) { // Validar los campos del formulario
                    Producto n = take(); // Crear un producto a partir de los datos del formulario
                    try {
                        controller.save(n); // Guardar el producto
                        JOptionPane.showMessageDialog(panel, "REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Acción para seleccionar un producto de la lista y editarlo
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = list.getSelectedRow(); // Obtener la fila seleccionada en la tabla
                controller.edit(row); // Editar el producto correspondiente
            }
        });

        // Acción para eliminar un producto seleccionado
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.delete(); // Eliminar el producto actual
                    JOptionPane.showMessageDialog(panel, "REGISTRO BORRADO", "", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción para limpiar el formulario y preparar para la creación de un nuevo producto
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear(); // Limpiar los campos del formulario
            }
        });

        report.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    controller.print();
                    String currentDirectory = System.getProperty("user.dir");

                    // Crea un objeto File con la ruta del archivo PDF
                    File pdfFile = new File(currentDirectory + File.separator + "pdfs/productos.pdf");

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
        if (codigo.getText().isEmpty()) {
            valid = false;
            codLbl.setBorder(Application.BORDER_ERROR);
            codLbl.setToolTipText("Codigo requerido");
        } else {
            codLbl.setBorder(null);
            codLbl.setToolTipText(null);
        }

        if (descripcion.getText().isEmpty()) {
            valid = false;
            descripLbl.setBorder(Application.BORDER_ERROR);
            descripLbl.setToolTipText("Nombre requerido");
        } else {
            descripLbl.setBorder(null);
            descripLbl.setToolTipText(null);
        }

        if (unidad.getText().isEmpty()) {
            valid = false;
            unidadLbl.setBorder(Application.BORDER_ERROR);
            unidadLbl.setToolTipText("unidad requerido");
        } else {
            unidadLbl.setBorder(null);
            unidadLbl.setToolTipText(null);
        }

        if (precio.getText().isEmpty()) {
            valid = false;
            precioLbl.setBorder(Application.BORDER_ERROR);
            precioLbl.setToolTipText("Precio requerida");
        } else {
            precioLbl.setBorder(null);
            precioLbl.setToolTipText(null);
        }

        // Validación de las existencias
        try {
            Integer.parseInt(existencias.getText());
            existencias.setBorder(null);
            existencias.setToolTipText(null);
        } catch (Exception e) {
            valid = false;
            existencias.setBorder(Application.BORDER_ERROR);
            existencias.setToolTipText("Existencias inválidas");
        }

        // Validación de la categoría
        if (categorias.getSelectedItem() == null) {
            valid = false;
            catLbl.setBorder(Application.BORDER_ERROR);
            catLbl.setToolTipText("Categoría requerida");
        } else {
            catLbl.setBorder(null);
            catLbl.setToolTipText(null);
        }
        return valid;
    }

    public Producto take() {
        Producto e = new Producto();
        e.setCodigo(codigo.getText());
        e.setDescripcion(descripcion.getText());
        e.setUnidadMedida(unidad.getText());
        e.setPrecioUnitario(Double.parseDouble(precio.getText()));
        e.setExistencias(Integer.parseInt(existencias.getText()));
        e.setCategoria((Categoria) categorias.getSelectedItem()); // Usar Categoria aquí
        return e;
    }
    // MVC
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
            case Model.COMBOBOX:
                for(Categoria c: model.getCategorias()){
                    categorias.addItem(c);
                }

                break;
            case Model.LIST:
                int[] cols = {TableModel.CODIGO, TableModel.DESCRIPCION, TableModel.UNIDAD_MEDIDA, TableModel.PRECIO_UNITARIO, TableModel.EXISTENCIAS, TableModel.CATEGORIA};

                list.setModel(new TableModel(cols, model.getList()));
                list.setRowHeight(30);
                TableColumnModel columnModel = list.getColumnModel();
                columnModel.getColumn(0).setPreferredWidth(200);
                columnModel.getColumn(1).setPreferredWidth(200);
                columnModel.getColumn(5).setPreferredWidth(300);
                break;
            case Model.CURRENT:
                codigo.setText(model.getCurrent().getCodigo());
                descripcion.setText(model.getCurrent().getDescripcion());
                unidad.setText(model.getCurrent().getUnidadMedida());
                precio.setText(""+ model.getCurrent().getPrecioUnitario());
                existencias.setText(""+ model.getCurrent().getExistencias());

                categorias.setSelectedItem(model.getCurrent().getCategoria());
                if (model.getMode() == Application.MODE_EDIT) {
                    codigo.setEnabled(false);
                    delete.setEnabled(true);
                } else {
                    codigo.setEnabled(true);
                    delete.setEnabled(false);
                }

                codLbl.setBorder(null);
                codLbl.setToolTipText(null);
                descripLbl.setBorder(null);
                descripLbl.setToolTipText(null);
                unidadLbl.setBorder(null);
                unidadLbl.setToolTipText(null);
                precioLbl.setBorder(null);
                precioLbl.setToolTipText(null);
                existencias.setBorder(null);
                existencias.setToolTipText(null);
                categorias.setBorder(null);

                //categorias.setSelectedItem(model.getCurrent().getCategorias());

                break;
            case Model.FILTER:
                searchNombre.setText(model.getFilter().getDescripcion());
                break;
        }

        this.panel.revalidate();
    }
}

