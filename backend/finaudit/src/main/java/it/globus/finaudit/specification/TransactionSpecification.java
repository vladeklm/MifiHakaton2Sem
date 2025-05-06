package it.globus.finaudit.specification;

import it.globus.finaudit.entity.Transaction;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;

public class TransactionSpecification {
    public static Specification<Transaction> hasType(String type) {
        return (root, query, cb) ->
                type == null ? cb.conjunction() : cb.equal(root.get("type"), type);
    }

    public static Specification<Transaction> hasStatus(String status) {
        return (root, query, cb) ->
                status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
    }

    public static Specification<Transaction> dateBetween(LocalDateTime start, LocalDateTime end) {
        return (root, query, cb) ->
                start == null || end == null
                        ? cb.conjunction()
                        : cb.between(root.get("date"), start, end);
    }
}
