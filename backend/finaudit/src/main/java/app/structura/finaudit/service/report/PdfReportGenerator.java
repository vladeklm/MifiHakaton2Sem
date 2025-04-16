package app.structura.finaudit.service.report;


import app.structura.finaudit.entity.Operation;
import app.structura.finaudit.repository.OperationRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Component
public class PdfReportGenerator implements ReportGenerator {
    private final OperationRepository operationRepository;
    private static final float MARGIN = 50;
    private static final float LINE_HEIGHT = 20;
    private static final float TABLE_ROW_HEIGHT = 15;


    public PdfReportGenerator(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Transactional(readOnly = true)
    public byte[] generateOperationReport(Specification<Operation> criteria) {
        List<Operation> operations = operationRepository.findAll(criteria);
        operations.forEach(System.out::println);
        File reportsDir = new File("reports");
        if (!reportsDir.exists()) {
            reportsDir.mkdirs(); // Создаст папку и все родительские при необходимости
        }
        String fileName = "reports/operations_report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf";

        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            // Загрузка Unicode-шрифта (должен быть в resources/fonts/)
            PDType0Font font = PDType0Font.load(document,
                    getClass().getResourceAsStream("/fonts/arial.ttf"));
            PDType0Font fontBold = PDType0Font.load(document,
                    getClass().getResourceAsStream("/fonts/arialbd.ttf"));

            for (Operation operation : operations) {
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)){
                    float currentY = initDocument(contentStream, font, fontBold);
                    drawOperationRow(contentStream, operation, currentY);
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }

            document.save(new File(fileName));
            System.out.println("PDF отчет сохранен как: " + fileName);

            // Возвращаем байты файла (если нужно)
            document.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }


    private float initDocument(PDPageContentStream contentStream, PDType0Font font, PDType0Font fontBold) throws IOException {
        // Заголовок отчета
        contentStream.setFont(fontBold, 18);
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, PDRectangle.A4.getHeight() - MARGIN);
        contentStream.showText("Operations report");
        contentStream.endText();

        // Дата генерации
        contentStream.setFont(font, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, PDRectangle.A4.getHeight() - MARGIN - LINE_HEIGHT);
        contentStream.showText("Created: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        contentStream.endText();

        // Заголовки таблицы
        float currentY = PDRectangle.A4.getHeight() - MARGIN - 2 * LINE_HEIGHT;
        drawTableHeader(contentStream, currentY,font);
        return currentY - TABLE_ROW_HEIGHT;
    }


    private void drawTableHeader(PDPageContentStream contentStream, float y,PDType0Font font) throws IOException {
        contentStream.setFont(font, 10);

        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, y);
        contentStream.showText("ID");
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN + 40, y);
        contentStream.showText("Date/Time");
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN + 150, y);
        contentStream.showText("Summary");
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN + 220, y);
        contentStream.showText("Type");
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN + 320, y);
        contentStream.showText("Status");
        contentStream.endText();

        // Горизонтальная линия под заголовком
        contentStream.moveTo(MARGIN, y - 5);
        contentStream.lineTo(PDRectangle.A4.getWidth() - MARGIN, y - 5);
        contentStream.stroke();
    }

    private void drawOperationRow(PDPageContentStream contentStream, Operation operation, float y) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, y);
        contentStream.showText(String.valueOf(operation.getId()));
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN + 40, y);
        contentStream.showText(operation.getDateTimeOperation().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN + 150, y);
        contentStream.showText(operation.getAmount().toString());
        contentStream.endText();

        // Проверяем на null перед получением имени типа операции
        String operationType = operation.getOperationType() != null ? operation.getOperationType().toString() : "N/A";
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN + 220, y);
        contentStream.showText(operationType);
        contentStream.endText();

        // Проверяем на null перед получением имени статуса операции
        String operationStatus = operation.getOperationStatus() != null ? operation.getOperationStatus().toString() : "N/A";
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN + 320, y);
        contentStream.showText(operationStatus);
        contentStream.endText();
    }
}
