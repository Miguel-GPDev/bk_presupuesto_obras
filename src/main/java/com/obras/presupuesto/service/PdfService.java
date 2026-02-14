package com.obras.presupuesto.service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.obras.presupuesto.model.Capitulo;
import com.obras.presupuesto.model.Cliente;
import com.obras.presupuesto.model.EncabezadoEmpresa;
import com.obras.presupuesto.model.Partida;
import com.obras.presupuesto.model.Presupuesto;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    private static final Path OUTPUT_DIR = Path.of("generated-pdfs");

    public String generarPdf(Presupuesto presupuesto) {
        try {
            Files.createDirectories(OUTPUT_DIR);
            String fileName = "presupuesto-" + presupuesto.getId() + ".pdf";
            Path output = OUTPUT_DIR.resolve(fileName);

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(output.toFile()));
            document.open();

            Font titulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            document.add(new Paragraph("Presupuesto de obra", titulo));
            document.add(new Paragraph("Nombre: " + presupuesto.getNombre()));
            document.add(new Paragraph("Fecha: " + presupuesto.getFechaCreacion().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
            document.add(new Paragraph(" "));

            EncabezadoEmpresa empresa = presupuesto.getEncabezadoEmpresa();
            if (empresa != null) {
                document.add(new Paragraph("Empresa: " + empresa.getNombreEmpresa(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11)));
                document.add(new Paragraph("CIF: " + empresa.getCif()));
                document.add(new Paragraph("Dirección: " + empresa.getDireccion()));
                document.add(new Paragraph("Teléfono: " + empresa.getTelefono()));
                document.add(new Paragraph("Email: " + empresa.getEmail()));
                document.add(new Paragraph(" "));
            }

            Cliente cliente = presupuesto.getCliente();
            if (cliente != null) {
                document.add(new Paragraph("Cliente: " + cliente.getNombreCliente(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11)));
                document.add(new Paragraph("Documento: " + cliente.getDocumento()));
                document.add(new Paragraph("Dirección cliente: " + cliente.getDireccion()));
                document.add(new Paragraph("Teléfono cliente: " + cliente.getTelefono()));
                document.add(new Paragraph("Email cliente: " + cliente.getEmail()));
                document.add(new Paragraph(" "));
            }

            BigDecimal total = BigDecimal.ZERO;
            for (Capitulo capitulo : presupuesto.getCapitulos()) {
                document.add(new Paragraph("Capítulo: " + capitulo.getNombre(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100f);
                table.setWidths(new float[]{2f, 3f, 2f, 2f, 2f});
                addHeader(table, "Unidad");
                addHeader(table, "Referencia");
                addHeader(table, "Cantidad");
                addHeader(table, "Precio");
                addHeader(table, "Subtotal");

                for (Partida partida : capitulo.getPartidas()) {
                    table.addCell(partida.getUnidadMedida());
                    table.addCell(partida.getReferencia());
                    table.addCell(partida.getCantidad().setScale(3, RoundingMode.HALF_UP).toPlainString());
                    table.addCell(partida.getPrecio().setScale(2, RoundingMode.HALF_UP).toPlainString());
                    BigDecimal subtotal = partida.getSubtotal();
                    table.addCell(subtotal.setScale(2, RoundingMode.HALF_UP).toPlainString());
                    total = total.add(subtotal);
                }

                document.add(table);
                document.add(new Paragraph(" "));
            }

            document.add(new Paragraph("TOTAL PRESUPUESTO: " + total.setScale(2, RoundingMode.HALF_UP).toPlainString(),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13)));
            document.close();
            return output.toString();
        } catch (IOException | DocumentException e) {
            throw new IllegalStateException("No fue posible generar el PDF del presupuesto", e);
        }
    }

    private void addHeader(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell();
        cell.setPhrase(new Paragraph(text, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
        table.addCell(cell);
    }
}
