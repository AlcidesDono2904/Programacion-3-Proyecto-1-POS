package pos.presentation.facturacion;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import pos.Application;
import entidades.logic.*;
import pos.logic.Service;

import javax.swing.*;

public class Controller {
    View view;
    Model model;

    public Controller(View facturacionView, Model facturacionModel) {
        facturacionModel.init(Service.instance().search(new Producto()),Service.instance().search(new Cliente()),Service.instance().search(new Cajero()));
        this.view = facturacionView;
        this.model = facturacionModel;
        view.setController(this);
        view.setModel(model);
    }
    public void agregarCliente(Cliente c) throws Exception {
        Service.instance().create(c);
        model.setClientes(Service.instance().search(new Cliente()));
        view.actualizarClientes(model.getClientes());
    }

    public void agregarCajero(Cajero c) throws Exception {
        Service.instance().create(c);
        model.setCajeros(Service.instance().search(new Cajero()));
        view.actualizarCajeros(model.getCajeros());
    }

    public void agregarProducto(Producto p) throws Exception{
        model.setMode(Application.MODE_CREATE);
        for(var i : model.getLineas()){
            if(i.getProducto().equals(p)){
                return;
            }
        }
        Producto aux = Service.instance().read(p);
        if(aux.getExistencias() > 0) {
            model.agregarLinea(aux);
        }
        else{
            throw new Exception("No se puede agregar el producto, no hay suficientes existencias");
        }

}

    public void search(Producto filter) throws Exception {
        model.setProductos(Service.instance().search(filter));
    }
    public void show(){
        model.setProductos(Service.instance().search(new Producto()));
        model.setCajeros(Service.instance().search(new Cajero()));
        model.setClientes(Service.instance().search(new Cliente()));
    }

    public void edit(int row){
       // Verificar que la fila seleccionada sea válida
        if (row < 0 || row >= model.getLineas().size()) {
            JOptionPane.showMessageDialog(null, "Fila seleccionada no válida", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener el producto seleccionado desde la lista del modelo
        Linea lineaSeleccionada = model.getLineas().get(row);

        try {
            // Cambiar el modo del modelo a "editar"
            model.setMode(Application.MODE_EDIT);

            // Establecer el producto seleccionado como el producto actual en el modelo
            model.setCurrent(lineaSeleccionada);

            // Actualizar la vista con los datos del producto seleccionado
            // Esto generalmente ya se manejaría en el metodo "propertyChange" si estás usando Observer
        } catch (Exception ex) {
            // Manejar cualquier excepción que pueda ocurrir
            JOptionPane.showMessageDialog(null, "Error al intentar editar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void save(int cant)throws Exception{
        Linea l=model.getCurrent();

        int total=cant;
        for(Linea i : model.getLineas()){
            if(i.getProducto()==l.getProducto()){
                total+=i.getCantidad();
                if(total>l.getProducto().getExistencias()) {
                    throw new Exception("La cantidad de productos a facturar no puede ser mayor a las existencias");
                }
            }
        }

        l.setCantidad(cant);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(null);
}

    public void save(double desc)throws Exception{
        if(desc>100.0 || desc<0.0){
            throw new Exception();
        }

        Linea l=model.getCurrent();

        l.setDescuento(desc);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(null);
    }

    public void quitar(){
        model.getLineas().remove(model.getCurrent());
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(null);
    }

    public void cobrar(Factura f)throws Exception{
        for(Linea l: model.getLineas()){
            l.getProducto().setExistencias(l.getProducto().getExistencias()-l.getCantidad());
        }
        Service.instance().create(f);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(null);
        model.clear();
    }

    public void print()throws Exception{
        String dest="pdfs/factura.pdf";
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);

        //Document document = new Document(pdf, PageSize.A4.rotate());
        Document document = new Document(pdf);
        document.setMargins(20, 20, 20, 20);

        Table header = new Table(1);
        header.setWidth(400);
        header.setHorizontalAlignment(HorizontalAlignment.CENTER);
        header.addCell(getCell(new Paragraph("Factura").setFont(font).setBold().setFontSize(20f), TextAlignment.CENTER,false));
        //header.addCell(getCell(new Image(ImageDataFactory.create("logo.jpg")), HorizontalAlignment.CENTER,false));
        document.add(header);

        document.add(new Paragraph(""));document.add(new Paragraph(""));
// Agregar fecha
        Factura factura = new Factura(); // Aquí deberías obtener la instancia de Factura que estás manejando
        document.add(new Paragraph("Fecha: " + factura.getFecha().toString()).setFont(font).setFontSize(12f).setTextAlignment(TextAlignment.RIGHT));

        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
        //------------------Cuerpo------------------------------
        Color bkg = ColorConstants.RED;
        Color frg= ColorConstants.WHITE;

        Table body0 = new Table(2);
        body0.addCell(getCell(new Paragraph("Cliente").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));
        body0.addCell(getCell(new Paragraph("Cajero").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));

        body0.addCell(getCell(new Paragraph(view.getCliente().getSelectedItem().toString()),TextAlignment.CENTER,true));
        body0.addCell(getCell(new Paragraph(view.getCajero().getSelectedItem().toString()),TextAlignment.CENTER,true));

        document.add(body0);

        Table body = new Table(4);
        body.setWidth(400);
        body.setHorizontalAlignment(HorizontalAlignment.CENTER);
        body.addCell(getCell(new Paragraph("Articulo").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));
        body.addCell(getCell(new Paragraph("Cantidad").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));
        body.addCell(getCell(new Paragraph("Descuento").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));
        body.addCell(getCell(new Paragraph("Importe").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));


        for(Linea e: model.getLineas()){

            body.addCell(getCell(new Paragraph(e.getProducto().getDescripcion()),TextAlignment.CENTER,true));
            body.addCell(getCell(new Paragraph(e.getCantidad()+""),TextAlignment.CENTER,true));
            body.addCell(getCell(new Paragraph(e.getDescuento()+""),TextAlignment.CENTER,true));
            body.addCell(getCell(new Paragraph(e.importe()+""),TextAlignment.CENTER,true));
        }

        body.addCell(getCell(new Paragraph("Articulos").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));
        body.addCell(getCell(new Paragraph("Subtotal").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));
        body.addCell(getCell(new Paragraph("Descuento").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));
        body.addCell(getCell(new Paragraph("Total").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));

        body.addCell(getCell(new Paragraph(model.articulos()+""),TextAlignment.CENTER,true));
        body.addCell(getCell(new Paragraph(model.subTotal()+""),TextAlignment.CENTER,true));
        body.addCell(getCell(new Paragraph(model.descuento()+""),TextAlignment.CENTER,true));
        body.addCell(getCell(new Paragraph(model.total()+""),TextAlignment.CENTER,true));

        document.add(body);
        document.close();
    }

    private Cell getCell(Paragraph paragraph, TextAlignment alignment, boolean hasBorder) {
        Cell cell = new Cell().add(paragraph);
        cell.setPadding(0);
        cell.setTextAlignment(alignment);
        if(!hasBorder) cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    public void setDescuentoLineas(Cliente e){
        if(e != null){
            for(var i: model.getLineas()){
                i.setDescuento(e.getDescuento());
                System.out.println(e.getDescuento());
            }
            model.refrescar();
        }
    }

    public void selectCliente(Cliente e) {
        if(e == null){
            e = new Cliente();
        }
        model.selectCliente(e);
    }

    public void setCajero(Cajero e) {
        if(e == null){
            e = new Cajero();
        }
        model.setCajero(e);
    }

    public void clear() {
        model.clear();
    }
}
