package it.globus.finaudit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.globus.finaudit.entity.OperationType;

@Repository
public interface OperationTypeRepository extends JpaRepository<OperationType, Long> {
}