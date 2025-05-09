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

    public static Specification<Operation> equalStatusId(Long statusId) {
        return (root, query, criteriaBuilder) -> {
            if (statusId == null) return null;
            return criteriaBuilder.equal(root.get("operationStatus").get("id"), statusId);
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

    public static Specification<Operation> equalOperationCategoryId(Long operationCategoryId) {
        return (root, query, criteriaBuilder) -> {
            if (operationCategoryId == null) return null;
            return criteriaBuilder.equal(root.get("operationCategory").get("id"), operationCategoryId);
        };
    }

    public static Specification<Operation> equalOperationTypeId(Long operationTypeId) {
        return (root, query, criteriaBuilder) -> {
            if (operationTypeId == null) return null;
            return criteriaBuilder.equal(root.get("operationType").get("id"), operationTypeId);
        };
    }
}
