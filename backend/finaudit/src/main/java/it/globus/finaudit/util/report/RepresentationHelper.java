package it.globus.finaudit.util.report;

import it.globus.finaudit.DTO.OperationFilter;
import it.globus.finaudit.entity.Operation;
import it.globus.finaudit.entity.OperationCategory;
import it.globus.finaudit.entity.OperationStatus;
import it.globus.finaudit.entity.OperationType;
import it.globus.finaudit.repository.OperationCategoryRepository;
import it.globus.finaudit.repository.OperationStatusRepository;
import it.globus.finaudit.repository.OperationTypeRepository;
import it.globus.finaudit.service.report.representation.OperationForJasper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class RepresentationHelper {
    static DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final OperationTypeRepository operationTypeRepository;
    private final OperationCategoryRepository operationCategoryRepository;
    private final OperationStatusRepository operationStatusRepository;

    public RepresentationHelper(OperationTypeRepository operationTypeRepository,
                                OperationCategoryRepository operationCategoryRepository,
                                OperationStatusRepository operationStatusRepository) {
        this.operationTypeRepository = operationTypeRepository;
        this.operationCategoryRepository = operationCategoryRepository;
        this.operationStatusRepository = operationStatusRepository;
    }

    public List<OperationForJasper> mapOperationForJasper(List<Operation> operations) {
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

    public String getFilterDisplay(OperationFilter filter) {
        StringBuilder sb = new StringBuilder("Примененные фильтры:\n");
        if (filter.getBankFromId() != null) sb.append("Банк отправитель: ").append(filter.getBankFromId()).append("\n");
        if (filter.getBankToId() != null) sb.append("Банк получатель: ").append(filter.getBankToId()).append("\n");
        if (filter.getDateFrom() != null)
            sb.append("Дата с: ").append(formatterDate.format(filter.getDateFrom())).append("\n");
        if (filter.getDateTo() != null)
            sb.append("Дата по: ").append(formatterDate.format(filter.getDateTo())).append("\n");
        if (filter.getStatusId() != null) {
            OperationStatus operationStatus = operationStatusRepository.findById(filter.getStatusId())
                    .orElseThrow(EntityNotFoundException::new);
            sb.append("Статус: ").append(operationStatus).append("\n");
        }
        if (filter.getInn() != null) sb.append("ИНН: ").append(filter.getInn()).append("\n");
        if (filter.getMinAmount() != null) sb.append("Мин. сумма: ").append(filter.getMinAmount()).append("\n");
        if (filter.getMaxAmount() != null) sb.append("Макс. сумма: ").append(filter.getMaxAmount()).append("\n");
        if (filter.getOperationTypeId() != null) {
            OperationType operationType = operationTypeRepository.findById(filter.getOperationTypeId())
                    .orElseThrow(EntityNotFoundException::new);
            sb.append("Тип операции: ").append(operationType).append("\n");
        }
        if (filter.getOperationCategoryId() != null) {
            OperationCategory operationCategory = operationCategoryRepository.findById(filter.getOperationCategoryId())
                    .orElseThrow(EntityNotFoundException::new);
            sb.append("Категория: ").append(operationCategory).append("\n");
        }
        return sb.toString();
    }
}
