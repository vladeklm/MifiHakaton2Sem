package it.globus.finaudit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.globus.finaudit.entity.OperationStatus;

import java.util.Optional;

@Repository
public interface OperationStatusRepository extends JpaRepository<OperationStatus, Long> {
    Optional<Object> findByName(String statusCode);
}