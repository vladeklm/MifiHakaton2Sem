package it.globus.finaudit.util.report;

import it.globus.finaudit.DTO.OperationFilter;
import it.globus.finaudit.entity.Operation;
import it.globus.finaudit.service.report.representation.OperationForJasper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RepresentationHelper {
    static DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static List<OperationForJasper> mapOperationForJasper(List<Operation> operations) {
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

    public static String getFilterDisplay(OperationFilter filter) {
        StringBuilder sb = new StringBuilder("Примененные фильтры:\n");
        if (filter.getBankFromId() != null) sb.append("Банк отправитель: ").append(filter.getBankFromId()).append("\n");
        if (filter.getBankToId() != null) sb.append("Банк получатель: ").append(filter.getBankToId()).append("\n");
        if (filter.getDateFrom() != null)
            sb.append("Дата с: ").append(formatterDate.format(filter.getDateFrom())).append("\n");
        if (filter.getDateTo() != null)
            sb.append("Дата по: ").append(formatterDate.format(filter.getDateTo())).append("\n");
        if (filter.getStatus() != null) sb.append("Статус: ").append(filter.getStatus()).append("\n");
        if (filter.getInn() != null) sb.append("ИНН: ").append(filter.getInn()).append("\n");
        if (filter.getMinAmount() != null) sb.append("Мин. сумма: ").append(filter.getMinAmount()).append("\n");
        if (filter.getMaxAmount() != null) sb.append("Макс. сумма: ").append(filter.getMaxAmount()).append("\n");
        if (filter.getOperationType() != null)
            sb.append("Тип операции: ").append(filter.getOperationType()).append("\n");
        if (filter.getOperationCategory() != null)
            sb.append("Категория: ").append(filter.getOperationCategory()).append("\n");
        return sb.toString();
    }
}
