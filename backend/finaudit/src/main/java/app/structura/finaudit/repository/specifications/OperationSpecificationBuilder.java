package app.structura.finaudit.repository.specifications;

import app.structura.finaudit.DTO.OperationFilter;
import org.springframework.data.jpa.domain.Specification;
import app.structura.finaudit.entity.Operation;

public class OperationSpecificationBuilder {

    public static Specification<Operation> buildFromFilter(OperationFilter filter) {
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

        // Обработка amount
        if (filter.getMinAmount() != null || filter.getMaxAmount() != null) {
            spec = spec.and(OperationSpecifications.amountFilter(
                    filter.getMinAmount(),
                    filter.getMaxAmount()
            ));
        }

        return spec;
    }
}