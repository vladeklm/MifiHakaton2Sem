package it.globus.finaudit.repository;

import it.globus.finaudit.entity.Operation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> , JpaSpecificationExecutor<Operation> {

    @EntityGraph("Operation.withAssociations")
    List<Operation> findAll(Specification<Operation> spec);
}
