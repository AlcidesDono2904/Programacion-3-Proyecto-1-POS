package pos.presentation.historico;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import pos.logic.Factura;
import pos.logic.Linea;
import pos.logic.Service;

import java.util.ArrayList;



public class ControllerHistorico {
    public static final int MODE_CREATE=0;
    public static final int MODE_EDIT=1;

    ModelHistorico model;
    View view;
    public ControllerHistorico(View view, ModelHistorico model){
        model.init(Service.instance().searchFacturas(new Factura()));
        this.model = model;
        this.view = view;
        view.setController(this);
        view.setModel(model);
    }
    public void buscar(Factura filter)throws Exception{
        model.setFilter(filter);
        model.setMode(MODE_CREATE);
        model.setCurrentFactura(new Factura());
        model.setListFactura(Service.instance().searchFacturas(filter));
    }

    private Cell getCell(Paragraph paragraph, TextAlignment textAlignment, boolean hasBorder){
        Cell cell = new Cell().add(paragraph);
        cell.setPadding(0);
        cell.setTextAlignment(textAlignment);
        if(!hasBorder){
            cell.setBorder(Border.NO_BORDER);
        }
        return cell;
    }


    public void print() throws Exception {
        String fileName = "invoices.pdf";
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        try (PdfWriter writer = new PdfWriter(fileName);
             PdfDocument pdfDocument = new PdfDocument(writer);
             Document document = new Document(pdfDocument)) {

            document.setMargins(20, 20, 20, 20);

            for (Factura e : model.getListFactura()) {
                addInvoiceDetails(document, font, e);
                addTitle(document, font);
                addInvoiceLines(document, font, e);
                addInvoiceTotal(document, e);
                document.add(new Paragraph("\n\n"));
            }
        }
    }

    private void addTitle(Document document, PdfFont font) {
        Paragraph title = new Paragraph("\nFactura Detalles")
                .setFont(font)
                .setFontSize(11)
                .setTextAlignment(TextAlignment.LEFT);
        document.add(title);
    }

    private void addInvoiceDetails(Document document, PdfFont font, Factura e) {
        document.add(new Paragraph(e.getCodigo())
                .setFontColor(ColorConstants.RED)
                .setFont(font)
                .setFontSize(18)
                .setTextAlignment(TextAlignment.LEFT));

        Table separator = new Table(1);
        separator.setWidth(UnitValue.createPercentValue(100));
        separator.setBorder(Border.NO_BORDER);

        Cell cell = new Cell().setBorder(Border.NO_BORDER)
                .setBorderBottom(new SolidBorder(1));
        separator.addCell(cell);

        document.add(separator);
        document.add(new Paragraph("Fecha: " + e.getFecha()).setFontColor(ColorConstants.BLACK).setFont(font).setBackgroundColor(ColorConstants.LIGHT_GRAY).setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph("Cliente: " + e.getCliente().getNombre()).setFontColor(ColorConstants.BLACK).setFont(font).setTextAlignment(TextAlignment.LEFT));
        document.add(new Paragraph("Cajero: " + e.getCajero().getNombre()).setFontColor(ColorConstants.BLACK).setFont(font).setTextAlignment(TextAlignment.LEFT));
        document.add(separator);
    }

    private void addInvoiceLines(Document document, PdfFont font, Factura e) {
        Table tableLines = new Table(6);
        tableLines.setWidth(550);
        tableLines.setHorizontalAlignment(HorizontalAlignment.CENTER);

        tableLines.addCell(getCell(new Paragraph("Descripcion").setFontColor(ColorConstants.BLUE).setBackgroundColor(ColorConstants.LIGHT_GRAY).setFont(font), TextAlignment.CENTER, true));
        tableLines.addCell(getCell(new Paragraph("Cantidad").setFontColor(ColorConstants.BLUE).setBackgroundColor(ColorConstants.LIGHT_GRAY).setFont(font), TextAlignment.CENTER, true));
        tableLines.addCell(getCell(new Paragraph("Precio").setFontColor(ColorConstants.BLUE).setBackgroundColor(ColorConstants.LIGHT_GRAY).setFont(font), TextAlignment.CENTER, true));
        tableLines.addCell(getCell(new Paragraph("Descuento").setFontColor(ColorConstants.BLUE).setBackgroundColor(ColorConstants.LIGHT_GRAY).setFont(font), TextAlignment.CENTER, true));
        tableLines.addCell(getCell(new Paragraph("Neto").setFontColor(ColorConstants.BLUE).setBackgroundColor(ColorConstants.LIGHT_GRAY).setFont(font), TextAlignment.CENTER, true));
        tableLines.addCell(getCell(new Paragraph("Importe").setFontColor(ColorConstants.BLUE).setBackgroundColor(ColorConstants.LIGHT_GRAY).setFont(font), TextAlignment.CENTER, true));

        for (Linea  l : e.getLineas()) {
            tableLines.addCell(getCell(new Paragraph(l.getProducto().getDescripcion()), TextAlignment.CENTER, true));
            tableLines.addCell(getCell(new Paragraph(String.valueOf(l.getCantidad())), TextAlignment.CENTER, true));
            tableLines.addCell(getCell(new Paragraph(String.valueOf(l.getProducto().getPrecioUnitario())), TextAlignment.CENTER, true));
            tableLines.addCell(getCell(new Paragraph(String.valueOf(l.getDescuento())), TextAlignment.CENTER, true));
            tableLines.addCell(getCell(new Paragraph(String.valueOf(l.neto())), TextAlignment.CENTER, true));
            tableLines.addCell(getCell(new Paragraph(String.valueOf(l.importe())), TextAlignment.CENTER, true));
        }

        document.add(tableLines);
        document.add(new Paragraph("\n"));
    }


    private void addInvoiceTotal(Document document, Factura e) {
        Table tableTotal = new Table(1);
        tableTotal.setWidth(170);
        tableTotal.setHorizontalAlignment(HorizontalAlignment.RIGHT);

        Paragraph totalParagraph = new Paragraph("Total: " + e.importe())
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER);

        tableTotal.addCell(getCell(totalParagraph, TextAlignment.CENTER, false));
        document.add(tableTotal);
        document.add(new Paragraph("\n\n"));
    }
    public void editar(int fila ) {
        Factura f = model.getListFactura().get(fila);
        model.setCurrentFactura(f);
        model.setMode(MODE_EDIT);
    }
    public void eliminar(){
        model.setCurrentFactura(new Factura());
        model.setListFactura(new ArrayList<Factura>());
        model.setMode(MODE_CREATE);
    }
}
