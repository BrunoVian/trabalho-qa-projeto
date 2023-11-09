package br.com.securityprofit.report;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.IOException;

public class PdfReportGenerator {

   public static void generatePdfReport(String filePath) throws IOException {
        // Criação de um novo documento PDF
        PdfWriter pdfWriter = new PdfWriter(new File(filePath));
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);

        try {
            // Adição de conteúdo ao documento
            document.add(new Paragraph("Relatório PDF com iText")
                    .setFontColor(new DeviceRgb(0, 0, 255))  // Use cores RGB para evitar Unknown color space
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\nDetalhes do Relatório:")
                    .setFontColor(new DeviceRgb(128, 128, 128))  // Use cores RGB para evitar Unknown color space
                    .setFontSize(12));

            document.add(new Paragraph("1. Item 1"));
            document.add(new Paragraph("2. Item 2"));
            document.add(new Paragraph("3. Item 3"));
        } finally {
            // Fechamento do documento
            document.close();
        }

        System.out.println("Relatório PDF gerado com sucesso: " + filePath);
    }
}
