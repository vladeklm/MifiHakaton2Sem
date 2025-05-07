package it.globus.finaudit.repository;

import it.globus.finaudit.entity.OperationLogHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationLogHistoryRepository extends JpaRepository<OperationLogHistory, Long> {
}
