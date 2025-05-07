package it.globus.finaudit.specification;

import it.globus.finaudit.entity.Operation;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;

public class TransactionSpecification {
    public static Specification<Operation> hasType(String type) {
        return (root, query, cb) ->
                type == null ? cb.conjunction() : cb.equal(root.get("type"), type);
    }

    public static Specification<Operation> hasStatus(String status) {
        return (root, query, cb) ->
                status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
    }

    public static Specification<Operation> dateBetween(LocalDateTime start, LocalDateTime end) {
        return (root, query, cb) ->
                start == null || end == null
                        ? cb.conjunction()
                        : cb.between(root.get("date"), start, end);
    }
}
