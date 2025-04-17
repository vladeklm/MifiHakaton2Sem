package it.globus.finaudit.repository;

import it.globus.finaudit.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OperationRepository extends JpaRepository<Operation, Long> , JpaSpecificationExecutor<Operation> {
}
