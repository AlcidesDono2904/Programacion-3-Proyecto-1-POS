package pos.presentation.productos;

import pos.Application;

import pos.logic.Categoria;
import pos.logic.Producto;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

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
                Categoria selectedCategory = (Categoria) comboCategorias.getSelectedItem();
                if (selectedCategory != null) {
                    // Realizar la acción con la categoría seleccionada
                    try {
                        Producto filter = new Producto();
                        filter.setCategoria(selectedCategory);
                        controller.search(filter); // Llama al controlador para buscar productos con el filtro aplicado
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
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

       /*report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                ViewReporte report = new ViewReporte();
                JPanel panel = report.getPanel();
                JFrame frame = new JFrame();

                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setContentPane(panel);
                frame.pack();
                frame.setVisible(true);
            }
        });*/

       /* categoriasComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Categoria selectedCategory = (Categoria) categoriasComboBox.getSelectedItem();
                if (selectedCategory != null) {
                    try {
                        Producto filter = new Producto();
                        filter.setCategoria(selectedCategory);
                        controller.search(filter);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });*/

    }
   /* private void cargarCategorias(JComboBox<Categoria> comboCategorias) {
        List<Categoria> categoriasList = new ArrayList<>();
        categoriasList.add(new Categoria("001", "Dulces"));
        categoriasList.add(new Categoria("002", "Bebidas"));
        categoriasList.add(new Categoria("003", "Snacks"));

        for (Categoria categoria : categoriasList) {
            comboCategorias.addItem(categoria);
        }
    }*/
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
            case pos.presentation.productos.Model.COMBOBOX:
                for(Categoria c: model.getCategorias()){
                    categorias.addItem(c);
                }

            case pos.presentation.productos.Model.LIST:
                int[] cols = {TableModel.CODIGO, TableModel.DESCRIPCION,TableModel.UNIDAD_MEDIDA,TableModel.PRECIO_UNITARIO,};
                list.setModel(new TableModel(cols, model.getList()));
                list.setRowHeight(30);
                TableColumnModel columnModel = list.getColumnModel();
                columnModel.getColumn(0).setPreferredWidth(150);
                columnModel.getColumn(1).setPreferredWidth(150);
                break;
            case pos.presentation.clientes.Model.CURRENT:
                codigo.setText(model.getCurrent().getCodigo());
                descripcion.setText(model.getCurrent().getDescripcion());


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

                break;
            case Model.FILTER:
                searchNombre.setText(model.getFilter().getDescripcion());
                break;
        }

        this.panel.revalidate();
    }
}

