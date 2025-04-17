package it.globus.finaudit.service.report;


import it.globus.finaudit.DTO.report.OperationForJasper;
import it.globus.finaudit.entity.Operation;
import it.globus.finaudit.repository.OperationRepository;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class PdfReportGenerator implements ReportGenerator {
    private final OperationRepository operationRepository;


    public PdfReportGenerator(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Transactional(readOnly = true)
    public byte[] generateOperationReport(Specification<Operation> criteria) {
        // 1. Получаем данные
        List<Operation> operations = operationRepository.findAll(criteria);
        if (operations.isEmpty()) {
            throw new RuntimeException("No data found for report");
        }

        List<OperationForJasper> operationForJasper = mapOperationForJasper(operations);
        System.out.println("Generating report for " + operationForJasper.size() + " operations");

        // 2. Подготовка директории
        File reportsDir = new File("reports");
        if (!reportsDir.exists() && !reportsDir.mkdirs()) {
            throw new RuntimeException("Failed to create reports directory");
        }

        String fileName = "reports/operations_report_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf";

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            // 3. Загрузка шаблона
            JasperReport jasperReport = compileReport();

            // 4. Подготовка данных
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(operationForJasper);

            // 5. Параметры отчета
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("REPORT_TITLE", "Отчет по операциям"); // Кириллический текст
            parameters.put("GENERATION_DATE",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));

            // 6. Генерация отчета
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // 7. Экспорт в файл и байтовый массив
            JasperExportManager.exportReportToPdfFile(jasperPrint, fileName);
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            System.out.println("Report successfully generated: " + fileName);
            return outputStream.toByteArray();

        } catch (JRException | IOException e) {
            throw new RuntimeException("Failed to generate report: " + e.getMessage(), e);
        }
    }

    private JasperReport compileReport() throws JRException {
        InputStream jrxmlStream = getClass().getClassLoader()
                .getResourceAsStream("templates/report/operation_report.jrxml");
        if (jrxmlStream == null) {
            throw new RuntimeException("JRXML template not found");
        }
        return JasperCompileManager.compileReport(jrxmlStream);
    }

    private List<OperationForJasper> mapOperationForJasper(List<Operation> operations) {
        List<OperationForJasper> operationsForJasper = new ArrayList<>();
        for (Operation operation : operations) {
            OperationForJasper operationForJasper = new OperationForJasper();
            operationForJasper.setId(operation.getId());
            operationForJasper.setOperationType(operation.getOperationType().getName());
            operationForJasper.setOperationCategory(operation.getOperationCategory().getName());
            operationForJasper.setOperationStatus(operation.getOperationStatus().getName());
            operationForJasper.setAmount(operation.getAmount());
            operationForJasper.setComment(operation.getComment());
            operationsForJasper.add(operationForJasper);
        }
        return operationsForJasper;
    }


}
