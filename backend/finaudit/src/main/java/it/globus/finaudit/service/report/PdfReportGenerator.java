package it.globus.finaudit.service.report;


import it.globus.finaudit.DTO.OperationFilter;
import it.globus.finaudit.DTO.report.OperationForJasper;
import it.globus.finaudit.entity.Operation;
import it.globus.finaudit.repository.OperationRepository;

import it.globus.finaudit.repository.specifications.OperationSpecificationBuilder;
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
import java.util.*;


@Component
public class PdfReportGenerator implements ReportGenerator {
    private final OperationRepository operationRepository;
    DateTimeFormatter formatterForFileName = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    DateTimeFormatter formatterDateAndTime = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    DateTimeFormatter formatterDate= DateTimeFormatter.ofPattern("dd.MM.yyyy");


    public PdfReportGenerator(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Transactional(readOnly = true)
    public byte[] generateOperationReport(OperationFilter filter) {
        // 1. Получаем данные
        Specification<Operation> criteria = OperationSpecificationBuilder.buildFromFilter(filter);
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
                LocalDateTime.now().format(formatterForFileName) + ".pdf";

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            // 3. Загрузка шаблона
            JasperReport jasperReport = compileReport();

            // 4. Подготовка данных
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(operationForJasper);
            // 5. Параметры отчета
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("REPORT_TITLE", "Отчет по операциям"); // Кириллический текст
            parameters.put("GENERATION_DATE",
                    LocalDateTime.now().format(formatterDateAndTime));
            parameters.put("OPERATION_FILTER", getFilterDisplay(filter));

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

    private String getFilterDisplay(OperationFilter filter) {
        StringBuilder sb = new StringBuilder("Примененные фильтры:\n");
        if (filter.getBankFromId() != null) sb.append("Банк отправитель: ").append(filter.getBankFromId()).append("\n");
        if (filter.getBankToId() != null) sb.append("Банк получатель: ").append(filter.getBankToId()).append("\n");
        if (filter.getDateFrom() != null)
            sb.append("Дата с: ").append(formatterDate.format(filter.getDateFrom())).append("\n");
        if (filter.getDateTo() != null) sb.append("Дата по: ").append(formatterDate.format(filter.getDateTo())).append("\n");
        if (filter.getStatus() != null) sb.append("Статус: ").append(filter.getStatus()).append("\n");
        if (filter.getInn() != null) sb.append("ИНН: ").append(filter.getInn()).append("\n");
        if (filter.getMinAmount() != null) sb.append("Мин. сумма: ").append(filter.getMinAmount()).append("\n");
        if (filter.getMaxAmount() != null) sb.append("Макс. сумма: ").append(filter.getMaxAmount()).append("\n");
        if (filter.getOperationType() != null) sb.append("Тип операции: ").append(filter.getOperationType()).append("\n");
        if (filter.getOperationCategory() != null) sb.append("Категория: ").append(filter.getOperationCategory()).append("\n");
        return sb.toString();
    }
}
