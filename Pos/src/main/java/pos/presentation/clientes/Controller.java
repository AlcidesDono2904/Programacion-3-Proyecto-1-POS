package pos.presentation.clientes;

import pos.Application;
import pos.logic.Cliente;
import pos.logic.Service;


import com.itextpdf.io.font.constants.StandardFonts;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Image;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;


import java.util.List;

public class Controller {

    View view;
    public Model model;

    public Controller(View view, Model model) {
        model.init(Service.instance().search(new Cliente()));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void search(Cliente filter) throws  Exception{
        model.setFilter(filter);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Cliente());
        model.setList(Service.instance().search(model.getFilter()));
    }

    public void save(Cliente e) throws  Exception{
        switch (model.getMode()) {
            case Application.MODE_CREATE:
                Service.instance().create(e);
                break;
            case Application.MODE_EDIT:
                Service.instance().update(e);
                break;
        }
        model.setFilter(new Cliente());
        search(model.getFilter());
    }

    public void edit(int row){
        Cliente e = model.getList().get(row);
        try {
            model.setMode(Application.MODE_EDIT);
            model.setCurrent(Service.instance().read(e));
        } catch (Exception ex) {}
    }

    public void delete() throws Exception {
        Service.instance().delete(model.getCurrent());
        search(model.getFilter());
    }

    public void clear() {
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Cliente());
    }

    public void print()throws Exception{
        String dest="pdfs/clientes.pdf";
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);

        //Document document = new Document(pdf, PageSize.A4.rotate());
        Document document = new Document(pdf);
        document.setMargins(20, 20, 20, 20);

        Table header = new Table(1);
        header.setWidth(400);
        header.setHorizontalAlignment(HorizontalAlignment.CENTER);
        header.addCell(getCell(new Paragraph("Listado de Clientes").setFont(font).setBold().setFontSize(20f), TextAlignment.CENTER,false));
        //header.addCell(getCell(new Image(ImageDataFactory.create("logo.jpg")), HorizontalAlignment.CENTER,false));
        document.add(header);

        document.add(new Paragraph(""));document.add(new Paragraph(""));

        Color bkg = ColorConstants.RED;
        Color frg= ColorConstants.WHITE;
        Table body = new Table(4);
        body.setWidth(400);
        body.setHorizontalAlignment(HorizontalAlignment.CENTER);
        body.addCell(getCell(new Paragraph("Id").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));
        body.addCell(getCell(new Paragraph("Nombre").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));
        body.addCell(getCell(new Paragraph("Email").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));
        body.addCell(getCell(new Paragraph("Telefono").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));


        for(Cliente e: model.getList()){
            body.addCell(getCell(new Paragraph(e.getId()),TextAlignment.CENTER,true));
            body.addCell(getCell(new Paragraph(e.getNombre()),TextAlignment.CENTER,true));
            body.addCell(getCell(new Paragraph(e.getEmail()),TextAlignment.CENTER,true));
            body.addCell(getCell(new Paragraph(e.getTelefono()),TextAlignment.CENTER,true));
        }
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
}
