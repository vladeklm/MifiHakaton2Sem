package it.globus.finaudit.repository.specifications;

import it.globus.finaudit.entity.Operation;
import it.globus.finaudit.entity.OperationCategory;
import it.globus.finaudit.entity.OperationStatus;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OperationSpecifications {

    public static Specification<Operation> equalsBankFromId(Long bankFromId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("bankFromId"), bankFromId);
    }

    public static Specification<Operation> equalsBankToId(Long bankToId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("bankToId"), bankToId);
    }

    public static Specification<Operation> betweenDate(LocalDate dateFrom, LocalDate dateTo) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("dateTimeOperation"), dateFrom, dateTo);

    }

    public static Specification<Operation> equalStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) return null;
            Join<Operation, OperationStatus> statusJoin = root.join("operationStatus", JoinType.INNER);
            return criteriaBuilder.equal(statusJoin.get("name"), status);
        };
    }

    public static Specification<Operation> amountFilter(BigDecimal minAmount, BigDecimal maxAmount) {
        return (root, query, cb) -> {
            if (minAmount != null && maxAmount != null) {
                return cb.between(root.get("amount"), minAmount, maxAmount);
            } else if (minAmount != null) {
                return cb.greaterThanOrEqualTo(root.get("amount"), minAmount);
            } else if (maxAmount != null) {
                return cb.lessThanOrEqualTo(root.get("amount"), maxAmount);
            }
            return null;
        };
    }

    public static Specification<Operation> equalInn(String inn) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("inn"), inn);
    }

    public static Specification<Operation> equalOperationCategory(String operationCategory) {
        return (root, query, criteriaBuilder) -> {
            if (operationCategory == null) return null;
            Join<Operation, OperationCategory> operationCategoryJoin = root.join("operationCategory", JoinType.INNER);
            return criteriaBuilder.equal(operationCategoryJoin.get("name"), operationCategory);
        };
    }

}
