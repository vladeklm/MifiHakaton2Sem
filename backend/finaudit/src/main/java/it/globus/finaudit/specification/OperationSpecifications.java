package it.globus.finaudit.specification;

import it.globus.finaudit.entity.Operation;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OperationSpecifications {

    public static Specification<Operation> equalsUserId(Long userId){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("client").get("id"), userId);
    }

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
            return criteriaBuilder.equal(root.get("operationStatus").get("name"), status);
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
            return criteriaBuilder.equal(root.get("operationCategory").get("name"), operationCategory);
        };
    }

    public static Specification<Operation> equalOperationType(String operationType) {
        return (root, query, criteriaBuilder) -> {
            if (operationType == null) return null;
            return criteriaBuilder.equal(root.get("operationType").get("name"), operationType);
        };
    }

}
