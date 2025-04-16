package app.structura.finaudit.repository;

import app.structura.finaudit.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OperationRepository extends JpaRepository<Operation, Long> , JpaSpecificationExecutor<Operation> {
}
