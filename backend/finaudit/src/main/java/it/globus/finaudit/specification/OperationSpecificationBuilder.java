package it.globus.finaudit.specification;

import it.globus.finaudit.DTO.OperationFilter;
import it.globus.finaudit.entity.Operation;
import org.springframework.data.jpa.domain.Specification;

public class OperationSpecificationBuilder {

    private static Specification<Operation> buildFromFilter(OperationFilter filter) {

        Specification<Operation> spec = Specification.where(null);

        if (filter == null) {
            return spec;
        }

        if (filter.getBankFromId() != null) {
            spec = spec.and(OperationSpecifications.equalsBankFromId(filter.getBankFromId()));
        }

        if (filter.getBankToId() != null) {
            spec = spec.and(OperationSpecifications.equalsBankToId(filter.getBankToId()));
        }

        if (filter.getDateFrom() != null && filter.getDateTo() != null) {
            spec = spec.and(OperationSpecifications.betweenDate(filter.getDateFrom(), filter.getDateTo()));
        }

        if (filter.getStatus() != null) {
            spec = spec.and(OperationSpecifications.equalStatus(filter.getStatus()));
        }

        if (filter.getOperationCategory() != null) {
            spec = spec.and(OperationSpecifications.equalOperationCategory(filter.getOperationCategory()));
        }

        if (filter.getInn() != null) {
            spec = spec.and(OperationSpecifications.equalInn(filter.getInn()));
        }

        if (filter.getOperationType() != null) {
            spec = spec.and(OperationSpecifications.equalOperationType(filter.getOperationType()));
        }

        // Обработка amount
        if (filter.getMinAmount() != null || filter.getMaxAmount() != null) {
            spec = spec.and(OperationSpecifications.amountFilter(
                    filter.getMinAmount(),
                    filter.getMaxAmount()
            ));
        }
        return spec;
    }

    public static Specification<Operation> buildFromFilterAndUserId(OperationFilter filter,Long userId) {
        return buildFromFilter(filter).and(OperationSpecifications.equalsUserId(userId));
    }
}