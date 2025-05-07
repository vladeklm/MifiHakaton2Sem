package it.globus.finaudit.service.report;


import it.globus.finaudit.DTO.OperationFilter;
import it.globus.finaudit.entity.Operation;
import it.globus.finaudit.repository.OperationRepository;
import it.globus.finaudit.repository.OperationTypeRepository;
import it.globus.finaudit.service.report.representation.AmountByCategory;
import it.globus.finaudit.service.report.representation.NumberOperationsForPeriod;
import it.globus.finaudit.service.report.representation.OperationForJasper;
import it.globus.finaudit.specification.OperationSpecificationBuilder;
import it.globus.finaudit.util.report.RepresentationHelper;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static it.globus.finaudit.util.report.DateHelper.*;


@Component
@Slf4j
public abstract class AbstractReportGenerator implements ReportGenerator {
    private final OperationRepository operationRepository;
    private final ReportTemplate reportTemplate;
    private final OperationTypeRepository operationTypeRepository;
    @Autowired
    private RepresentationHelper representationHelper;


    public AbstractReportGenerator(OperationRepository operationRepository, ReportTemplate reportTemplate,
                                   OperationTypeRepository operationTypeRepository) {
        this.operationRepository = operationRepository;
        this.reportTemplate = reportTemplate;
        this.operationTypeRepository = operationTypeRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public byte[] generateGeneralReport(OperationFilter filter, Long userId) {
        Specification<Operation> criteria = OperationSpecificationBuilder.buildFromFilterAndUserId(filter, userId);
        List<Operation> operations = operationRepository.findAll(criteria);
        List<OperationForJasper> operationForJasper = representationHelper.mapOperationForJasper(operations);
        log.trace("Generating report for {} operations", operationForJasper.size());
        JasperReport jasperReport = reportTemplate.getGeneralReport();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(operationForJasper);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_TITLE", "Отчет по операциям");
        parameters.put("GENERATION_DATE",
                LocalDateTime.now().format(dateAndTimeFormat));
        parameters.put("OPERATION_FILTER", representationHelper.getFilterDisplay(filter));
        return generateReport(jasperReport, parameters, dataSource);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] generatePieChartIncome(OperationFilter filter, Long userId) {
        filter.setOperationTypeId(operationTypeRepository.findByName("Поступление").getId());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("CHART_TITLE", "График поступлений");
        return generatePieChart(filter, parameters, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] generatePieChartWithdraw(OperationFilter filter, Long userId) {
        filter.setOperationTypeId(operationTypeRepository.findByName("Поступление").getId());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("CHART_TITLE", "График списаний");
        return generatePieChart(filter, parameters, userId);
    }

    @Override
    public byte[] generateWeeklyDynamicsOperationsReport(OperationFilter filter, Long userId) {
        return generateDynamicsReport(filter, "Недельная динамика операций",
                op -> op.getDateTimeOperation().toLocalDate().format(dateFormat),
                generatingAllDaysOfWeek(filter.getDateFrom(), filter.getDateTo()), userId);
    }

    @Override
    public byte[] generateMonthlyDynamicsOperationsReport(OperationFilter filter, Long userId) {
        return generateDynamicsReport(filter, "Месячная динамика операций",
                op -> op.getDateTimeOperation().toLocalDate().format(dateFormat),
                generatingAllWeeksOfMonth(filter.getDateFrom(), filter.getDateTo()), userId);
    }

    @Override
    public byte[] generateQuarterlyDynamicsOperationsReport(OperationFilter filter, Long userId) {
        return generateDynamicsReport(filter, "Квартальная динамика операций",
                op -> op.getDateTimeOperation().format(monthFormat),
                generatingAllMonthOfQuarter(filter.getDateFrom(), filter.getDateTo()), userId);
    }

    @Override
    public byte[] generateYearlyDynamicsOperationsReport(OperationFilter filter, Long userId) {
        return generateDynamicsReport(filter, "Годовая динамика операций",
                op -> op.getDateTimeOperation().format(monthFormat),
                generatingAllMonthOfYear(filter.getDateFrom(), filter.getDateTo()),
                userId);
    }


    private byte[] generatePieChart(OperationFilter filter, Map<String, Object> parameters, Long userId) {
        Specification<Operation> criteria = OperationSpecificationBuilder.buildFromFilterAndUserId(filter, userId);
        List<Operation> operations = operationRepository.findAll(criteria);
        List<AmountByCategory> amountByCategories = operations.stream()
                .collect(Collectors.groupingBy(
                        op -> op.getOperationCategory().getName(),
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Operation::getAmount,
                                BigDecimal::add
                        )
                ))
                .entrySet().stream()
                .map(entry -> new AmountByCategory(entry.getKey(), entry.getValue()))
                .toList();
        JasperReport jasperReport = reportTemplate.getPieChartIncomeOrWithdraw();
        JRBeanCollectionDataSource categoryAmountDataSource = new JRBeanCollectionDataSource(amountByCategories);
        parameters.put("CATEGORY_AMOUNT", categoryAmountDataSource);
        return generateReport(jasperReport, parameters);
    }

    private byte[] generateDynamicsReport(OperationFilter filter, String title,
                                          Function<Operation, String> periodExtractor,
                                          List<String> allPeriods,
                                          Long userId) {
        Specification<Operation> criteria = OperationSpecificationBuilder.buildFromFilterAndUserId(filter, userId);
        List<Operation> operations = operationRepository.findAll(criteria);

        List<NumberOperationsForPeriod> allOperations = generateCompleteOperationsData(
                operations,
                op -> true,
                "Все операции",
                periodExtractor,
                allPeriods
        );

        List<NumberOperationsForPeriod> incomeOperations = generateCompleteOperationsData(
                operations,
                op -> "Поступление".equals(op.getOperationType().getName()),
                "Поступления",
                periodExtractor,
                allPeriods
        );

        List<NumberOperationsForPeriod> outcomeOperations = generateCompleteOperationsData(
                operations,
                op -> "Списание".equals(op.getOperationType().getName()),
                "Списания",
                periodExtractor,
                allPeriods
        );

        List<NumberOperationsForPeriod> combinedData = new ArrayList<>();
        combinedData.addAll(allOperations);
        combinedData.addAll(incomeOperations);
        combinedData.addAll(outcomeOperations);

        Map<String, Object> parameters = new HashMap<>();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(combinedData);
        parameters.put("CHART_TITLE", title);
        parameters.put("REPORT_PERIOD",
                filter.getDateFrom().format(dateFormat) +
                        " - " +
                        filter.getDateTo().format(dateFormat));
        parameters.put("NUMBER_OPERATIONS_FOR_PERIOD", dataSource);

        JasperReport jasperReport = reportTemplate.getDynamicsOfOperationsReport();
        return generateReport(jasperReport, parameters);
    }

    private List<NumberOperationsForPeriod> generateCompleteOperationsData(
            List<Operation> operations,
            Predicate<Operation> filter,
            String typeCategory,
            Function<Operation, String> periodExtractor,
            List<String> allPeriods) {

        // Получаем фактические данные
        Map<String, Long> actualData = operations.stream()
                .filter(filter)
                .collect(Collectors.groupingBy(
                        periodExtractor,
                        Collectors.counting()
                ));

        // Создаем полный набор данных с нулями для отсутствующих периодов
        return allPeriods.stream()
                .map(period -> new NumberOperationsForPeriod(
                        typeCategory,
                        actualData.getOrDefault(period, 0L),
                        period
                ))
                .sorted(Comparator.comparing(NumberOperationsForPeriod::getDatePeriod))
                .collect(Collectors.toList());
    }


    private byte[] generateReport(JasperReport jasperReport, Map<String, Object> parameters) {
        return generateReport(jasperReport, parameters, new JREmptyDataSource());
    }

    private byte[] generateReport(JasperReport jasperReport,
                                  JRDataSource dataSource) {
        return generateReport(jasperReport, new HashMap<>(), dataSource);
    }

    private byte[] generateReport(JasperReport jasperReport, Map<String, Object> parameters,
                                  JRDataSource dataSource) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            exportReport(jasperPrint, outputStream);
            log.trace("Report successfully generated");
            return outputStream.toByteArray();
        } catch (JRException | IOException e) {
            throw new RuntimeException("Failed to generate report: " + e.getMessage(), e);
        }
    }

    abstract void exportReport(JasperPrint jasperPrint, OutputStream outputStream) throws JRException;
}